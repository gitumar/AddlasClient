package com.sonicworkflow.addlas.custom_adapters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by umarbradshaw on 12/12/15.
 */
public class ChannelNumberHashMapModel {

    static Map<String, String> showIdToChannelNumberMap = new HashMap<String, String>();
    static String tempChannelNumber;


    public static Map<String, String> getShowIdToChannelNumberMap() {
        return showIdToChannelNumberMap;
    }

    public static void setShowIdToChannelNumberMap(String showId, String showChannel) {

        ChannelNumberHashMapModel.showIdToChannelNumberMap.put(showId,showChannel);
    }

    public static String getTempChannelNumber() {
        return tempChannelNumber;
    }

    public static void setTempChannelNumber(String tempChannelNumber) {
        ChannelNumberHashMapModel.tempChannelNumber = tempChannelNumber;
    }
}
