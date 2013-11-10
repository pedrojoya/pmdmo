package com.androidbook.parse;

import android.os.Bundle;
import android.view.View;

import com.parse.ParseUser;

public class ParseStarterProjectActivity 
extends BaseActivity
{
	public ParseStarterProjectActivity() 
	{
		super("ParseStarterProjectActivity");
	}

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//setViewsProperly();
	}
	@Override
	protected void onStart() {
		super.onStart();
		setViewsProperly();
	}
	private void setViewsProperly()
	{
		ParseUser pu = ParseUser.getCurrentUser();
		if (pu == null)
		{
			showLoggedOutView();
			return;
		}
		showLoggedInView();
	}
	private void showLoggedOutView()
	{
		//user is null
		//show signup
		//show login
		//hide welcome
		hideView(R.id.LogoutButton);
		hideView(R.id.welcomeButton);

		showView(R.id.SignupButton);
		showView(R.id.LoginButton);
		
		return;
	}
	private void showLoggedInView()
	{
		//user is found
		//hide signup
		//hide login
		//show welcome!
		//show logout
		hideView(R.id.SignupButton);
		hideView(R.id.LoginButton);
		
		showView(R.id.LogoutButton);
		showView(R.id.welcomeButton);

	}
	private void hideView(int viewId)
	{
		View v = this.findViewById(viewId);
		v.setVisibility(View.INVISIBLE);
	}
	private void showView(int viewId)
	{
		View v = this.findViewById(viewId);
		v.setVisibility(View.VISIBLE);
	}
	public void signUp(View button)
	{
		//what should happen?
		//Go to sign up activity
		gotoActivity(SignupActivity.class);
	}
	public void login(View button)
	{
		gotoActivity(LoginActivity.class);
	}
	public void logout(View button)
	{
		//Issue a logout to parse
		//go to logout view
		logoutFromParse();
		this.setViewsProperly();
	}
	public void welcome(View button)
	{
		gotoActivity(WelcomeActivity.class);
	}
	
	private void logoutFromParse()
	{
		reportBack("Logging out from Parse");
		ParseUser.logOut();
	}
}//eof-class