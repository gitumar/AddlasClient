package com.sonicworkflow.addlas.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sonicworkflow.addlas.MySharedPreferences;
import com.sonicworkflow.addlas.R;
import com.sonicworkflow.addlas.ir_remote_stuff.ButtonNames;
import com.sonicworkflow.addlas.ir_remote_stuff.Hex2Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by umarbradshaw on 9/26/15.
 */
public class RemoteTestDialog extends Dialog implements DialogInterface.OnCancelListener,DialogInterface.OnClickListener,
        DialogInterface.OnDismissListener{

    String fontPath = "fonts/PatuaOne-Regular.ttf"; //path to custom font
    Typeface tf;

    Context mContext;


    String hexCode;
    String deviceName;
    String remoteButtonName;
    BufferedReader br = null;


    //Button testDevice;

    TextView testSelectedRemote;
    ImageButton numberone;
    ImageButton numbertwo;
    ImageButton numberthree;
    ImageButton numberfour;
    ImageButton numberfive;
    ImageButton numbersix;
    ImageButton numberseven;
    ImageButton numbereight;
    ImageButton numbernine;
    ImageButton numberzero;
    ImageButton mutebutton;
    ImageButton okbutton;

    public RemoteTestDialog(Context context) {
        super(context);

        this.mContext= context;


        this.setContentView(R.layout.remote_test_dialog);
        //this.setTitle("Test Selected Remote");
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //get device the user selected from sharedpreferences. This is the subfolder name in Assets
        deviceName = MySharedPreferences.readString(mContext, MySharedPreferences.DEVICE_NAME, "Sony");


        //declaring and initializing typeface
        tf = Typeface.createFromAsset(mContext.getAssets(), fontPath);
        //testDevice = (Button) findViewById(R.id.dialogTestButton);

        testSelectedRemote = (TextView)findViewById(R.id.test_selected_remote_text);
        testSelectedRemote.setTypeface(tf);

        numberone = (ImageButton) findViewById(R.id.numberone);
        numbertwo = (ImageButton) findViewById(R.id.numbertwo);
        numberthree = (ImageButton) findViewById(R.id.numberthree);
        numberfour = (ImageButton) findViewById(R.id.numberfour);
        numberfive = (ImageButton) findViewById(R.id.numberfive);
        numbersix = (ImageButton) findViewById(R.id.numbersix);
        numberseven = (ImageButton) findViewById(R.id.numberseven);
        numbereight = (ImageButton) findViewById(R.id.numbereight);
        numbernine = (ImageButton) findViewById(R.id.numbernine);
        numberzero = (ImageButton) findViewById(R.id.numberzero);
        mutebutton = (ImageButton) findViewById(R.id.mutebutton);
        okbutton = (ImageButton) findViewById(R.id.myokbutton);

        mutebutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mutebutton.setImageResource(R.drawable.mutebuttonselected);
                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.MUTE);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        mutebutton.setImageResource(R.drawable.mutebutton);

                }

                return false;
            }
        });

        numberone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        numberone.setImageResource(R.drawable.numberoneselected);

                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.DIGIT_1);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        numberone.setImageResource(R.drawable.numberone);

                }

                return false;
            }
        });
        numbertwo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        numbertwo.setImageResource(R.drawable.numbertwoselected);

                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.DIGIT_2);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        numbertwo.setImageResource(R.drawable.numbertwo);

                }

                return false;
            }
        });
        numberthree.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        numberthree.setImageResource(R.drawable.numberthreeselected);

                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.DIGIT_3);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        numberthree.setImageResource(R.drawable.numberthree);

                }

                return false;
            }
        });
        numberfour.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        numberfour.setImageResource(R.drawable.numberfourselected);

                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.DIGIT_4);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        numberfour.setImageResource(R.drawable.numberfour);

                }

                return false;
            }
        });
        numberfive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        numberfive.setImageResource(R.drawable.numberfiveselected);

                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.DIGIT_5);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        numberfive.setImageResource(R.drawable.numberfive);

                }

                return false;
            }
        });
        numbersix.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        numbersix.setImageResource(R.drawable.numbersixselected);

                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.DIGIT_6);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        numbersix.setImageResource(R.drawable.numbersix);

                }

                return false;
            }
        });
        numberseven.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        numberseven.setImageResource(R.drawable.numbersevenselected);

                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.DIGIT_7);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        numberseven.setImageResource(R.drawable.numberseven);

                }

                return false;
            }
        });
        numbereight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        numbereight.setImageResource(R.drawable.numbereightselected);

                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.DIGIT_8);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        numbereight.setImageResource(R.drawable.numbereight);

                }

                return false;
            }
        });
        numbernine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        numbernine.setImageResource(R.drawable.numbernineselected);

                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.DIGIT_9);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        numbernine.setImageResource(R.drawable.numbernine);

                }

                return false;
            }
        });
        numberzero.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        numberzero.setImageResource(R.drawable.numberzeroselected);

                        //get the hexstring for the mute button
                        remoteButtonName = myHexString(ButtonNames.DIGIT_0);
                        new Hex2Duration(mContext, remoteButtonName);

                        return true;
                    case MotionEvent.ACTION_UP:
                        numberzero.setImageResource(R.drawable.numberzero);

                }

                return false;
            }
        });

        //onClick for accepted device
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                closeOptionsMenu();
            }
        });
    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {


    }

    public String myHexString(String buttonname){
        //open file for reading
        try {
            br = new BufferedReader(new InputStreamReader(mContext.getAssets().open("devices/"+deviceName+buttonname)));
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

        return hexCode;
    }
}
