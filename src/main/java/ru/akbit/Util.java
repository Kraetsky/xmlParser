package ru.akbit;


import examples.schema.AINTERFACECDRVERSION8;
import examples.schema.SmsData;
import examples.schema.SmsDataChild;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by kraetsky on 20.06.2017.
 */
public class Util {
    private static final Logger log = LoggerFactory.getLogger(UnmarshallerAndWriter.class);

    public static boolean isValidSmsDataChild(AINTERFACECDRVERSION8 mapCdr, SmsDataChild child) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NestedNullException {
        if (PropertyUtils.getNestedProperty(child, "smsMsgType").toString().equals("1") &&
                PropertyUtils.getNestedProperty(child, "submitTime") != null) {
            return true;


            //        if (PropertyUtils.getNestedProperty(child, "smsMsgType").toString().equals("1") &&
//                PropertyUtils.getNestedProperty(child, "submitTime") != null &&
//                PropertyUtils.getNestedProperty(child, "concatRef") == null) {
//            return true;
//        }
//        if (PropertyUtils.getNestedProperty(child, "smsMsgType").toString().equals("1") &&
//                PropertyUtils.getNestedProperty(child, "submitTime") != null &&
//                PropertyUtils.getNestedProperty(child, "concatRef") != null &&
//                PropertyUtils.getNestedProperty(child, "concatSeq") != null &&
//                PropertyUtils.getNestedProperty(child, "concatMax") != null) {
//            return true;
//        }
//        if (PropertyUtils.getNestedProperty(child, "smsMsgType").toString().equals("1") &&
//                PropertyUtils.getNestedProperty(child, "submitTime") != null &&
//                PropertyUtils.getNestedProperty(child, "rpAckSMSCTime") != null &&
//                PropertyUtils.getNestedProperty(child, "concatRef") == null) {
//            return true;
//        }
//        if (PropertyUtils.getNestedProperty(child, "smsMsgType").toString().equals("1") &&
//                PropertyUtils.getNestedProperty(child, "submitTime") != null &&
//                PropertyUtils.getNestedProperty(child, "rpAckSMSCTime") != null &&
//                PropertyUtils.getNestedProperty(child, "concatRef") != null &&
//                PropertyUtils.getNestedProperty(child, "concatSeq") != null &&
//                PropertyUtils.getNestedProperty(child, "concatMax") != null) {
//            return true;
        } else {
            return false;
        }
    }

    public static boolean isSmsExist(AINTERFACECDRVERSION8 mapCdr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NestedNullException {
        if (mapCdr.getMoSms() != null &&
                mapCdr.getMoSms().getSmsData() != null &&
                mapCdr.getMoSms().getSmsData().getSmsDataChild() != null &&
                mapCdr.getMoSms().getSmsData().getSmsDataChild().size() != 0) {
            return true;
        }
        log.debug("data child is null");

        return false;


    }

    public static boolean isSmsOfType4(AINTERFACECDRVERSION8 mapCdr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NestedNullException {
        return mapCdr.getMoSms() != null &&
                (mapCdr.getMoSms().getSmsData() == null || mapCdr.getMoSms().getSmsData().getSmsDataChild().size() == 0) &&
                (mapCdr.getMoSms().getCommonData().getCmServiceType().equals("4"));
    }

    public static boolean isSmsValidSecondVariant(AINTERFACECDRVERSION8 mapCdr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NullPointerException, NestedNullException {
        SmsData smsData = mapCdr.getMoSms().getSmsData();
        if (smsData == null) {
            log.debug("sms data is null");
            return false;
        }
        if (smsData.getSmsDataChild() == null) {
            log.debug("data child is null");
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
            LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);
            return formattedDateTime;
        } else {
            return null;
        }
    }
    public static boolean isSmsStartTimeValid(SmsDataChild child) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (PropertyUtils.getNestedProperty(child, "smsStartTime") == null) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse("2017-07-05 08:00:00", formatter);
        LocalDateTime currentSmsTime = LocalDateTime.parse(getFormattedTime(PropertyUtils.getNestedProperty(child, "smsStartTime").toString()), formatter);
        long seconds = Duration.between(startTime, currentSmsTime).getSeconds();
        if (seconds <= 900) {
            return true;
        } else return false;
    }
    public static boolean isRecordClosingTimeValid(AINTERFACECDRVERSION8 mapCdr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (PropertyUtils.getNestedProperty(mapCdr, "moSms.commonData.recordClosingTime") == null){
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse("2017-07-05 08:00:00", formatter);
        LocalDateTime currentTime = LocalDateTime.parse(getFormattedTime(PropertyUtils.getNestedProperty(mapCdr, "moSms.commonData.recordClosingTime").toString()), formatter);
        long seconds = Duration.between(startTime, currentTime).getSeconds();
        if (seconds <= 900) {
            return true;
        } else return false;
    }


}
