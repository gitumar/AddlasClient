package com.sonicworkflow.addlas.custom_adapters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by umarbradshaw on 9/28/15.
 */
public class ChannelNumberModel {

    static String[] channelNumbers;
    static String[] dummyValidationArray = new String[]{"BlankChannel"};
    static String tempChannelNumber;
    static Map<String, String> showIdToChannelNumberMap = new HashMap<String, String>();


    public ChannelNumberModel(String[] channelNumbers){

        this.channelNumbers = channelNumbers;
    }
    public static String[] getChannelNumbers() {

        if(channelNumbers == null){
            return dummyValidationArray;
        }

        return channelNumbers;
    }

    public static String getTempChannelNumber() {
        return tempChannelNumber;
    }

    public static void setTempChannelNumber(String tempChannelNumber) {
        ChannelNumberModel.tempChannelNumber = tempChannelNumber;
    }

    public static Map<String, String> getShowIdToChannelNumberMap() {
        return showIdToChannelNumberMap;
    }

    public static void setShowIdToChannelNumberMap(String showId, String showChannel) {

        ChannelNumberModel.showIdToChannelNumberMap.put(showId,showChannel);
    }
}
