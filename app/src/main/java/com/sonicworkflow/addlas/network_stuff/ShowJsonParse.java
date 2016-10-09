package com.sonicworkflow.addlas.network_stuff;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by umarbradshaw on 9/23/15.
 */
public class ShowJsonParse  {
    JSONObject singleShowObject;
    String showName;
    String showTime;
    String showID;
    String showCommercialStatus;


    public ShowJsonParse(JSONObject singleShowObject) throws JSONException {

        this.singleShowObject = singleShowObject;

        setShowName(singleShowObject.getString("show_name"));
        setShowTime(singleShowObject.getString("show_time"));
        setShowID(singleShowObject.getString("show_id"));
        setShowCommercialStatus(singleShowObject.getString("commercialStatus"));

    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID) {
        this.showID = showID;
    }

    public String getShowCommercialStatus() {
        return showCommercialStatus;
    }

    public void setShowCommercialStatus(String showCommercialStatus) {
        this.showCommercialStatus = showCommercialStatus;
    }
}