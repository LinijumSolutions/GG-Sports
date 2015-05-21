package com.linijumsolutions.gg_sports.clients;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.linijumsolutions.gg_sports.MainActivity;
import com.linijumsolutions.gg_sports.screens.LoginActivity;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

/**
 * Created by lukas on 17/05/15.
 * Connection , and CRUD operations
 */
public class ParseClient {

    public static Intent setParseUser(Context context){

        Intent intent;
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            intent = new Intent(context ,MainActivity.class);
        } else {
            // show the activity_signup or login screen
            intent = new Intent(context ,LoginActivity.class);
        }
        return intent;
    }
}
