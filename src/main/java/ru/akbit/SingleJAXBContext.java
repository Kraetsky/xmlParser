package ru.akbit;

import examples.schema.AINTERFACECDRVERSION8;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class SingleJAXBContext {

    private static JAXBContext inContext;
    private static JAXBContext outContext;

    public SingleJAXBContext() {
    }

    public static synchronized JAXBContext getInContext() {
        try {
            if (inContext == null)
                inContext = JAXBContext.newInstance(AINTERFACECDRVERSION8.class);

        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inContext;
    }

    public static synchronized JAXBContext getOutContext() {
        try {
            if (outContext == null)
                outContext = JAXBContext.newInstance(AINTERFACECDRVERSION8Decorator.class, AINTERFACECDRVERSION8DecoratorAllFields.class);
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return outContext;
    }

}
