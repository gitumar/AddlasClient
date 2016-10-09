package com.sonicworkflow.addlas.custom_adapters;

/**
 * Created by umarbradshaw on 9/21/15.
 */
public class ShowListModel {

    String showName = null;
    String showTime = null;
    String contributor_name = null;
    String showID = null;
    String showCommercialStatus = null;

    public  ShowListModel(){

    }

    public ShowListModel(String showName, String showTime, String contributor_name, String showID, String showCommercialStatus){

        this.showName = showName;
        this.showTime = showTime;
        this.contributor_name = contributor_name;
        this.showID = showID;
        this.showCommercialStatus = showCommercialStatus;

    }

    public String getShowName() {
        return showName;
    }

    public String getShowTime() {
        return showTime;
    }

    public String getShowCommercialStatus() {
        return showCommercialStatus;
    }

    public String getContributor_name() {
        return contributor_name;
    }

    public String getShowID() {
        return showID;
    }
}

