package es.iessaladillo.pedrojoya.pr077;

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

        // Inicializamos Parse con el código de aplicación y el código de
        // cliente.
        Parse.initialize(this, "d2PHQCDhqFMHPJEPxaBHCM6VwNJGAOyb4ZA7bvop",
                "4udokv0y95AcXIP6DlbRhlDkmM7x4cwBNQAtskqM");
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
        PushService.setDefaultPushCallback(this, MainActivity.class);
        PushService.subscribe(getApplicationContext(), "contador",
                MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}
