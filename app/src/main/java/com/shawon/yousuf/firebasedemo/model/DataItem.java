package com.shawon.yousuf.firebasedemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;
import com.shawon.yousuf.firebasedemo.utils.Constants;

import java.util.HashMap;

/**
 * Created by user on 6/13/2016.
 */
public class DataItem {

    private String name;
    private HashMap<String, Object> timestampLastChanged;
    private HashMap<String, Object> timestampCreated;

    /**
     * Required public constructor
     */
    public DataItem() {
    }

    public DataItem(String name, HashMap<String, Object> timestampCreated) {
        this.name = name;
        this.timestampCreated = timestampCreated;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    @JsonIgnore
    public long getTimestampLastChangedLong() {

        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    @JsonIgnore
    public long getTimestampCreatedLong() {
        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    @Override
    public String toString() {
        return name;
    }
}
