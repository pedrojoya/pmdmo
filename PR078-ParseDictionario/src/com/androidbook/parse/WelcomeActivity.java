package com.androidbook.parse;

import android.os.Bundle;
import android.view.View;

public class WelcomeActivity 
extends BaseActivity 
{
	public WelcomeActivity() 
	{
		super("WelcomeActivity");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
	}

	public void showUsers(View v)
	{
		gotoActivity(UserListActivity.class);
	}
	
	public void createWord(View v)
	{
		gotoActivity(CreateAWordActivity.class);
	}
		
	public void showWordListActivity(View v)
	{
		gotoActivity(WordListActivity.class);
	}
	public void testPush(View v)
	{
		gotoActivity(RespondToPushActivity.class);
	}
}//eof-class