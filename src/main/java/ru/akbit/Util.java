package ru.akbit;

import examples.schema.MAPCDRVERSION13;
import org.apache.commons.beanutils.PropertyUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by kraetsky on 20.06.2017.
 */
public class Util {
    public static String getFormattedValue(MAPCDRVERSION13 mapCdr, List<String> configFields) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < configFields.size(); i++) {
            String field = configFields.get(i);
            field = formatToCamelCase(field);
            if (!field.contains(".")) {
                sb.append(PropertyUtils.getProperty(mapCdr, field));
            } else {
                sb.append(PropertyUtils.getNestedProperty(mapCdr, field));
            }

            if (i != configFields.size() - 1) {
                sb.append("\t");
            }
        }
        sb.append("\r\n");

        return sb.toString();
    }

    public static String getFormattedValueOld(MAPCDRVERSION13 mapCdr, List<String> configFields) {
        StringBuilder sb = new StringBuilder();
        if (configFields.contains("seizureTime")) {
            sb.append(mapCdr.getSeizureTime());
        }
        if (configFields.contains("stopTime")) {
            sb.append("\t");
            sb.append(mapCdr.getStopTime());
        }
        if (configFields.contains("octetsSent")) {
            sb.append("\t");
            sb.append(mapCdr.getOctetsSent());
        }
        if (configFields.contains("octetsReceived")) {
            sb.append("\t");
            sb.append(mapCdr.getOctetsReceived());
        }
        if (configFields.contains("msusSent")) {
            sb.append("\t");
            sb.append(mapCdr.getMsusSent());
        }
        if (configFields.contains("msusReceived")) {
            sb.append("\t");
            sb.append(mapCdr.getMsusReceived());
        }
        if (configFields.contains("opc")) {
            sb.append("\t");
            sb.append(mapCdr.getMtp3Data().getOpc());
        }
        if (configFields.contains("dpc")) {
            sb.append("\t");
            sb.append(mapCdr.getMtp3Data().getDpc());
        }
        if (configFields.contains("ni")) {
            sb.append("\t");
            sb.append(mapCdr.getMtp3Data().getNi());
        }
        if (configFields.contains("origGlobalTitle")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getOrigGlobalTitle());
        }
        if (configFields.contains("termGlobalTitle")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getTermGlobalTitle());
        }
        if (configFields.contains("calledNoA")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getCalledNoA());
        }
        if (configFields.contains("callingdNoA")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getCallingNoA());
        }
        if (configFields.contains("calledNP")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getCalledNP());
        }
        if (configFields.contains("callingNP")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getCallingNP());
        }
        if (configFields.contains("calledSSN")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getCalledSSN());
        }
        if (configFields.contains("callingSSN")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getCallingSSN());
        }
        if (configFields.contains("callingAddrInd")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getCalledAddrInd());
        }
        if (configFields.contains("calledAddrInd")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getCalledAddrInd());
        }
        if (configFields.contains("callingTransType")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getCallingTransType());
        }
        if (configFields.contains("calledTransType")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getCalledTransType());
        }
        if (configFields.contains("firstTermGlobalTitle")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getFirstTermGlobalTitle());
        }
        if (configFields.contains("firstCalledNoA")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getFirstCalledNoA());
        }
        if (configFields.contains("firstCalledNP")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getFirstCalledNP());
        }
        if (configFields.contains("firstCalledTransType")) {
            sb.append("\t");
            sb.append(mapCdr.getSccpData().getFirstCalledTransType());
        }
        if (configFields.contains("otid")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getTcapData().getOtid());
        }
        if (configFields.contains("firstOpcode")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getTcapData().getFirstOpcode());
        }
        if (configFields.contains("lastOpcode")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getTcapData().getLastOpcode());
        }
        if (configFields.contains("firstTcapMessageType")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getTcapData().getFirstTcapMessageType());
        }
        if (configFields.contains("lastTcapMessageType")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getTcapData().getLastTcapMessageType());
        }
        if (configFields.contains("lastTcapMessageType")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getTcapData().getLastTcapMessageType());
        }
        if (configFields.contains("firstMessageComponentType")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getTcapData().getFirstMessageComponentType());
        }
        if (configFields.contains("lastMessageComponentType")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getTcapData().getLastMessageComponentType());
        }
        if (configFields.contains("imsi")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getImsi());
        }
        if (configFields.contains("nrOfTriplets")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getNrOfTriplets());
        }
        if (configFields.contains("map-version")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getMapVersion());
        }
        if (configFields.contains("numberOfRequestedVectors")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getAuthenticationData().getNumberOfRequestedVectors());
        }
        if (configFields.contains("numberOfTriplets")) {
            sb.append("\t");
            sb.append(mapCdr.getMapData().getAuthenticationData().getNumberOfTriplets());
        }

        if (configFields.contains("numberOfMsusWithoutLinkPC")) {
            sb.append("\t");
            sb.append(mapCdr.getLinkStatistics().getNumberOfMsusWithoutLinkPC());
        }
        if (configFields.contains("src")) {
            sb.append("\t");
            sb.append(mapCdr.getLinkDataList().getLinkItems().get(0).getLink().getIpv4().getSrc());
        }
        if (configFields.contains("dst")) {
            sb.append("\t");
            sb.append(mapCdr.getLinkDataList().getLinkItems().get(0).getLink().getIpv4().getDst());
        }
        if (configFields.contains("packets")) {
            sb.append("\t");
            sb.append(mapCdr.getLinkDataList().getLinkItems().get(0).getVolume().getPackets());
        }
        if (configFields.contains("octets")) {
            sb.append("\t");
            sb.append(mapCdr.getLinkDataList().getLinkItems().get(0).getVolume().getOctets());
        }
        if (configFields.contains("src")) {
            sb.append("\t");
            sb.append(mapCdr.getLinkDataList().getLinkItems().get(1).getLink().getIpv4().getSrc());
        }
        if (configFields.contains("dst")) {
            sb.append("\t");
            sb.append(mapCdr.getLinkDataList().getLinkItems().get(1).getLink().getIpv4().getDst());
        }
        if (configFields.contains("packets")) {
            sb.append("\t");
            sb.append(mapCdr.getLinkDataList().getLinkItems().get(1).getVolume().getPackets());
        }
        if (configFields.contains("octets")) {
            sb.append("\t");
            sb.append(mapCdr.getLinkDataList().getLinkItems().get(1).getVolume().getOctets());
        }

        sb.append("\n");
        return sb.toString();


    }

    private static String formatToCamelCase(String field) {
        if (field.contains("-")) {
            StringBuilder sb = new StringBuilder();
            String[] arr = field.split("-");
            sb.append(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                sb.append(arr[i].substring(0, 1).toUpperCase());
                sb.append(arr[i].substring(1, arr[i].length()));
            }
            return sb.toString();
        } else {
            return field;
        }
    }

    public static List<String> getFieldsFromConfigFile(String filePath) throws IOException {

        Stream<String> stream = Files.lines(Paths.get(filePath));
        List<String> list = new ArrayList<String>();
        stream.forEach(line -> {
            list.add(line);
        });

        return list;
    }

}
