package com.androidbook.parse;

import android.os.Bundle;
import android.view.View;

public class PasswordResetSuccessActivity 
extends BaseActivity 
{
	public PasswordResetSuccessActivity() 
	{
		super("PasswordResetSuccessActivity");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_reset_success);
	}
	public void gotoLogin(View v)
	{
		gotoActivity(LoginActivity.class);
		finish();
	}

}//eof-class