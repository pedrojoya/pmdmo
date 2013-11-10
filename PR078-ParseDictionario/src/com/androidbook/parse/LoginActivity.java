package com.androidbook.parse;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity 
extends FormActivity 
{
	EditText userid;
	EditText password;
	public LoginActivity() 
	{
		super("LoginActivity");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}

	@Override
	protected void initializeFormFields() 
	{
		userid = (EditText)findViewById(R.id.userid);
		password = (EditText)findViewById(R.id.password);
		
		addValidator(new Field(userid));
		addValidator(new Field(password));
	}

	private String getUserid()
	{
		return userid.getText().toString();
	}
	private String getPassword()
	{
		return password.getText().toString();
	}
	public void login(View v)
	{
		if (validateForm() == false){
			return;
		}
		//form is valid
		String sUserid = getUserid();
		String sPassword = getPassword();
		
		turnOnProgressDialog("Login","Wait while we log you in");
		ParseUser.logInInBackground(sUserid, sPassword, new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
				turnOffProgressDialog();
			    if (user != null) {
			      reportSuccessfulLogin();
			    } else {
			      reportParseException(e);
			    }
			  }
			});	
	}//eof-login
	
	private void reportParseException(ParseException e)
	{
		String error = e.getMessage();
		reportTransient("Login failed with:" + error);
	}
	private void reportSuccessfulLogin()
	{
		gotoActivity(ParseStarterProjectActivity.class);
		finish();
	}
	public void resetPassword(View v)
	{
		gotoActivity(PasswordResetActivity.class);
	}
	
}//eof-class