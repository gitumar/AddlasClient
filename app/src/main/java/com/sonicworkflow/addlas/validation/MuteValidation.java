package com.sonicworkflow.addlas.validation;

import com.sonicworkflow.addlas.custom_adapters.CheckBoxModel;

/**
 * Created by umarbradshaw on 12/10/15.
 */
public class MuteValidation {

    static int count;
    static boolean avoidChannelNumberValidation;

    public static boolean muteValidation() {
        //check how many mute on commercials are set to true
        for (boolean muted : CheckBoxModel.getMuteOnCommercialStatus()) {
            if(muted) count++;
        }
        if(count>0){
            setAvoidChannelNumberValidation(false);
        }else {
            setAvoidChannelNumberValidation(true);
        }
        if(count<2){
            count = 0;
            return true;
        }else {
            count = 0;
            return false;
        }
    }

    public static boolean isAvoidChannelNumberValidation() {
        return avoidChannelNumberValidation;
    }

    public static void setAvoidChannelNumberValidation(boolean avoidChannelNumberValidation) {
        MuteValidation.avoidChannelNumberValidation = avoidChannelNumberValidation;
    }
}
