package com.androidbook.parse;

/*
 * An interface implemented typically by an activity
 * so that a worker class can report back
 * on what happened.
 */
public interface IReportBack 
{
	public void reportBack(String tag, String message);
	public void reportTransient(String tag, String message);
	public void reportBack(String message);
	public void reportTransient(String message);
	
	public void turnOnProgressDialog(String title, String message);
	public void turnOffProgressDialog();
	public void alert(String title, String message);
	
}
