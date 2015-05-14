package com.linijumsolutions.gg_sports;


import android.app.Application;
import com.parse.Parse;
import com.parse.ParseCrashReporting;

public class MyApplication extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();

        ParseCrashReporting.enable(this);
        Parse.initialize(this, "2T57SZX4ITgo4bTQ4u3jJtoJ0MeXm2h3uWD9SCfB", "pZDJndJqhODXOciAtJlGCbVn5Ap6cOmkfhmYH5Fg");
    }

    protected void initSingletons()
    {
        // Initialize the instance of MySingleton
        MySingleton.initInstance();
    }

    public void customAppMethod()
    {
        // Custom application method
    }

}