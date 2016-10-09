package com.sonicworkflow.addlas.ir_remote_stuff;

import android.content.Context;

import com.sonicworkflow.addlas.MySharedPreferences;
import com.sonicworkflow.addlas.custom_adapters.ChannelNumberHashMapModel;
import com.sonicworkflow.addlas.custom_adapters.CheckBoxModel;
import com.sonicworkflow.addlas.custom_adapters.CommercialStatusModel;
import com.sonicworkflow.addlas.custom_adapters.PriorityValueHashMapModel;
import com.sonicworkflow.addlas.custom_adapters.PriorityValueModel;
import com.sonicworkflow.addlas.network_stuff.ShowStatusParse;
import com.sonicworkflow.addlas.validation.MonitoringShows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by umarbradshaw on 9/28/15.
 */
public class ChannelToChange {

    static int[] priorityValueArray;
    static char[] isCommercial;
    int backupLoopCounter;

    Context mContext;

    ArrayList<Boolean> showMonitorStatus = new ArrayList<>();


    ArrayList<String> getShow_ID_List_Being_Monitored;
    ArrayList<String> priorityOrderedShowIdMonitoringList = new ArrayList<>();
    Map<String, String> showIdToChannelNumberMap = new HashMap<String, String>();
    Map<String, Integer> showIdToPriorityMap = new HashMap<String, Integer>();
    Map<String, Integer> showIdToCommercialStatusMap = new HashMap<String, Integer>();
    //String showIDWithPriorityOne = null;

    public ChannelToChange(Context mContext){

        this.mContext = mContext;
        this.isCommercial = new char[ShowStatusParse.getIsCommercialStatic().length];
        //this.channelNumbers = new String[ChannelNumberModel.getChannelNumbers().length];
        this.priorityValueArray = new int[PriorityValueModel.getChannelPriority().length];

        //get boolean array from CheckBox data model true if is monitoring
        this.showMonitorStatus = CheckBoxModel.getShowMonitorStatus();

        //get arrays needing to walk
        priorityValueArray = PriorityValueModel.getChannelPriority();
        isCommercial = ShowStatusParse.getIsCommercialStatic();
        //channelNumbers = ChannelNumberModel.getChannelNumbers();

        //get shows being monitored from global monitoring List and HashMaps
        getShow_ID_List_Being_Monitored = MonitoringShows.getShow_ID_List_Being_Monitored_Global();
        showIdToChannelNumberMap = ChannelNumberHashMapModel.getShowIdToChannelNumberMap();
        showIdToPriorityMap = PriorityValueHashMapModel.getShowIdToPriorityMap();
        showIdToCommercialStatusMap = CommercialStatusModel.getShowIdToCommercialStatusMap();

        //TODO: New way of doing things
        //new way of doing it with Key Value mapping

        //initialize list with show ID in priority order
        for (int i = 0; i < showIdToPriorityMap.size() ; i++) {
            for(Map.Entry<String,Integer> showEntry : showIdToPriorityMap.entrySet()){
                if(showEntry.getValue().equals(i+1)){
                    priorityOrderedShowIdMonitoringList.add(showEntry.getKey());
                }
            }
        }

        //find priority Number 1 and set the current channel to that value
        if (ChannelNumberHashMapModel.getTempChannelNumber() == null) {

            ChannelNumberHashMapModel.setTempChannelNumber(showIdToChannelNumberMap
                    .get(priorityOrderedShowIdMonitoringList.get(0)));
        }


        //1 is a commercial 0 is not a commercial
      loop: for (int i = 0; i < priorityOrderedShowIdMonitoringList.size() ; i++) {

          //test what the commercial status are in priority order
            switch (showIdToCommercialStatusMap.get(priorityOrderedShowIdMonitoringList.get(i))){
                //Case 0 means the show is not on commercial
                case 0:
                    //if the channel is not the current channel that is selected
                    if(!ChannelNumberHashMapModel.getTempChannelNumber().equals(
                            showIdToChannelNumberMap.get(priorityOrderedShowIdMonitoringList.get(i)))){

                        new ParseDigitsAndFire(mContext,showIdToChannelNumberMap
                                .get(priorityOrderedShowIdMonitoringList.get(i)));

                        ChannelNumberHashMapModel.setTempChannelNumber(showIdToChannelNumberMap
                                .get(priorityOrderedShowIdMonitoringList.get(i)));

                        break loop;
                    }
                    break loop;
                case 1:
                    break;

            }
         backupLoopCounter++;
        }
        if(backupLoopCounter == priorityOrderedShowIdMonitoringList.size()){

            String backupChannel = MySharedPreferences.readString(mContext,
                    MySharedPreferences.BACKUP_CHANNEL, null);

            if(backupChannel != null){

                new ParseDigitsAndFire(mContext,backupChannel);

                ChannelNumberHashMapModel.setTempChannelNumber(backupChannel);
            }
        }
    }




}
