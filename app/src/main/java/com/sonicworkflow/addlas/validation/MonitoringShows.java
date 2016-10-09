package com.sonicworkflow.addlas.validation;

import java.util.ArrayList;

/**
 * Created by umarbradshaw on 12/10/15.
 */
public class MonitoringShows {

    static ArrayList<String> show_ID_List_Being_Monitored_Global = new ArrayList<>();

    public static ArrayList<String> getShow_ID_List_Being_Monitored_Global() {
        return show_ID_List_Being_Monitored_Global;
    }

    public static void add_Show_ID_List_Being_Monitored_Global(String show_ID_List_Being_Monitored_Global) {
        MonitoringShows.show_ID_List_Being_Monitored_Global.add(show_ID_List_Being_Monitored_Global);
    }
    public static void remove_Show_ID_List_Being_Monitored_Global(String show_ID_List_Being_Monitored_Global) {
        MonitoringShows.show_ID_List_Being_Monitored_Global.remove(show_ID_List_Being_Monitored_Global);
    }
}
