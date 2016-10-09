package com.sonicworkflow.addlas.custom_adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.sonicworkflow.addlas.R;
import com.sonicworkflow.addlas.validation.MonitoringShows;
import com.sonicworkflow.addlas.validation.isConnectButtonPressed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umarbradshaw on 9/21/15.
 */
public class CustomShowListAdapter extends ArrayAdapter<ShowListModel> {

    String fontPath = "fonts/PatuaOne-Regular.ttf"; //path to custom font
    Typeface tf;

    private final Context context;
    private final List<ShowListModel> showList;
    private String show_id;
    private Bitmap commercialImageStatus;
    private ArrayList<Boolean> showMonitorStatus = new ArrayList<Boolean>();
    private ArrayList<Boolean> audibleChannelStatus = new ArrayList<Boolean>();
    private ArrayList<Boolean> muteOnComercialStatus = new ArrayList<Boolean>();

    ArrayList<ImageHolderModel> list;

    CheckBoxModel checkBoxModel;
    PriorityValueHashMapModel priorityValueHashMapModel;
    int numChannelsMonitoring = 0;

    String[] channelNumbers; //initialize array of channel numbers
    int[] channelPriority;


    public CustomShowListAdapter(Context context, int resource, List<ShowListModel> objects) {
        super(context, resource, objects);

        this.showList = objects;
        this.context = context;
        checkBoxModel = new CheckBoxModel();
        priorityValueHashMapModel = new PriorityValueHashMapModel();

        //declaring and initializing typeface
        tf = Typeface.createFromAsset(context.getAssets(), fontPath);

        //initialize holder list of images
        list = getImageHolderList();

        for (int i = 0; i < showList.size(); i++) {
            //fill list with the number of show objects
            showMonitorStatus.add(false);
            audibleChannelStatus.add(false);
            muteOnComercialStatus.add(false);

        }
        //pass initial showMonitorStatus array
        checkBoxModel.setShowMonitor(showMonitorStatus);
        checkBoxModel.setMuteOnComercialStatus(muteOnComercialStatus);
        checkBoxModel.setAudibleChannelStatus(audibleChannelStatus);

        //set sizes of channel number and priorities
        this.channelNumbers = new String[showList.size()];
        this.channelPriority = new int[showList.size()];

    }

    static class ViewHolder {
        TextView showName;

        TextView showTime;

        CheckBox monitorShow;
        CheckBox audioChannelStatus;
        CheckBox mutOncommercial;

        EditText channelNumber;

        ImageView showStatusImage;

        NumberPicker channelPriority;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        final ViewHolder holder;

        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.show_list_single, null);
            holder = new ViewHolder();

            //setup the viewholder
            holder.showName = (TextView) convertView.findViewById(R.id.show_name);

            holder.showTime = (TextView) convertView.findViewById(R.id.showtime);


            //declare checkboxes
            holder.monitorShow = (CheckBox) convertView.findViewById(R.id.monitor_show);
            holder.audioChannelStatus = (CheckBox) convertView.findViewById(R.id.audio_channel_status);
            holder.mutOncommercial = (CheckBox) convertView.findViewById(R.id.mute_on_commercial);

            //number picker for Channel priority Ranking
            holder.channelPriority = (NumberPicker)convertView.findViewById(R.id.priorityNumber);
            holder.channelPriority.setWrapSelectorWheel(true);

            //set mute and audible to disabled until monitor is checked
            //holder.audioChannelStatus.setEnabled(false);
            //holder.mutOncommercial.setEnabled(false);

            holder.channelNumber = (EditText) convertView.findViewById(R.id.channel_number);
            //holder.channelNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

            holder.showStatusImage = (ImageView) convertView.findViewById(R.id.show_status_image);


            //Set Typface
            holder.showName.setTypeface(tf);
            holder.showTime.setTypeface(tf);
            holder.monitorShow.setTypeface(tf);
            holder.audioChannelStatus.setTypeface(tf);
            holder.mutOncommercial.setTypeface(tf);
            holder.channelNumber.setTypeface(tf);


            //holder.showStatusImage.

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();

            //clear onChangeListner
            holder.monitorShow.setOnCheckedChangeListener(null);


            holder.audioChannelStatus.setOnCheckedChangeListener(null);
            holder.mutOncommercial.setOnCheckedChangeListener(null);

        }

        //put the correct image in the correct position
        //problem here is that position is greater than number of list items
        ImageHolderModel imageHolderModel = list.get(position);
        holder.showStatusImage.setImageBitmap(imageHolderModel.getImage());

        //holder.showStatusImage.setImageBitmap(getCommercialImageStatus());



        holder.channelPriority.setValue(channelPriority[position]);

        //Here we set text based on position of the in listview the get methods are being called
        //from the ShowListModel class
        holder.showName.setText(showList.get(position).getShowName());

        holder.showTime.setText(showList.get(position).getShowTime());


        holder.monitorShow.setFocusable(false);
        holder.monitorShow.setTag(position);
        holder.monitorShow.setChecked(showMonitorStatus.get(position));



        holder.audioChannelStatus.setEnabled(false);
        holder.audioChannelStatus.setChecked(audibleChannelStatus.get(position));

        holder.mutOncommercial.setEnabled(false);
        holder.mutOncommercial.setChecked(muteOnComercialStatus.get(position));

        //initialize size of number picker
        holder.channelPriority.setMaxValue(showList.size());



        //Todo find way to disable gui when connected
        //disable gui when user is connected
        /*if(isConnectButtonPressed.CONNECTED_BUTTON_PRESSED){
            holder.channelNumber.setEnabled(false);
            holder.channelPriority.setEnabled(false);
            holder.monitorShow.setEnabled(false);
        }*/



        holder.monitorShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //if(!isConnectButtonPressed.CONNECTED_BUTTON_PRESSED) {
                    if (isChecked) {
                        showMonitorStatus.set(position, true);
                        holder.mutOncommercial.setEnabled(true);
                        holder.audioChannelStatus.setEnabled(true);
                        numChannelsMonitoring++; //increment number of channels to monitor
                        setNumChannelsMonitoring(numChannelsMonitoring);
                        checkBoxModel.setShowMonitor(showMonitorStatus);

                        //add the show ID to list of shows being monitored
                        MonitoringShows.add_Show_ID_List_Being_Monitored_Global(showList.get(position).showID);

                    } else {
                        showMonitorStatus.set(position, false);
                        checkBoxModel.setShowMonitor(showMonitorStatus);
                        numChannelsMonitoring--; //decrement number of channels to monitor
                        setNumChannelsMonitoring(numChannelsMonitoring);
                        //holder.channelPriority.setMaxValue(getNumChannelsMonitoring());

                        //uncheck and disable stuff if not monitoring it
                        if (holder.mutOncommercial.isChecked()) {
                            holder.mutOncommercial.toggle();
                        }
                        if (holder.audioChannelStatus.isChecked()) {
                            holder.audioChannelStatus.toggle();
                        }
                        holder.mutOncommercial.setEnabled(false);
                        holder.audioChannelStatus.setEnabled(false);

                        //remove the show ID to list of shows being monitored
                        MonitoringShows.remove_Show_ID_List_Being_Monitored_Global(showList.get(position).showID);
                    }
                //}else {

                //}
            }
        });


        holder.audioChannelStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    audibleChannelStatus.set(position, true);
                    isConnectButtonPressed.AUDIBLE_IS_ACTIVE = true;
                }else {
                    audibleChannelStatus.set(position, false);
                    isConnectButtonPressed.AUDIBLE_IS_ACTIVE = false;
                }

            }
        });
        holder.mutOncommercial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    muteOnComercialStatus.set(position, true);
                } else {
                    muteOnComercialStatus.set(position, false);
                }

            }
        });

        //Todo: make the number picker remove priorities that are assigned to other channels.
        holder.channelPriority.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                channelPriority[position]=newVal;

                new PriorityValueModel(channelPriority);
                //put the showID and Priority in a hash map
                //PriorityValueModel.setShowIdToPriorityMap(show_id,newVal);

                PriorityValueHashMapModel.setShowIdToPriorityMap(showList.get(position).showID, newVal);

            }
        });

        //Track Edittext changes and store the values in the channelNumbers Arrays
        holder.channelNumber.setText(channelNumbers[position]);
        //holder.channelNumber.clearFocus();
        holder.channelNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    channelNumbers[position] = holder.channelNumber.getText().toString();

                    new ChannelNumberModel(channelNumbers);

                    //put the showID and ChannelNumber in a hash map
                    //ChannelNumberModel.setShowIdToChannelNumberMap(showList.get(position).showID, holder.channelNumber.getText().toString());
                    ChannelNumberHashMapModel.setShowIdToChannelNumberMap(showList.get(position).showID, holder.channelNumber.getText().toString());
                    holder.channelNumber.clearFocus();
                }
            }
        });


        //holder.monitorShow.setChecked(status.get(position));
        return convertView;

    }

    //getters to get which check boxes are checked
    public ArrayList<Boolean> getShowMonitorStatus() {
        return showMonitorStatus;
    }

    public ArrayList<Boolean> getAudibleChannelStatus() {
        return audibleChannelStatus;
    }

    public ArrayList<Boolean> getMuteOnComercialStatus() {
        return muteOnComercialStatus;
    }

    public int getNumChannelsMonitoring() {
        return numChannelsMonitoring;
    }

    public void setNumChannelsMonitoring(int numChannelsMonitoring) {
        this.numChannelsMonitoring = numChannelsMonitoring;
    }

    public void setImageHolderList(ArrayList<ImageHolderModel> list){
        this.list = list;
    }

    public ArrayList<ImageHolderModel> getImageHolderList(){
        return list;
    }

    /*private Bitmap decodeImage(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(myContext.getResources(), res);
        return bitmap;
    }*/

    public Bitmap getCommercialImageStatus() {
        return commercialImageStatus;
    }

    public void setCommercialImageStatus(Bitmap commercialImageStatus) {
        this.commercialImageStatus = commercialImageStatus;
    }
}
