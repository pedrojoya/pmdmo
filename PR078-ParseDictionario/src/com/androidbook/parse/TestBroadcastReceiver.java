package com.androidbook.parse;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TestBroadcastReceiver 
extends BroadcastReceiver 
{
	public static final String ACTION="com.androidbook.parse.TestPushAction";
	public static final String PARSE_EXTRA_DATA_KEY="com.parse.Data";
	public static final String PARSE_JSON_ALERT_KEY="alert";
	public static final String PARSE_JSON_CHANNELS_KEY="com.parse.Channel";
		
	private static final String TAG = "TestBroadcastReceiver";
	 
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		try 
		{
	      String action = intent.getAction();
	      
	      //"com.parse.Channel"
	      String channel = 
	    	  intent.getExtras()
	    	  	.getString(PARSE_JSON_CHANNELS_KEY);
	      
	      JSONObject json = 
	    	  new JSONObject(
	    			  intent.getExtras()
	    			     .getString(PARSE_EXTRA_DATA_KEY));
	 
	      Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
	      Iterator itr = json.keys();
	      while (itr.hasNext()) 
	      {
	    	  String key = (String) itr.next();
	    	  Log.d(TAG, "..." + key + " => " + json.getString(key));
	      }
	      notify(context,intent,json);
		} 
		catch (JSONException e) 
		{
			Log.d(TAG, "JSONException: " + e.getMessage());
	    }
	}
	private void notify(Context ctx, Intent i, JSONObject dataObject)
	throws JSONException
	{
	      NotificationManager nm = (NotificationManager)        
	         ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	      
	      int icon = R.drawable.robot;
	      String tickerText = 
	    	  dataObject.getString("customdata");
	      long when = System.currentTimeMillis();	      
	      Notification n = new Notification(icon, tickerText, when);
	      
	      //Let the intent invoke the respond activity
	      Intent intent = new Intent(ctx, RespondToPushActivity.class);
	      //Load it with parse data
	      intent.putExtra("com.parse.Data", 
	    		  i.getExtras().getString("com.parse.Data"));
	      
	      PendingIntent pi = PendingIntent.getActivity(ctx, 0, intent, 0);

	      n.setLatestEventInfo(ctx, "Parse Alert", tickerText, pi);
	      n.flags |= Notification.FLAG_AUTO_CANCEL;
	         
	      nm.notify(1, n);	      
	}
}//eof-class