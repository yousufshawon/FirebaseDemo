package com.shawon.yousuf.firebasedemo.utils;

import com.shawon.yousuf.firebasedemo.BuildConfig;

/**
 * Created by user on 6/13/2016.
 */
public class Constants {

    /**
     * Constants for Firebase URL
     */
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;

    public static final String FIREBASE_LOCATION_DATA_LIST = "dataList";


    public static final String FIREBASE_URL_DATA_LIST = FIREBASE_URL + "/" + FIREBASE_LOCATION_DATA_LIST;

    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";
    public static final String FIREBASE_PROPERTY_ITEM_NAME = "name";
    public static final String FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED = "timestampLastChanged";

    public static final String KEY_LIST_ITEM_ID = "LIST_ITEM_ID";
    public static final String KEY_LAYOUT_RESOURCE = "LAYOUT_RESOURCE";

}
