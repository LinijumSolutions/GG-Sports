package com.linijumsolutions.gg_sports.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.linijumsolutions.gg_sports.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class LoginFragment extends Fragment {

    private List<String> permissions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button button = (Button) view.findViewById(R.id.button3);

        permissions = new ArrayList<>();
        permissions.add("user_friends");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginFragment.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else {
                            Log.d("MyApp", "User logged in through Facebook!");
                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

}
