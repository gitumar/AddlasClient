package com.sonicworkflow.addlas.validation;

import com.sonicworkflow.addlas.custom_adapters.CheckBoxModel;

/**
 * Created by umarbradshaw on 2/2/16.
 */
public class AudibleValidation {

    static int count;
    static boolean avoidChannelNumberValidation;

    public static boolean audibleValidation() {
        //check how many mute on commercials are set to true
        for (boolean audible : CheckBoxModel.getAudibleChannelStatus()) {
            if(audible) count++;
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
        AudibleValidation.avoidChannelNumberValidation = avoidChannelNumberValidation;
    }


}
