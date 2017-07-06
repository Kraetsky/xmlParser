package ru.akbit;

import examples.schema.AINTERFACECDRVERSION8;
import examples.schema.RpDestAddress;
import examples.schema.SmsDataChild;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

import static ru.akbit.Util.getFormattedImsi;
import static ru.akbit.Util.getFormattedTime;


@XmlRootElement(name = "A-INTERFACE-CDR-VERSION8")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AINTERFACECDRVERSION8DecoratorAllFields implements Decorator {

    private AINTERFACECDRVERSION8 ainterfacecdrversion8;
    private SmsDataChild child;

    public AINTERFACECDRVERSION8DecoratorAllFields() {
    }

    public AINTERFACECDRVERSION8DecoratorAllFields(AINTERFACECDRVERSION8 ainterfacecdrversion8, SmsDataChild child) {
        this.ainterfacecdrversion8 = ainterfacecdrversion8;
        this.child = child;
    }

    @XmlElement
    public String getImsi() {
        if (ainterfacecdrversion8.getMoSms().getCommonData().getImsi() == null) {
            return null;
        }
        return getFormattedImsi(ainterfacecdrversion8.getMoSms().getCommonData().getImsi());
    }

    @XmlElement
    public String getSmsStartTime() {

        return getFormattedTime(child.getSmsStartTime());
    }

    @XmlElement
    public String getSmsEndTime() {
        if (child.getSmsEndTime() == null) {
            return null;
        }
        return getFormattedTime(child.getSmsEndTime());
    }

    @XmlElement(name = "mess-reference")
    public short getMessReference() {
        return child.getMessReference();
    }

    @XmlElement
    public String getSubmitTime() {
        if (child.getSubmitTime() == null) {
            return null;
        }
        return getFormattedTime(child.getSubmitTime());
    }

    @XmlElement
    public String getRpAckSMSCTime() {
        if (child.getRpAckSMSCTime() == null) {
            return null;
        }
        return getFormattedTime(child.getRpAckSMSCTime());
    }

    @XmlElement
    public String getIeIdentifier() {
        if (child.getIeIdentifier()== null) {
            return null;
        }
        return child.getIeIdentifier();
    }

    @XmlElement
    public String getConcatRef() {
        if (child.getConcatRef()== null) {
            return null;
        }
        return child.getConcatRef();
    }

    @XmlElement
    public String getConcatMax() {
        if (child.getConcatMax()== null) {
            return null;
        }

        return child.getConcatMax();
    }

    @XmlElement
    public String getConcatSeq() {
        if (child.getConcatSeq()== null) {
            return null;
        }
        return child.getConcatSeq();
    }

    @XmlElement
    public short getSmsLength() {
        return child.getSmsLength();
    }

    @XmlElement
    public short getSmsMsgType() {
        return child.getSmsMsgType();
    }

    @XmlElement(name = "end-time")
    public String getEndTimeP() {
        return getFormattedTime(ainterfacecdrversion8.getMoSms().getCommonData().getEndTime());
    }

    @XmlElement
    public short getCmServiceType() {
        return ainterfacecdrversion8.getMoSms().getCommonData().getCmServiceType();
    }

    @XmlElement
    public String getRpDestAdressString() {
        if (child.getRpDestAddress() == null) {
            return null;
        } else {
            return getFormattedImsi(child.getRpDestAddress().getAddressString());
        }
    }

    @XmlElement
    public String getTpDestAdressString() {
        if (child.getTpDestAddress() == null) {
            return null;
        }
        return getFormattedImsi(String.valueOf(child.getTpDestAddress().getAddressString()));
    }

}
