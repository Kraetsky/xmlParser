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

import static ru.akbit.UnmarshallerAndWriter.isArgsValid;

import static ru.akbit.Util.*;

/**
 * Created by kraetsky on 21.06.2017.
 */
public class Main {


    public static void main(String[] args) throws IOException, JAXBException {
        UnmarshallerAndWriter unmarshallerAndWriter = new UnmarshallerAndWriter();



        if (!isArgsValid(args)) {
            return;
        }
        else{
            unmarshallerAndWriter.readAndWriteToFile(args[0], args[1]);
        }

    }
}
