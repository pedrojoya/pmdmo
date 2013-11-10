package com.androidbook.parse;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class ParseApplication extends Application {

	private static String tag = "ParseApplication";
	
	private static String PARSE_APPLICATION_ID
	   = "vykek4pssnHgXhH3aMwzWMGWllSe9GFRIwqHaD2m";
	
	private static String PARSE_CLIENT_KEY
	   = "w52SGUXvUbQ8BaICBcndAqaMfuYFNSkGwU9WGd2p";
	
	@Override
	public void onCreate() {
		super.onCreate();

		Log.d(tag,"initializing with keys");
		// Add your initialization code here
		Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

		//This will automatically create an annonymous user
		//The data associated to this user is abandoned when it is 
		//logged out.
		//ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
		
		//Enable to receive push
		PushService.setDefaultPushCallback(this, RespondToPushActivity.class);
		ParseInstallation pi = ParseInstallation.getCurrentInstallation();
		
		//Register a channel to test channels
		Context ctx = this.getApplicationContext();
		PushService.subscribe(ctx, "ch1", RespondToPushActivity.class);
		
		pi.saveEventually();
		Log.d(tag,"initializing app complete");
	}
}
