package com.sonicworkflow.addlas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.sonicworkflow.addlas.custom_adapters.CheckBoxModel;
import com.sonicworkflow.addlas.custom_adapters.CommercialStatusModel;
import com.sonicworkflow.addlas.custom_adapters.CustomShowListAdapter;
import com.sonicworkflow.addlas.custom_adapters.ImageHolderModel;
import com.sonicworkflow.addlas.custom_adapters.PriorityValueModel;
import com.sonicworkflow.addlas.custom_adapters.ShowListModel;
import com.sonicworkflow.addlas.dialogs.BackupChannelDialog;
import com.sonicworkflow.addlas.dialogs.BadAudibleChannel;
import com.sonicworkflow.addlas.dialogs.BadChannelNumber;
import com.sonicworkflow.addlas.dialogs.BadPriorityDialog;
import com.sonicworkflow.addlas.dialogs.MutingTooManyShows;
import com.sonicworkflow.addlas.dialogs.NotMonitoringAnything;
import com.sonicworkflow.addlas.network_stuff.AppConfig;
import com.sonicworkflow.addlas.network_stuff.ShowJsonParse;
import com.sonicworkflow.addlas.network_stuff.ShowStatusParse;
import com.sonicworkflow.addlas.validation.AudibleValidation;
import com.sonicworkflow.addlas.validation.ChannelNumberValidation;
import com.sonicworkflow.addlas.validation.MonitoringShows;
import com.sonicworkflow.addlas.validation.MuteValidation;
import com.sonicworkflow.addlas.validation.PriorityValueValidation;
import com.sonicworkflow.addlas.validation.isConnectButtonPressed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by umarbradshaw on 9/21/15.
 */
public class StartActivity extends ActionBarActivity {

    String fontPath = "fonts/PatuaOne-Regular.ttf"; //path to custom font
    Typeface tf;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    Button connectButton;
    Button backupChannelButton;
    boolean isConnectedButtonPressedThis = false;

    CustomShowListAdapter showListAdapter;
    ArrayList<ImageHolderModel> hold = new ArrayList<>();
    Bitmap images;
    Bitmap imageCommOn;
    Bitmap imageCommOff;

    ListView listView;
    List<ShowListModel> showListModels;
    ArrayList<String> show_id_keys = new ArrayList<>();

    private ProgressDialog pDialog;

    Context mContext;
    Activity myActivity;
    private static final String TAG = StartActivity.class.getSimpleName();


    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    private Socket socket;
    {
        try{

            socket = IO.socket(AppConfig.SOCKET_URL);
        }catch(URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    //private TextToSpeech engine;
    private String contributor_name = "conNameer";

    //sound pool stuff
    SoundPool mySound;
    int commercialOnID;
    int commercialOffID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        myActivity = this;

        this.mContext = this;

        MonitoringShows.getShow_ID_List_Being_Monitored_Global().clear();

        //connect socket
        socketStartConnect();

        //create bitmap with placeholder image and commercial on off
        images = decodeImage(R.drawable.ic_highlight_off_white_36dp);
        imageCommOff = decodeImage(R.drawable.ic_videocam_off_white_36dp);
        imageCommOn = decodeImage(R.drawable.ic_videocam_white_36dp);

        //declaring and initializing typeface
        tf = Typeface.createFromAsset(mContext.getAssets(), fontPath);


        listView = (ListView) findViewById(R.id.startlistview);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mDrawerList = (ListView)findViewById(R.id.navList);mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //add header to the list when the shows have returned
        LayoutInflater inflaterHeader = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflaterHeader.inflate(R.layout.showlistheader,listView,false);
        listView.addHeaderView(header);
        connectButton= (Button) header.findViewById(R.id.connectbutton);
        backupChannelButton=(Button) header.findViewById(R.id.backupChannel);

        connectButton.setTypeface(tf);
        backupChannelButton.setTypeface(tf);

        //the connect button to start getting messages
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*button will display connect for user to connect. Its initial state is false
                and then set the button connected.
                field to true to give the ShowStatusParse clearance to process messages. When the
                button is press the text changes to disconnected and to give the ShowStatusParse
                clearance to process messages.
                 */
                connectButtonValidation();
            }
        });
        backupChannelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupButtonDialog();
            }
        });


        //set wakelock to prevent app from going to sleep
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
                "MyWakelockTag");
        wakeLock.acquire();

        mySound = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        commercialOnID = mySound.load(mContext, R.raw.commercialon, 1);
        commercialOffID = mySound.load(mContext, R.raw.commercialoff, 1);
    }
    private void addDrawerItems() {
        String[] osArray = { "Get Shows", "Configure","IR Calibration", "Support"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(StartActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 0:
                        //Toast.makeText(StartActivity.this, "Time to get shows!", Toast.LENGTH_SHORT).show();

                        //close drawer
                        mDrawerLayout.closeDrawers();

                        //show progress dialog while getting values
                        pDialog.setMessage("Getting Shows...");
                        showDialog();

                        //make string request to volley
                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,
                                AppConfig.SHOW_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i(TAG, response.toString());
                                hideDialog();

                                if (response.length() > 0) {
                                    try {

                                        //clear original images
                                        hold.clear();
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray results = jsonObject.getJSONArray("results"); //results is the array that holds the current shows data
                                        showListModels = new ArrayList<>();
                                        for (int i = 0; i < results.length(); i++) {

                                            ShowJsonParse singleShowObject = new ShowJsonParse((JSONObject) results.get(i));

                                            showListModels.add(new ShowListModel(singleShowObject.getShowName(), singleShowObject.getShowTime()
                                                    , contributor_name, singleShowObject.getShowID(), singleShowObject.getShowCommercialStatus()));

                                            //initialize array list show id keys for the emitter listener notify
                                            show_id_keys.add(singleShowObject.getShowID());

                                            //initialize comm status model with temp values until manually filled by contributor
                                            CommercialStatusModel.setShowIdToCommercialStatusMap(singleShowObject.getShowID(),
                                                    Integer.parseInt(singleShowObject.getShowCommercialStatus()));

                                            //fill hold list with as many images needed to serve adapter
                                            switch (singleShowObject.getShowCommercialStatus()) {
                                                case "0":
                                                    hold.add(new ImageHolderModel(imageCommOn));
                                                    break;
                                                case "1":
                                                    hold.add(new ImageHolderModel(imageCommOff));
                                                    break;
                                                case "6":
                                                    hold.add(new ImageHolderModel(images));
                                                    break;
                                            }
                                        }

                                        //show list adapter
                                        showListAdapter = new CustomShowListAdapter(getApplicationContext(), R.layout.show_list_single, showListModels);

                                        /*//fill hold with as many images as needed for each show
                                        for (int i = 0; i < results.length(); i++) {
                                            //fill hold list with as many images needed to serve adapter
                                            hold.add(new ImageHolderModel(images));
                                        }*/

                                        //pass my image holder list to the custom adapter
                                        showListAdapter.setImageHolderList(hold);

                                        //set the adapter
                                        listView.setAdapter(showListAdapter);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(StartActivity.this, "No shows right now!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                hideDialog();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                // Posting parameters to login url
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("tag", "get_all_shows");
                                return params;
                            }

                        };
                        //add request to volley request queue
                        Volley.newRequestQueue(myActivity).add(jsonObjectRequest);
                        break;
                    case 1:
                        Toast.makeText(StartActivity.this, "Set your stuff up!", Toast.LENGTH_SHORT).show();

                        //start configuration activity and kill the current activity
                        Intent intent = new Intent(StartActivity.this, DeviceSelection.class);
                        startActivity(intent);
                        finish();

                        break;
                    case 2:

                        //start configuration activity and kill the current activity
                        Intent i = new Intent(StartActivity.this, IrTransmitCalibration.class);
                        startActivity(i);
                        finish();
                        break;
                    case 3:

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Addlas stuff!");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Twitter: @ZeroGuiltLLC , Email:" +
                                "support@zeroguiltllc.com");
                        startActivity(Intent.createChooser(shareIntent, "Contact via"));
                        break;
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Select Item");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            socket.disconnect();
            Intent i = new Intent(getApplicationContext(),
                    LoginActivity.class);
            startActivity(i);
            finish();

        }

        if (id == R.id.share){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out Addlas!");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Your message here");
            startActivity(Intent.createChooser(shareIntent, "Share Via"));

        }

        if(id == R.id.change_password){
            Intent i = new Intent(getApplicationContext(),
                    ChangePassword.class);
            startActivity(i);
            finish();
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // here is the emitter listener that handles the incoming message
    // This method will be responsible for updating status image setting and other events
    private Emitter.Listener handleIncomingMessages = new Emitter.Listener(){

        @Override
        public void call(final Object... args) {

            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    String show_id;
                    String commercial_status;
                    try {
                        message = data.getString("show_id");
                        show_id = data.getString("show_id");
                        commercial_status = data.getString("comm_status");
                        //Log.i(TAG, "Server Message: " + message);

                        // get the position of the key sent from contributor
                        int position = show_id_keys.indexOf(show_id);

                        switch (commercial_status) {
                            case "0":

                                hold.set(position, new ImageHolderModel(imageCommOn));
                                break;
                            case "1":

                                hold.set(position, new ImageHolderModel(imageCommOff));
                                break;
                            case "6":

                                hold.set(position, new ImageHolderModel(images));
                                break;
                        }

                        //pass my image holder list to the custom adapter
                        showListAdapter.setImageHolderList(hold);
                        showListAdapter.notifyDataSetChanged();

                        //set Map to hold show ID and their current commercial status
                        CommercialStatusModel.setShowIdToCommercialStatusMap(show_id, Integer.parseInt(commercial_status));

                        /*send message and context to message parser for distribution only if
                        show is being monitored and the connected button is pressed
                        */
                        if (isConnectButtonPressed.CONNECTED_BUTTON_PRESSED) {
                            if (MonitoringShows.getShow_ID_List_Being_Monitored_Global().contains(show_id)) {
                                new ShowStatusParse(mContext, message, commercial_status, show_id, position);

                                if (isConnectButtonPressed.AUDIBLE_IS_ACTIVE) {
                                    if (Objects.equals(isConnectButtonPressed.AUDIBLE_COMMERCIAL_STATUS, "commOn")) {
                                        mySound.play(commercialOnID, 1, 1, 1, 0, 1);
                                    } else {
                                        mySound.play(commercialOffID, 1, 1, 1, 0, 1);
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });

        }
    };

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //wakeLock.release();
        //the connected button has been pressed
        if(wakeLock.isHeld()){
            wakeLock.release();
        }

        if(socket.connected()){
            socket.disconnect();
        }
        mySound.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //wakeLock.release();
        //socket.disconnect();
        connectButton.setText("Connect");
        if(wakeLock.isHeld()){
            wakeLock.release();
        }

        if(socket.connected()){
            socket.disconnect();
        }
        isConnectButtonPressed.CONNECTED_BUTTON_PRESSED = false;
        isConnectedButtonPressedThis = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(!wakeLock.isHeld()) {
            wakeLock.acquire();
        }

    }

    private boolean validatePriorites(){
        boolean valid;
        ArrayList<Integer> priorityList = new ArrayList<Integer>();
        int[] priorityValueArray;

        //check if Array is null or zero length
        priorityValueArray = PriorityValueModel.getChannelPriority();

        //dummy validate array has zero element value equal 86. This will be false if not null array returns
        if(priorityValueArray[0] != 86) {

            priorityValueArray = new int[PriorityValueModel.getChannelPriority().length];
            priorityValueArray = PriorityValueModel.getChannelPriority();

            //valid returns true if priorities are good returns false if bad
            valid = new PriorityValueValidation().priorityValueVerification(priorityValueArray);
            return valid;
        }else {
            return false;
        }
    }

    public  void toastNeedToDisconnectFirst(){
        Toast.makeText(StartActivity.this, "You are connected!", Toast.LENGTH_SHORT).show();
    }
    //image method to process commercial status image
    private Bitmap decodeImage(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        return bitmap;
    }

    public void connectButtonValidation(){
        if (CheckBoxModel.getShowMonitorStatus().contains(true)) {
            //check if trying to mute more than one show
            if (MuteValidation.muteValidation()) {
                //check if trying to audible more than one show
                if (AudibleValidation.audibleValidation()) {
                    //check if priorities are good
                    if (validatePriorites()) {
                        //check to see if channels are inputed correctly
                        if (ChannelNumberValidation.channelNumberValidation()) {
                            if (isConnectedButtonPressedThis) {
                                //socket.disconnect();
                                connectButton.setText("Connect");
                                //the connected button has been pressed
                                isConnectButtonPressed.CONNECTED_BUTTON_PRESSED = false;
                                isConnectedButtonPressedThis = false;
                                Toast.makeText(StartActivity.this, "You are disconnected!", Toast.LENGTH_LONG).show();
                            } else {
                                connectButton.setText("Disconnect");
                                //socketStartConnect();
                                isConnectButtonPressed.CONNECTED_BUTTON_PRESSED = true;
                                isConnectedButtonPressedThis = true;
                                Toast.makeText(StartActivity.this, "You are connected!\n\nPlease disconnect to make changes.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            BadChannelNumber badChannelNumber = new BadChannelNumber(mContext);
                            badChannelNumber.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            badChannelNumber.show();
                        }
                    } else {
                        //show bad priorities dialog because priorities failed validation
                        BadPriorityDialog badPriorityDialog = new BadPriorityDialog(mContext);
                        badPriorityDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        badPriorityDialog.show();
                    }
                } else {
                    //show bad audible dialog because priorities failed validation
                    BadAudibleChannel badAudibleDialog = new BadAudibleChannel(mContext);
                    badAudibleDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    badAudibleDialog.show();
                }
            } else {
                //bad number of mute
                MutingTooManyShows mutingTooManyShows = new MutingTooManyShows(mContext);
                mutingTooManyShows.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mutingTooManyShows.show();
            }
        } else {
            //show not monitoring anything
            NotMonitoringAnything notMonitoringAnything = new NotMonitoringAnything(mContext);
            notMonitoringAnything.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            notMonitoringAnything.show();
        }
    }

    public void backupButtonDialog(){
        BackupChannelDialog backupChannelDialog = new BackupChannelDialog(mContext);
        backupChannelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        backupChannelDialog.show();
    }

    public void socketStartConnect(){
        //try to connect to socket
        socket.connect();
        socket.on("message", handleIncomingMessages);
    }


}
