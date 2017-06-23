package ru.akbit;

import examples.schema.MAPCDRVERSION13;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by kraetsky on 21.06.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException, JAXBException {
        if (!isArgsValid(args)) {
            return;
        }
        String fileName = args[0];
        List<String> fields = Util.getFieldsFromConfigFile(args[1]);
        FileOutputStream fos = new FileOutputStream(args[2]);

        StringBuilder sb = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(fileName));
        JAXBContext jaxbContext = JAXBContext.newInstance(MAPCDRVERSION13.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        long start = System.currentTimeMillis();
        stream.forEach(line -> {
            sb.append(line);

            if (line.equals("</MAP-CDR-VERSION13>")) {
                try {
                    StringReader reader = new StringReader(sb.toString());
                    MAPCDRVERSION13 mapCdr = (MAPCDRVERSION13) unmarshaller.unmarshal(reader);
                    byte[] buffer = Util.getFormattedValue(mapCdr, fields).getBytes();
                    fos.write(buffer, 0, buffer.length);
                    sb.delete(0, sb.length());

                } catch (JAXBException e) {
                    System.out.println("XML file is not valid.");

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    System.out.println("Config fields should be passed by camelCase.");
                } catch (IOException e) {
                    System.out.println("File not found.");
                }
            }

        });
        System.out.println("Fields are receieved in " + (System.currentTimeMillis() - start) + "millis");


    }

    private static boolean isArgsValid(String[] args) {
        if (args.length != 3) {
            System.out.println("You should pass 3 arguments");
            return false;
        }


        if (!Files.exists(Paths.get(args[0]))) {
            System.out.println("File not found");
            return false;
        }

        if (!Files.exists(Paths.get(args[1]))) {
            System.out.println("Config file not found");
            return false;
        }
        return true;
    }

}
