package com.sonicworkflow.addlas.custom_adapters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by umarbradshaw on 9/28/15.
 */
public class PriorityValueModel {

    static int[] channelPriority;
    static int[] dummyValidatearray;
    static Map<String, Integer> showIdToPriorityMap = new HashMap<>();

    public  PriorityValueModel(int[] channelPriority){
        this.channelPriority = channelPriority;
    }


    public static int[] getChannelPriority() {

        //check if channel priority is null if so return 86 in first element of dummy array
        if(channelPriority == null){
            dummyValidatearray = new int[1];
            dummyValidatearray[0] = 86;
            return dummyValidatearray;
        }else {
            return channelPriority;
        }

    }

    public static Map<String, Integer> getShowIdToPriorityMap() {
        return showIdToPriorityMap;
    }

    public static void setShowIdToPriorityMap(String showId, int showPriority) {

        //if the map contains the show find the position and set the position equal to new value
        PriorityValueModel.showIdToPriorityMap.put(showId, showPriority);

    }
}
