package com.androidbook.parse;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.ParsePush;

/*
 * Main layout: rtp_respond_to_push.xml
 * id prefix: rtp_
 */
public class RespondToPushActivity 
extends BaseActivity 
{
	private int i = 0;
	public RespondToPushActivity() {
		super("RespondToPushActivity");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rtp_respond_to_push);
		examineIntent(getIntent());
	}

	
	@Override
	protected void onNewIntent(Intent intent) {
		examineIntent(intent);
	}

	public void sendMessage(View v)
	{
		ParsePush push = new ParsePush();
		String message = "Client message" + Integer.toString(i++);
		push.setChannel("ch1");
		push.setMessage(message);
		push.sendInBackground();
		reportTransient(message);
	}
	
	public void sendMessageAsData(View v)
	{
		JSONObject data = getJSONDataMessage();
		ParsePush push = new ParsePush();
		push.setChannel("ch1");
		push.setData(data);
		push.sendInBackground();
		reportTransient("Sent as data");
	}
	
	private JSONObject getJSONDataMessage()
	{
		try
		{
			JSONObject data = new JSONObject();
			data.put("alert", "Main Message");
			data.put("customdata", "custom data value");
			return data;
		}
		catch(JSONException x)
		{
			throw new RuntimeException("Something wrong with JSON", x);
		}
	}
	private JSONObject getJSONDataMessageForIntent()
	{
		try
		{
			JSONObject data = new JSONObject();
			//Notice alert is not required
			//data.put("alert", "Message from Intent");
			//instead action is used
			data.put("action", TestBroadcastReceiver.ACTION);
			data.put("customdata", "custom data value");
			return data;
		}
		catch(JSONException x)
		{
			throw new RuntimeException("Something wrong with JSON", x);
		}
	}
	public void sendMessageAsIntent(View v)
	{
		JSONObject data = getJSONDataMessageForIntent();
		ParsePush push = new ParsePush();
		push.setChannel("ch1");
		push.setData(data);
		push.sendInBackground();
		reportTransient("Sent as data");
	}
	
	private void examineIntent(Intent i)
	{
		String u = i.toURI();
		this.reportTransient(u);
		
		TextView tv = (TextView)findViewById(R.id.rtp_welcomeMessage);
		tv.setText(u);
	}
}//eof-class