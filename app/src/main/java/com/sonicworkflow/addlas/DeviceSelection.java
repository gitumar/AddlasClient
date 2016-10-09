package com.sonicworkflow.addlas;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sonicworkflow.addlas.dialogs.RemoteTestDialog;

import java.io.IOException;


public class DeviceSelection extends ActionBarActivity {

    String devicePath = "devices";
    String [] deviceList;
    Context mContext;
    ListView listOfDevices;

    AssetManager am;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_selection);

        this.mContext = this;
        am = this.getAssets();

        //try to get the names of the device folders in assets/devices
        try {
            deviceList = am.list(devicePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //listview that holds all of the device names
        listOfDevices = (ListView)findViewById(R.id.devicelist);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deviceList);
        listOfDevices.setAdapter(mAdapter);

        listOfDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DeviceSelection.this, deviceList[position], Toast.LENGTH_SHORT).show();

                //Store selected device name in SharedPreferences
                MySharedPreferences.writeString(mContext,MySharedPreferences.DEVICE_NAME,deviceList[position]);

                //open a dialog to test the remote selected
                RemoteTestDialog dialog = new RemoteTestDialog(mContext);
                dialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(DeviceSelection.this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
