package com.parse.starter;

import android.app.Application;
import android.os.StrictMode;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "N0jUe0ljCBYpd4wlJwsFpaD8Pq8pEcJflXrNVb5A",
                "pl65P4rn1Iv4AR8DIZsQXMNMHTMy4wvxzqWuHi4h");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        PushService.setDefaultPushCallback(this,
                ParseStarterProjectActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        PushService.subscribe(getApplicationContext(), "prueba",
                ParseStarterProjectActivity.class);
    }

}
