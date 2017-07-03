package ru.akbit;


import examples.schema.AINTERFACECDRVERSION8;
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

import static ru.akbit.Util.*;

/**
 * Created by kraetsky on 21.06.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException, JAXBException {
        if (!isArgsValid(args)) {
            return;
        }
        String pathName = args[0];

        FileOutputStream fos = new FileOutputStream(args[1]);
        byte[] bufferBegin = ("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n<A_INTERFACE>").getBytes();
        byte[] bufferEnd = ("\n</A_INTERFACE>").getBytes();


        List<Decorator> resultList = new ArrayList<>();

        Files.list(Paths.get(pathName))
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        unmarshallAndWriteToFile(filePath.toString(), fos, resultList);
                        System.out.println("RESULT LIST SIZE === " + resultList.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                });

        JAXBContext jaxbContextToWrite = JAXBContext.newInstance(AInterface.class, AINTERFACECDRVERSION8Decorator.class, AINTERFACECDRVERSION8DecoratorAllFields.class);
        Marshaller marshaller = jaxbContextToWrite.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        System.out.println(resultList.size());
        marshaller.marshal(new AInterface(resultList), fos);


    }


    public static void unmarshallAndWriteToFile(String str, FileOutputStream fos, List<Decorator> resultList) throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(str));
        JAXBContext jaxbContext = JAXBContext.newInstance(AINTERFACECDRVERSION8.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        long start = System.currentTimeMillis();
        stream.forEach(line -> {
            if (line.equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {

            } else {
                sb.append(line)
                        .append("\n");

            }

            if (line.equals("</A-INTERFACE-CDR-VERSION8>")) {
                try {

                    StringReader reader = new StringReader(sb.toString());
                    AINTERFACECDRVERSION8 mapCdr = (AINTERFACECDRVERSION8) unmarshaller.unmarshal(reader);
                    if (mapCdr.getMoSms() != null) {
                        if (isValidSms(mapCdr)) {
                            resultList.add(new AINTERFACECDRVERSION8Decorator(mapCdr));



                        }
                        if (isSmsValidSecond(mapCdr)) {
                            resultList.add(new AINTERFACECDRVERSION8DecoratorAllFields(mapCdr));
                        }

                    }
                    sb.delete(0, sb.length());


                } catch (IllegalAccessException e) {
                    System.out.println(e);
                } catch (InvocationTargetException e) {

                    System.out.println("File not found.");
                } catch (NoSuchMethodException e) {
                    System.out.println(e);
                } catch (JAXBException e) {
                    e.printStackTrace();
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
