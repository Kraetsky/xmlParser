package ru.akbit;

import examples.schema.AINTERFACECDRVERSION8;

import javax.xml.bind.annotation.*;

import java.util.Date;

import static ru.akbit.Util.getFormattedImsi;
import static ru.akbit.Util.getFormattedTime;


@XmlRootElement(name="A-INTERFACE-CDR-VERSION8")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AINTERFACECDRVERSION8Decorator implements Decorator {

    private AINTERFACECDRVERSION8 ainterfacecdrversion8;

    public AINTERFACECDRVERSION8Decorator() {
    }

    public AINTERFACECDRVERSION8Decorator(AINTERFACECDRVERSION8 ainterfacecdrversion8) {
        this.ainterfacecdrversion8 = ainterfacecdrversion8;
    }

    @XmlElement
    public String getImsi() {
        return getFormattedImsi(ainterfacecdrversion8.getMoSms().getCommonData().getImsi());
    }

//    @XmlElement
//    public String getSmsStartTime() {
//        return getFormattedTime(ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getSmsStartTime());
//    }

}
