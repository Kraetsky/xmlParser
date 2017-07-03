package ru.akbit;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "A_INTERFACE")
@XmlAccessorType(XmlAccessType.FIELD)
public class AInterface {

    @XmlElement(name = "A-INTERFACE-CDR-VERSION8", type = AINTERFACECDRVERSION8Decorator.class)
    private List<Decorator> list;

    public AInterface() {}

    public AInterface(List<Decorator> list) {
        this.list = list;
    }
}
