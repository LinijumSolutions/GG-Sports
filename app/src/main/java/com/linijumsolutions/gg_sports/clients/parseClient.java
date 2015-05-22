package com.linijumsolutions.gg_sports.clients;

import android.content.Context;
import android.content.Intent;

import com.linijumsolutions.gg_sports.screens.MainActivity;
import com.linijumsolutions.gg_sports.screens.LoginActivity;
import com.parse.ParseUser;

public class ParseClient {

    public static Intent setParseUser(Context context){

        Intent intent;
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            intent = new Intent(context , MainActivity.class);
        } else {
            intent = new Intent(context , LoginActivity.class);
        }
        return intent;
    }
}
