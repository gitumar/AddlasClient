package com.sonicworkflow.addlas.ir_remote_stuff;

/**
 * Created by umarbradshaw on 9/26/15.
 */
public class ButtonNames {

    public static final String POWER = "/b_1.txt"; // 1
    public static final String NAV_OK = "/b_12.txt"; // 12
    public static final String MUTE = "/b_14.txt"; // 14
    public static final String DIGIT_0 = "/b_16.txt"; // 16
    public static final String DIGIT_1 = "/b_17.txt"; // 17
    public static final String DIGIT_2 = "/b_18.txt"; // 18
    public static final String DIGIT_3 = "/b_19.txt"; // 19
    public static final String DIGIT_4 = "/b_20.txt"; // 20
    public static final String DIGIT_5 = "/b_21.txt"; // 21
    public static final String DIGIT_6 = "/b_22.txt"; // 22
    public static final String DIGIT_7 = "/b_23.txt"; // 23
    public static final String DIGIT_8 = "/b_24.txt"; // 24
    public static final String DIGIT_9 = "/b_25.txt"; // 25


    public String buttonName;


    public String getButtonName(String buttonName){
        this.buttonName = buttonName;

        String buttonToReturn = null;

        switch (buttonName){
            case "0":
                buttonToReturn = DIGIT_0;
                break;
            case "1":
                buttonToReturn = DIGIT_1;
                break;
            case "2":
                buttonToReturn = DIGIT_2;
                break;
            case "3":
                buttonToReturn = DIGIT_3;
                break;
            case "4":
                buttonToReturn = DIGIT_4;
                break;
            case "5":
                buttonToReturn = DIGIT_5;
                break;
            case "6":
                buttonToReturn = DIGIT_6;
                break;
            case "7":
                buttonToReturn = DIGIT_7;
                break;
            case "8":
                buttonToReturn = DIGIT_8;
                break;
            case "9":
                buttonToReturn = DIGIT_9;
                break;

        }
        return buttonToReturn;
    }

}
