package com.sonicworkflow.addlas.ir_remote_stuff;

import android.content.Context;
import android.os.SystemClock;

import com.sonicworkflow.addlas.MySharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by umarbradshaw on 9/29/15.
 */
public class ParseDigitsAndFire {

    Context mContext;
    String channelNumber;
    char[] cArray;
    BufferedReader br = null;

    ButtonNames myButtonNames;

    String deviceName;
    String hexCode;


    public ParseDigitsAndFire(final Context mContext, String channelNumber){

        this.mContext = mContext;
        this.channelNumber = channelNumber;

        this.cArray = channelNumber.toCharArray();

        //get device the user selected from sharedpreferences. This is the subfolder name in Assets
        deviceName = MySharedPreferences.readString(mContext, MySharedPreferences.DEVICE_NAME, "Sony");
        myButtonNames = new ButtonNames();

        for (int i = 0; i <cArray.length ; i++) {

            //get the button file name
            String channelCharacterString = myButtonNames.getButtonName(String.valueOf(cArray[i]));

            //open file for reading
            try {
                br = new BufferedReader(new InputStreamReader(mContext.getAssets().open("devices/"+deviceName+channelCharacterString)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                hexCode = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //fire IR
            new Hex2Duration(mContext,hexCode);

            //wait for 1.2 second
            SystemClock.sleep(MySharedPreferences.readInteger(mContext,MySharedPreferences.TIME_INTERVAL,1500));

        }

        /*this is the "accept the channel button" allow the user and option to fire this button
        first check if use wants to have the nav button press for auto navigate
         */
        if(MySharedPreferences.readBoolean(mContext,MySharedPreferences.AUTO_ACCEPT_CHANNEL,false)) {
            //open file for reading
            try {
                br = new BufferedReader(new InputStreamReader(mContext.getAssets().open("devices/" + deviceName + ButtonNames.NAV_OK)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                hexCode = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //fire NAV OK IR
            new Hex2Duration(mContext, hexCode);

        }

    }


}
