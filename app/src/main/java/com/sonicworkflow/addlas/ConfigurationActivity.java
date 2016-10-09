package com.sonicworkflow.addlas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sonicworkflow.addlas.ir_remote_stuff.Hex2Duration;

/*
*
* this was going to get called but this class is never called
* */

public class ConfigurationActivity extends ActionBarActivity {

    ListView setupOptions;
    String[] options;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        options = new String[]{"Smart Setup","Manual Setup"};

        setupOptions = (ListView) findViewById(R.id.setup_options);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
        setupOptions.setAdapter(mAdapter);

        setupOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        //selected smart setup
                        //test hex string mute button on LG TV
                        String hexString = "0000 006d 0024 0000 0157 00ac 0015 0016 0015 0016 0015 0041 0015 0016 0015 0016 0015 0016 0015 0016 0015 0016 0015 0041 0015 0041 0015 0016 0015 0041 0015 0041 0015 0041 0015 0041 0015 0041 0015 0041 0015 0016 0015 0016 0015 0041 0015 0016 0015 0016 0015 0016 0015 0016 0015 0016 0015 0041 0015 0041 0015 0016 0015 0041 0015 0041 0015 0041 0015 0041 0015 0689 0157 0056 0015 0e94";
                        new Hex2Duration(getApplicationContext(),hexString);

                        break;
                    case 1:
                        //selected manual setup
                        //start configuration activity and kill the current activity
                        Intent intent = new Intent(ConfigurationActivity.this,DeviceSelection.class);
                        startActivity(intent);
                        //finish();

                        break;


                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration, menu);
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

        finish();
        Intent intent = new Intent(ConfigurationActivity.this, StartActivity.class);
        startActivity(intent);
    }

}
