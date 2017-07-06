package ru.akbit;


import examples.schema.AINTERFACECDRVERSION8;
import examples.schema.SmsData;
import examples.schema.SmsDataChild;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by kraetsky on 20.06.2017.
 */
public class Util {

    public static boolean isValidSmsDataChild(SmsDataChild child) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NestedNullException{
        if (PropertyUtils.getNestedProperty(child, "smsMsgType").toString().equals("1") &&
                PropertyUtils.getNestedProperty(child, "submitTime") != null &&
                PropertyUtils.getNestedProperty(child, "concatRef") == null) {
            return true;
        }
        if (PropertyUtils.getNestedProperty(child, "smsMsgType").toString().equals("1") &&
                PropertyUtils.getNestedProperty(child, "submitTime") != null &&
                PropertyUtils.getNestedProperty(child, "concatRef") != null &&
                PropertyUtils.getNestedProperty(child, "concatSeq") != null &&
                PropertyUtils.getNestedProperty(child, "concatMax") != null) {
            return true;
        }
        if (PropertyUtils.getNestedProperty(child, "smsMsgType").toString().equals("1") &&
                PropertyUtils.getNestedProperty(child, "submitTime") != null &&
                PropertyUtils.getNestedProperty(child, "rpAckSMSCTime") != null &&
                PropertyUtils.getNestedProperty(child, "concatRef") == null) {
            return true;
        }
        if (PropertyUtils.getNestedProperty(child, "smsMsgType").toString().equals("1") &&
                PropertyUtils.getNestedProperty(child, "submitTime") != null &&
                PropertyUtils.getNestedProperty(child, "rpAckSMSCTime") != null &&
                PropertyUtils.getNestedProperty(child, "concatRef") != null &&
                PropertyUtils.getNestedProperty(child, "concatSeq") != null &&
                PropertyUtils.getNestedProperty(child, "concatMax") != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidSms(AINTERFACECDRVERSION8 mapCdr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NestedNullException {
        if (mapCdr.getMoSms() != null && mapCdr.getMoSms().getSmsData().getSmsDataChild() != null) {
            return true;
        }
        System.out.println("data child is null");
        return false;
    }

    public static boolean isSmsValidSecond(AINTERFACECDRVERSION8 mapCdr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NullPointerException, NestedNullException {
        SmsData smsData = mapCdr.getMoSms().getSmsData();
        if (smsData == null) {
            System.out.println("sms data  is null");
            return false;
        }
        if (smsData.getSmsDataChild() == null) {
            System.out.println("data child is null");
            return false;
        }


        if (PropertyUtils.getNestedProperty(mapCdr, "moSms.smsData.smsDataChild.submitTime") == null && (PropertyUtils.getNestedProperty(mapCdr, "moSms.commonData.cmServiceType").toString()).equals("4")) {
            return true;
        } else return false;

    }

    public static String getFormattedImsi(String imsi) {
        StringBuilder result = new StringBuilder(15);

        Arrays.stream(imsi.split(" "))
                .forEach(part -> {
                    for (int i = 0; i < part.length(); i += 2) {
                        result.append(part.charAt(i + 1))
                                .append(part.charAt(i));
                    }
                });
        String tempResult = result.toString();
        return tempResult.substring(0, tempResult.length() - 1);
    }

    public static String getFormattedTime(String time) {
        if (time != null) {
            String[] arr = time.split(" ");
            StringBuilder sb = new StringBuilder();
            sb.append(arr[0])
                    .append(arr[1]);

            Long midResult = Long.parseLong(sb.toString(), 16);
            sb.delete(0, sb.length());
            sb.append(midResult.toString());


            Date date = new Date(Long.parseLong(sb.toString()));

            return date.toString();
        } else {
            return null;
        }
    }


}
