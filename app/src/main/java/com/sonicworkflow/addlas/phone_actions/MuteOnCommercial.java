package com.sonicworkflow.addlas.phone_actions;

import android.content.Context;

import com.sonicworkflow.addlas.MySharedPreferences;
import com.sonicworkflow.addlas.ir_remote_stuff.ButtonNames;
import com.sonicworkflow.addlas.ir_remote_stuff.Hex2Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by umar bradshaw on 9/24/15.
 */
public class MuteOnCommercial {

    String isCommercial;
    static boolean isMuted = false;
    int isCommercialIntValue;

    Context mContext;

    String MuteHexCode;
    String deviceName;
    BufferedReader br = null;

    public  MuteOnCommercial (Context mContext, String isCommercial){

        this.mContext = mContext;
        //this will be either "1" or "0"
        this.isCommercial = isCommercial;

        //isCommercialIntValue = Integer.parseInt(isCommercial);

        //do file hexcode getting
        fileHexCodeIO();

        // fire the mute button
        new Hex2Duration(mContext, MuteHexCode);

    }

    public void fileHexCodeIO(){
        //get device the user selected from shared preferences. This is the subfolder name in Assets
        deviceName = MySharedPreferences.readString(mContext, MySharedPreferences.DEVICE_NAME, "Samsung");

        //open file for reading
        try {
            br = new BufferedReader(new InputStreamReader(mContext.getAssets().open("devices/"+deviceName+ ButtonNames.MUTE)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            MuteHexCode = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
