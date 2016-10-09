package com.sonicworkflow.addlas.custom_adapters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by umarbradshaw on 12/11/15.
 */
public class CommercialStatusModel {

    static Map<String, Integer> showIdToCommercialStatusMap = new HashMap<String, Integer>();

    /*public CommercialStatusModel() {
    }*/

    public static Map<String, Integer> getShowIdToCommercialStatusMap() {
        return showIdToCommercialStatusMap;
    }

    public static void setShowIdToCommercialStatusMap(String showId, int commercialStatus) {
        CommercialStatusModel.showIdToCommercialStatusMap.put(showId,commercialStatus);
    }
}
