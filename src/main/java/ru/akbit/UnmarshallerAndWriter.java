package ru.akbit;

import examples.schema.AINTERFACECDRVERSION8;
import examples.schema.SmsDataChild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static ru.akbit.Util.*;

public class UnmarshallerAndWriter {

    private static final Logger log = LoggerFactory.getLogger(UnmarshallerAndWriter.class);

    public UnmarshallerAndWriter() throws FileNotFoundException {
    }


    public void readAndWriteToFile(String directoryPath, String outputFilePath) throws IOException {
        long startTime = System.currentTimeMillis();
        AtomicLong counter = new AtomicLong(0);

        FileOutputStream fos = new FileOutputStream(outputFilePath);

        byte[] bufferBegin = ("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n<A_INTERFACE>").getBytes();
        byte[] bufferEnd = ("</A_INTERFACE>").getBytes();
        fos.write(bufferBegin, 0, bufferBegin.length);


        Files.list(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        unmarshallAndWriteToFile(filePath.toString(), fos, counter);
                        log.debug("current file: {}", filePath.toString());
                    } catch (IOException ioe) {
                        log.error("IO error: ", ioe);
                    } catch (JAXBException jaxbe) {
                        log.error("Parsing error: ", jaxbe);
                    }
                });


        fos.write(bufferEnd, 0, bufferEnd.length);
        fos.close();
        long workingTimeInSeconds = (System.currentTimeMillis() - startTime) / 1000;
        log.debug("Parsing finished succesfully in {} seconds, count: {}", workingTimeInSeconds, counter.get());
    }


    public void unmarshallAndWriteToFile(String filePath, FileOutputStream fos, AtomicLong counter) throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(filePath));
        JAXBContext jaxbInContext = SingleJAXBContext.getInContext();
        Unmarshaller unmarshaller = jaxbInContext.createUnmarshaller();

        JAXBContext jaxbOutContext = SingleJAXBContext.getOutContext();
        Marshaller marshaller = jaxbOutContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        System.out.println("current file: " + filePath);
//        log.debug("current file: {}", str);
        stream.forEach(line -> {
            if (line.equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {

            } else {
                sb.append(line);
            }

            if (line.equals("</A-INTERFACE-CDR-VERSION8>")) {
                try {
                    StringReader reader = new StringReader(sb.toString());
                    AINTERFACECDRVERSION8 mapCdr = (AINTERFACECDRVERSION8) unmarshaller.unmarshal(reader);
                    if (isSmsExist(mapCdr)) {
                        for (SmsDataChild child : mapCdr.getMoSms().getSmsData().getSmsDataChild()) {
                            if (isSmsStartTimeValid(child)) {
                                if (isValidSmsDataChild(mapCdr, child)) {
                                    counter.getAndIncrement();
                                    marshaller.marshal(new AINTERFACECDRVERSION8DecoratorAllFields(mapCdr, child), fos);
                                }
                            }
                        }
                    }
                    if (isSmsOfType4(mapCdr)) {
                        if (isRecordClosingTimeValid(mapCdr)) {
                            counter.getAndIncrement();
                            marshaller.marshal(new AINTERFACECDRVERSION8Decorator(mapCdr), fos);
                        }
                    }

//                        if (isSmsValidSecondVariant(mapCdr)) {
//
//                            marshaller.marshal(new AINTERFACECDRVERSION8DecoratorAllFields(mapCdr),fos);
//                            list.add(new AINTERFACECDRVERSION8DecoratorAllFields(mapCdr));
//                        }
                    sb.delete(0, sb.length());
                } catch (IllegalAccessException e) {
                    System.out.println(e);
                } catch (InvocationTargetException e) {
                    System.out.println("File not found.");
                } catch (NoSuchMethodException e) {
                    System.out.println(e);
                    e.printStackTrace();
                } catch (JAXBException e) {
                    System.out.println(e);
                }
            }
        });
    }

    public static boolean isArgsValid(String[] args) {
        if (args.length != 2) {
            log.warn("You should pass 2 arguments");
            return false;
        }


        if (!Files.exists(Paths.get(args[0]))) {
            log.warn("Path not found");
            return false;
        }

        return true;
    }

}

