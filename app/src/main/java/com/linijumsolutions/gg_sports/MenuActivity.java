package com.linijumsolutions.gg_sports;

import android.app.Activity;
import android.os.Bundle;
import com.parse.ParseObject;


public class MenuActivity extends Activity {
    protected MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        app = (MyApplication)getApplication();
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }

}
