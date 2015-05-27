package com.linijumsolutions.gg_sports;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseFacebookUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        ParseCrashReporting.enable(this);
        Parse.initialize(this, "2T57SZX4ITgo4bTQ4u3jJtoJ0MeXm2h3uWD9SCfB", "pZDJndJqhODXOciAtJlGCbVn5Ap6cOmkfhmYH5Fg");
        ParseFacebookUtils.initialize(this);
    }

}