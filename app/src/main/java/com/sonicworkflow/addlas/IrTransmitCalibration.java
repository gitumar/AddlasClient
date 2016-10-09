package com.sonicworkflow.addlas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sonicworkflow.addlas.ir_remote_stuff.ButtonNames;
import com.sonicworkflow.addlas.ir_remote_stuff.Hex2Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by umarbradshaw on 11/6/15.
 */
public class IrTransmitCalibration extends ActionBarActivity {

    String fontPath = "fonts/PatuaOne-Regular.ttf"; //path to custom font
    Typeface tf;

    private TextView timeIntervalValue;
    private TextView delayIRsendValue;
    private TextView timeIntervalValueText;
    private TextView delayIRsendValueText;
    private Button tesChannelInterval;
    private CheckBox auto_channel_accept;

    SeekBar timeIntervalBar;
    int timeInterval;
    SeekBar delayValueBar;
    int delay;

    Activity myActivity;


    String hexCode;
    String deviceName;
    String remoteButtonName_one;
    String remoteButtonName_two;

    BufferedReader br = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ir_transmit_calibration);

        myActivity = this;

        timeIntervalValue = (TextView) findViewById(R.id.time_interval_value);
        delayIRsendValue = (TextView) findViewById(R.id.delay_time_value);
        timeIntervalValueText = (TextView) findViewById(R.id.timeIntervalValueText);
        delayIRsendValueText = (TextView) findViewById(R.id.delayIRsendValueText);
        tesChannelInterval = (Button) findViewById(R.id.test_channel_interval);
        auto_channel_accept = (CheckBox) findViewById(R.id.auto_channel_accept);


        //declaring and initializing typeface
        tf = Typeface.createFromAsset(myActivity.getAssets(), fontPath);
        timeIntervalValue.setTypeface(tf);
        delayIRsendValue.setTypeface(tf);
        timeIntervalValueText.setTypeface(tf);
        delayIRsendValueText.setTypeface(tf);

        timeIntervalSeekBar();
        delayIrSendSeekBar();

        //get device the user selected from sharedpreferences. This is the subfolder name in Assets
        deviceName = MySharedPreferences.readString(myActivity,MySharedPreferences.DEVICE_NAME,"Sony");

        tesChannelInterval.setOnClickListener(new View.OnClickListener() {

            int myDelay;

            @Override
            public void onClick(View v) {
                myDelay = MySharedPreferences.readInteger(myActivity, MySharedPreferences.TIME_INTERVAL, 1500);

                //get the hexstring for channel one
                remoteButtonName_one = myHexString(ButtonNames.DIGIT_1);
                new Hex2Duration(myActivity, remoteButtonName_one);

                SystemClock.sleep(myDelay);

                //get the hexstring for channel one
                remoteButtonName_two = myHexString(ButtonNames.DIGIT_2);
                new Hex2Duration(myActivity, remoteButtonName_two);

            }
        });

        auto_channel_accept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MySharedPreferences.writeBoolean(myActivity, MySharedPreferences.AUTO_ACCEPT_CHANNEL, auto_channel_accept.isChecked());
            }
        });

    }

    public void timeIntervalSeekBar(){

        int sharedPrefProgress = MySharedPreferences.readInteger(myActivity,MySharedPreferences.TIME_INTERVAL,1500);
        timeIntervalBar = (SeekBar) findViewById(R.id.interval_between_transmit);
        timeIntervalBar.setMax(2000);
        timeIntervalBar.incrementProgressBy(100);
        timeIntervalBar.setProgress(sharedPrefProgress);
        timeIntervalValue.setText("Milliseconds: " + sharedPrefProgress);
        timeIntervalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int prgress_value;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                prgress_value = progress;
                timeIntervalValue.setText("Milliseconds: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                setTimeInteval(prgress_value);
                //Store selected time interval in SharedPreferences
                MySharedPreferences.writeInteger(myActivity, MySharedPreferences.TIME_INTERVAL, getTimeInteval());
            }
        });

    }
    public void delayIrSendSeekBar(){

        int sharedPrefProgress = MySharedPreferences.readInteger(myActivity,MySharedPreferences.DELAY_TIME,0);

        delayValueBar = (SeekBar) findViewById(R.id.delay_transmit_ir);
        delayValueBar.setMax(300);
        delayValueBar.incrementProgressBy(1);
        delayValueBar.setProgress(sharedPrefProgress);
        delayIRsendValue.setText("Seconds: " + sharedPrefProgress);

        delayValueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int prgress_value;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                prgress_value = progress;
                delayIRsendValue.setText("Seconds: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                setDelay(prgress_value);
                //Store selected time interval in SharedPreferences
                MySharedPreferences.writeInteger(myActivity, MySharedPreferences.DELAY_TIME, getDelay());

            }
        });
    }

    public int getTimeInteval() {
        return timeInterval;
    }

    public void setTimeInteval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ir_calibration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public String myHexString(String buttonname){
        //open file for reading
        try {
            br = new BufferedReader(new InputStreamReader(myActivity.getAssets().open("devices/"+deviceName+buttonname)));
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(IrTransmitCalibration.this, StartActivity.class);
        startActivity(intent);
        finish();

        super.onBackPressed();

    }
}
