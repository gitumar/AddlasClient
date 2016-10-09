package com.sonicworkflow.addlas.network_stuff;

import android.content.Context;

import com.sonicworkflow.addlas.custom_adapters.CheckBoxModel;
import com.sonicworkflow.addlas.ir_remote_stuff.ChannelToChange;
import com.sonicworkflow.addlas.phone_actions.AudibleChannelStatus;
import com.sonicworkflow.addlas.phone_actions.MuteOnCommercial;
import com.sonicworkflow.addlas.validation.AudibleValidation;
import com.sonicworkflow.addlas.validation.MonitoringShows;
import com.sonicworkflow.addlas.validation.MuteValidation;
import com.sonicworkflow.addlas.validation.isConnectButtonPressed;

import java.util.ArrayList;

/**
 * Created by umarbradshaw on 9/24/15.
 */
public class ShowStatusParse {

    char[] isCommercial;
    static char[] isCommercialStatic;
    String commercialStatus;
    String show_ID;
    int position;
    ArrayList<Boolean> showMonitorStatus = new ArrayList<>();
    ArrayList<Boolean> muteOnComercialStatus = new ArrayList<>();
    ArrayList<Boolean> audibleChannelStatus = new ArrayList<>();

    Context mContext;

    public ShowStatusParse(Context mContext, String isCommercial, String commercialStatus
            , String show_ID, int position) {

        this.mContext = mContext;
        this.commercialStatus = commercialStatus;
        this.show_ID = show_ID;
        this.position = position;

        //isCommercial is the array with the "1" or "0" for commercial or not
        this.isCommercial = isCommercial.toCharArray();
        this.isCommercialStatic = isCommercial.toCharArray();


        //get boolean array from CheckBox data model
        this.showMonitorStatus = CheckBoxModel.getShowMonitorStatus();
        this.muteOnComercialStatus = CheckBoxModel.getMuteOnCommercialStatus();
        this.audibleChannelStatus = CheckBoxModel.getAudibleChannelStatus();

        /* check which shows are being monitored and for those being monitored check if Mute
         * on commercial is checked or audible channel status is checked the auxiliary channel
         * is not being monitored. I need to get this based on there being a value in the edit
         * text field*/
        if (isConnectButtonPressed.CONNECTED_BUTTON_PRESSED) {

            //mute the device if the user has the show
            if(!MuteValidation.isAvoidChannelNumberValidation()) {
                doMuteOnCommercial();
                return;
            }

            if(!AudibleValidation.isAvoidChannelNumberValidation()) {
                doAudibleCommercial();
                return;
            }

            new ChannelToChange(mContext);
        }
    }

    public void doMuteOnCommercial(){
            //if this is true, the show is being monitored by the user
            if (MonitoringShows.getShow_ID_List_Being_Monitored_Global().contains(show_ID)) {
                //check if the show is to be muted by the position
                if (muteOnComercialStatus.get(position)) {
                    new MuteOnCommercial(mContext, commercialStatus);
                }
            }
    }

    public void doAudibleCommercial(){

        //if this is true, the show is being monitored by the user
        if (MonitoringShows.getShow_ID_List_Being_Monitored_Global().contains(show_ID)) {
            //check if the show is to be muted by the position
            if (audibleChannelStatus.get(position)) {
                new AudibleChannelStatus(mContext, commercialStatus);
            }
        }

    }

    public static char[] getIsCommercialStatic() {
        return isCommercialStatic;
    }
}