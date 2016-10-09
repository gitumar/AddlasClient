package com.sonicworkflow.addlas.validation;

import com.sonicworkflow.addlas.custom_adapters.CheckBoxModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by umarbradshaw on 9/30/15.
 */
public class PriorityValueValidation {

    //int[] priorityValueArray;

    public PriorityValueValidation() {

    }
    public PriorityValueValidation( int[] priorityValueArray) {

        //not sure if this is being used anywhere?
        //priorityValueVerification(priorityValueArray);
    }

    public static boolean priorityValueVerification(int[] priorityValueArray){

        //need indexes of of the shows being monitored
        ArrayList<Integer> indexShowsBeingMonitored = new ArrayList<Integer>();
        ArrayList<Boolean> priorityList = new ArrayList<Boolean>();
        priorityList = CheckBoxModel.getShowMonitorStatus();

        int[] onlyShowsBeingMonitoredPriorityValue;

        //put index values of shows being monitored into a list to use validate only shows being monitored
        for (int i = 0; i < priorityList.size() ; i++) {
            if(priorityList.get(i)==true){
                indexShowsBeingMonitored.add(i);
            }
        }
        //if any of the shows being monitored has and priority of 0 validation failed
        for (int i = 0; i < indexShowsBeingMonitored.size() ; i++) {
            if (priorityValueArray[indexShowsBeingMonitored.get(i)]==0) {
                return false;
            }
        }

        onlyShowsBeingMonitoredPriorityValue = new int[indexShowsBeingMonitored.size()];

        //fill new int[] with the priorities that are being monitored
        for (int i = 0; i <indexShowsBeingMonitored.size() ; i++) {
            onlyShowsBeingMonitoredPriorityValue[i] = priorityValueArray[indexShowsBeingMonitored.get(i)];
        }

        //now to see if there are any duplicates values in the array
        Set<Integer> prioritySet = new HashSet<Integer>();
        for (int i : onlyShowsBeingMonitoredPriorityValue){
            if (prioritySet.contains(i)){
                return false;
            }
            prioritySet.add(i);
        }

        /*now see if the priorities are in sequential order ie if there is a priority
        *4 there should also be a priority 1, 2, and 3. If not the priorities are not
        *sequential.
        */

        SortedSet<Integer> set=new TreeSet<Integer>();
        for(int i = 0; i<onlyShowsBeingMonitoredPriorityValue.length;i++){
            set.add(onlyShowsBeingMonitoredPriorityValue[i]);
        }
        //this list now has the array in sorted order
        List<Integer> list=new ArrayList<Integer>(set);
        for(int i = 0; i<list.size();i++){
            if(list.get(i) != i+1){

                return false;
            }
        }
        return true;

    }


}
