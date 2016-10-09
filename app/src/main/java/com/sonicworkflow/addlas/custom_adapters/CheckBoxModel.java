package com.sonicworkflow.addlas.custom_adapters;

import java.util.ArrayList;

/**
 * Created by umarbradshaw on 9/25/15.
 */
public class CheckBoxModel {


    static ArrayList<Boolean> showMonitorStatus;
    static ArrayList<Boolean> audibleChannelStatus;
    static ArrayList<Boolean> muteOnComercialStatus;


    public static void setShowMonitor(ArrayList<Boolean> showMonitorStatus){

        CheckBoxModel.showMonitorStatus = showMonitorStatus;

    }

    public static ArrayList<Boolean> getShowMonitorStatus() {
        return showMonitorStatus;
    }

    public static ArrayList<Boolean> getAudibleChannelStatus() {
        return audibleChannelStatus;
    }

    public static void setAudibleChannelStatus(ArrayList<Boolean> audibleChannelStatus) {
        CheckBoxModel.audibleChannelStatus = audibleChannelStatus;
    }

    public static ArrayList<Boolean> getMuteOnCommercialStatus() {
        return muteOnComercialStatus;
    }

    public static void setMuteOnComercialStatus(ArrayList<Boolean> muteOnComercialStatus) {
        CheckBoxModel.muteOnComercialStatus = muteOnComercialStatus;
    }
}
