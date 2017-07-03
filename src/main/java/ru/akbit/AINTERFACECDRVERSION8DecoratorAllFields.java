package ru.akbit;

import examples.schema.AINTERFACECDRVERSION8;
import examples.schema.RpDestAddress;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

import static ru.akbit.Util.getFormattedImsi;
import static ru.akbit.Util.getFormattedTime;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class AINTERFACECDRVERSION8DecoratorAllFields implements Decorator {
    private AINTERFACECDRVERSION8 ainterfacecdrversion8;

    public AINTERFACECDRVERSION8DecoratorAllFields() {
    }

    public AINTERFACECDRVERSION8DecoratorAllFields(AINTERFACECDRVERSION8 ainterfacecdrversion8) {
        this.ainterfacecdrversion8 = ainterfacecdrversion8;
    }

    @XmlElement
    public String getImsi() {
        return getFormattedImsi(ainterfacecdrversion8.getMoSms().getCommonData().getImsi());
    }

    @XmlElement
    public Date getSmsStartTime() {
        return getFormattedTime(ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getSmsStartTime());
    }

    @XmlElement
    public Date getSmsEndTime() {
        return getFormattedTime(ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getSmsEndTime());
    }

    @XmlElement(name = "mess-reference")
    public byte getMessReference() {
        return ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getMessReference();
    }

    @XmlElement
    public Date getSubmitTime() {
        return getFormattedTime(ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getSubmitTime());
    }

    @XmlElement
    public Date getRpAckSMSCTime() {
        return getFormattedTime(ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getRpAckSMSCTime());
    }

    @XmlElement
    public byte getIeIdentifier() {
        return ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getIeIdentifier();
    }

    @XmlElement
    public short getConcatRef() {
        return ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getConcatRef();
    }

    @XmlElement
    public short getConcatMax() {
        return ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getConcatMax();
    }

    @XmlElement
    public short getConcatSeq() {
        return ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getConcatSeq();
    }

    @XmlElement
    public short getSmsLength() {
        return ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getSmsLength();
    }

    @XmlElement
    public short getSmsMsgType() {
        return ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getSmsMsgType();
    }

    @XmlElement(name = "end-time")
    public Date getEndTimeP() {
        return getFormattedTime(ainterfacecdrversion8.getMoSms().getCommonData().getEndTime());
    }

    @XmlElement
    public short getCmServiceType() {
        return ainterfacecdrversion8.getMoSms().getCommonData().getCmServiceType();
    }

    @XmlElement
    public String getRpDestAdressString() {
        if (ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getRpDestAddress() == null) {
            return "";
        } else {
            return ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getRpDestAddress().getAddressString();
        }
    }

    @XmlElement
    public String getTpDestAdressString() {
        if (ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getTpDestAddress() == null) {
            return "";
        }
        return getFormattedImsi(ainterfacecdrversion8.getMoSms().getSmsData().getSmsDataChild().getTpDestAddress().getAddressString());
    }

}
