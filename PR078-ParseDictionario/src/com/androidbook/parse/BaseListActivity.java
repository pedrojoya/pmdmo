package com.androidbook.parse;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public abstract class BaseListActivity 
extends ListActivity
implements IReportBack
{
    //private variables set by constructor
	private static String tag=null;
	private ProgressDialog pd = null;
	
	public BaseListActivity(String inTag)
	{
		tag = inTag;
	}
	
	public void reportBack(String message)
	{
		reportBack(tag,message);
	}
	public void reportTransient(String message)
	{
		reportTransient(tag,message);
	}
	
	public void reportBack(String tag, String message)
	{
		Log.d(tag,message);
	}
	public void reportTransient(String tag, String message)
	{
		String s = tag + ":" + message;
		Toast mToast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
		mToast.show();
		reportBack(tag,message);
		Log.d(tag,message);
	}
	public boolean invalidString(String s)
    {
    	return !validString(s);
    }
    public boolean validString(String s)
    {
    	if (s == null)
    	{
    		return false;
    	}
    	if (s.trim().equalsIgnoreCase(""))
    	{
    		return false;
    	}
    	return true;
    }	
	public void gotoActivity(Class activityClassReference)
	{
		Intent i = new Intent(this,activityClassReference);
		startActivity(i);
	}
	
	//Utility functions
	public void turnOnProgressDialog(String title, String message)
	{
		pd = ProgressDialog.show(this,title,message);
	}
	public void turnOffProgressDialog()
	{
		pd.cancel();
	}
	
	public void alert(String title, String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		   }
		});
		alertDialog.show();		
	}
}//eof-class