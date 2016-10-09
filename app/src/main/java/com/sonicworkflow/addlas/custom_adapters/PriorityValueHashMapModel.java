package com.sonicworkflow.addlas.custom_adapters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by umarbradshaw on 12/11/15.
 */
public class PriorityValueHashMapModel {

    static Map<String, Integer> showIdToPriorityMap = new HashMap<>();

    public static Map<String, Integer> getShowIdToPriorityMap() {

        return showIdToPriorityMap;


    }

    public static void setShowIdToPriorityMap(String showId, int showPriority) {

        //if the map contains the show find the position and set the position equal to new value
        PriorityValueHashMapModel.showIdToPriorityMap.put(showId, showPriority);

    }

}
