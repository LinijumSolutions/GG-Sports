package com.linijumsolutions.gg_sports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.linijumsolutions.gg_sports.screens.StartTrainingActivity;
import com.parse.ParseObject;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
    }

    public void onStartTrainingClicked(View v){
        Intent intent = new Intent(this, StartTrainingActivity.class);
        startActivity(intent);
    }

}
