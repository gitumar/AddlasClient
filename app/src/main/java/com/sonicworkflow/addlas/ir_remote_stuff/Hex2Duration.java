package com.sonicworkflow.addlas.ir_remote_stuff;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by umarbradshaw on 9/25/15.
 *
 * this class will convert pronto hex values into Ir duration patterns given a context and hex string
 */
public class Hex2Duration {

    String hexString;
    List<String> hexList;
    List<String> hexDecimalCountList;
    int[] decimalListPattern;
    int[] irPattern;
    int[] decimalIrPattern;
    int frequency;
    int pulses;
    int count;
    int duration;

    Context mContext;

    private ConsumerIrManager mCIR;

    //this constructor will have a string hexString parameter
    public Hex2Duration(Context mContext, String hexString){

        this.mContext = mContext;
        this.hexString = hexString;
        //initialized hex list with hex string values as elements
        hexList = new ArrayList<>(Arrays.asList(hexString.split(" ")));
        hexDecimalCountList = new ArrayList<>(Arrays.asList(hexString.split(" ")));

        // Get a reference to the ConsumerIrManager
        mCIR = (ConsumerIrManager)mContext.getSystemService(Context.CONSUMER_IR_SERVICE);

        //set the frequency by parse secod element in hexString to int base 16 and multiply
        setFrequency((int)(1000000/((Integer.parseInt(hexList.get(1),16))*0.241246)));
        pulses = 1000000/getFrequency();


        //.remove shifts elements up, so we delete first element four times
        for(int i =0; i<4;i++){
            hexList.remove(0);
            hexDecimalCountList.remove(0);
        }

        //initialize array with size of hex values
        irPattern = new int[hexList.size()];
        decimalIrPattern = new int[hexList.size()];

        //fill hexlist with duration values and decimal list with decimal count CRASHES HERE
        for(int i = 0; i<hexList.size(); i++){
            count = Integer.parseInt(hexList.get(i),16);
            duration = count*pulses;

            hexList.set(i,Integer.toString(duration));
            hexDecimalCountList.set(i,Integer.toString(count));

        }

        //fill irPattern array while removing all hexlist values and only getting first element on
        //each iteration
        for(int i = 0; i<irPattern.length; i++){
            irPattern[i] = Integer.parseInt(hexList.get(0));
            hexList.remove(0);

            decimalIrPattern[i] = Integer.parseInt(hexDecimalCountList.get(0));
            hexDecimalCountList.remove(0);
        }

        //set the ir pattern
        setIrPattern(irPattern);
        setDecimalIrPattern(decimalIrPattern);


        //check if they fave an ir transmitter
        if(!mCIR.hasIrEmitter()){
            return;
        }

        //get the version of andriod in use
        if(Build.VERSION.SDK_INT==19){
            int lastIdx = Build.VERSION.RELEASE.lastIndexOf(".");
            int VERSION_MR = Integer.valueOf(Build.VERSION.RELEASE.substring(lastIdx+1));
            if(VERSION_MR<3){
                //Before version Android 4.4.2
                mCIR.transmit(getFrequency(),getDecimalIrPattern());
            }else {
                //Later version of Android like lollipop
                mCIR.transmit(getFrequency(), getIrPattern());
            }
        }else {
            //Later version of Android like lollipop
            mCIR.transmit(getFrequency(), getIrPattern());
        }


    }

    public int[] getIrPattern() {
        return irPattern;
    }

    public void setIrPattern(int[] irPattern) {
        this.irPattern = irPattern;
    }

    public int[] getDecimalIrPattern() {
        return decimalIrPattern;
    }

    public void setDecimalIrPattern(int[] decimalIrPattern) {
        this.decimalIrPattern = decimalIrPattern;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
