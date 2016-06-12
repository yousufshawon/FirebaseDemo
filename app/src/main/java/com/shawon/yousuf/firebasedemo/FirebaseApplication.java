package com.shawon.yousuf.firebasedemo;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by user on 6/13/2016.
 */
public class FirebaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

                /* Initialize Firebase */
        Firebase.setAndroidContext(this);
    }
}
