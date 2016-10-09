package com.sonicworkflow.addlas.phone_actions;

import android.content.Context;

import com.sonicworkflow.addlas.validation.isConnectButtonPressed;

/**
 * Created by umarbradshaw on 9/25/15.
 */
public class AudibleChannelStatus {

    String isCommercial;
    static boolean audioCommercialOff = false;
    int isCommercialIntValue;

    Context mContext;


    /*SoundPool mySound;
    int commercialOnID;
    int commercialoffID;*/


    public AudibleChannelStatus(Context mContext,String isCommercial){

        this.mContext = mContext;
        //1 for is commercial and 0 for not, should match Arraylist position
        this.isCommercial = isCommercial;

        isCommercialIntValue = Integer.parseInt(isCommercial);

       /* mySound = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        commercialOnID = mySound.load(mContext, R.raw.commercialon, 1);
        commercialoffID = mySound.load(mContext, R.raw.commercialoff, 1);*/



        /*if is on commercial fire channelName off if it has a channel name.
        * if not on commercial anymore and mute button is muted then fire
        * the mute button set isMute false*/
        if (isCommercialIntValue == 1) {
            //Todo: fire channel off audio clip from assets
            //mySound.play(commercialoffID, 1, 1, 1, 0, 1);
            //set audioCommercialOff to true after button fire
            isConnectButtonPressed.AUDIBLE_COMMERCIAL_STATUS = "commOff";
            //audioCommercialOff = true;

        } else {
            //Todo: fire channel on audio clip from assets
            //mySound.play(commercialOnID, 1, 1, 1, 0, 1);
            //audioCommercialOff = false;

            isConnectButtonPressed.AUDIBLE_COMMERCIAL_STATUS = "commOn";
        }

        //release resources after sound plays
        releaseMySoundPoolResources();

    }

    public void releaseMySoundPoolResources(){
        //mySound.release();
    }
}
