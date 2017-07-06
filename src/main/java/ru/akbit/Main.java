package ru.akbit;


import examples.schema.AINTERFACECDRVERSION8;
import examples.schema.SmsDataChild;
import org.apache.commons.beanutils.PropertyUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.akbit.Util.*;

/**
 * Created by kraetsky on 21.06.2017.
 */
public class Main {

//    Logger log = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) throws IOException, JAXBException {



        if (!isArgsValid(args)) {
            return;
        }
        String pathName = args[0];

        FileOutputStream fos = new FileOutputStream(args[1]);
        byte[] bufferBegin = ("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n<A_INTERFACE>").getBytes();
        byte[] bufferEnd = ("</A_INTERFACE>").getBytes();
        fos.write(bufferBegin, 0, bufferBegin.length);

        List<Decorator> list = new ArrayList<Decorator>();


        Files.list(Paths.get(pathName))
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        unmarshallAndWriteToFile(filePath.toString(), fos, list);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                });


        fos.write(bufferEnd, 0, bufferEnd.length);
        fos.close();
        System.out.println(list.size());
    }


    public static void unmarshallAndWriteToFile(String str, FileOutputStream fos, List<Decorator> list) throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(str));
        JAXBContext jaxbContext = JAXBContext.newInstance(AINTERFACECDRVERSION8.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        JAXBContext jaxbContextToWrite = JAXBContext.newInstance(AINTERFACECDRVERSION8Decorator.class, AINTERFACECDRVERSION8DecoratorAllFields.class);
        Marshaller marshaller = jaxbContextToWrite.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        long start = System.currentTimeMillis();
        System.out.println("current file: " + str);
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
                    if (isValidSms(mapCdr)) {
                        for (SmsDataChild child : mapCdr.getMoSms().getSmsData().getSmsDataChild()) {
                            if (isValidSmsDataChild(child)) {
                                list.add(new AINTERFACECDRVERSION8DecoratorAllFields(mapCdr, child));
                                marshaller.marshal(new AINTERFACECDRVERSION8DecoratorAllFields(mapCdr, child), fos);
                            }
                        }
                    }
//                        if (isSmsValidSecond(mapCdr)) {
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
        System.out.println("Fields are receieved in " + (System.currentTimeMillis() - start) + "millis");

    }

    private static boolean isArgsValid(String[] args) {
        if (args.length != 2) {
            System.out.println("You should pass 2 arguments");
            return false;
        }


        if (!Files.exists(Paths.get(args[0]))) {
            System.out.println("Path not found");
            return false;
        }

        return true;
    }

}
