package com.sonicworkflow.addlas.validation;

import android.text.TextUtils;

import com.sonicworkflow.addlas.custom_adapters.ChannelNumberModel;
import com.sonicworkflow.addlas.custom_adapters.CheckBoxModel;

import java.util.ArrayList;

/**
 * Created by umarbradshaw on 10/1/15.
 */
public class ChannelNumberValidation {

    public ChannelNumberValidation() {
    }

    public static boolean channelNumberValidation(){

        //need indexes of of the shows being monitored
        ArrayList<Integer> indexShowsBeingMonitored = new ArrayList<Integer>();
        ArrayList<Boolean> channelList = new ArrayList<Boolean>();
        channelList = CheckBoxModel.getShowMonitorStatus();

        if(!MuteValidation.isAvoidChannelNumberValidation() && !AudibleValidation.isAvoidChannelNumberValidation()) {

        //if(MuteValidation.isAvoidChannelNumberValidation()) {
            String[] channelNumbers;
            channelNumbers = ChannelNumberModel.getChannelNumbers();
            //if the channel number string array is blank channel the edittexts are null
            if (channelNumbers[0] == "BlankChannel") {
                return false;
            }

            int[] onlyShowsBeingMonitoredNumber;

            //put index values of shows being monitored into a list to validate only shows being monitored
            for (int i = 0; i < channelList.size(); i++) {
                if (channelList.get(i) == true) {
                    indexShowsBeingMonitored.add(i);
                }
            }

            //if any of the shows being monitored have a blank channel number
            for (int i = 0; i < indexShowsBeingMonitored.size(); i++) {
                if (TextUtils.isEmpty(channelNumbers[indexShowsBeingMonitored.get(i)])) {
                    return false;
                }
            }
        }

        return true;
    }
}
