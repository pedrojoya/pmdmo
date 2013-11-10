package com.androidbook.parse;

import android.os.Bundle;
import android.view.View;

public class SignupSuccessActivity 
extends BaseActivity 
{
	public SignupSuccessActivity() {
		super("SignupSuccessActivity");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_success);
	}

	public void gotoMainActivity(View v)
	{
		gotoActivity(ParseStarterProjectActivity.class);
		finish();
	}

}//eof-class