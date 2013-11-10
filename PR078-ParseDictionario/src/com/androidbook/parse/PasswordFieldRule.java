package com.androidbook.parse;

import android.widget.TextView;

public class PasswordFieldRule implements IValidator
{

	private TextView password1;
	private TextView password2;
	
	public PasswordFieldRule(TextView p1, TextView p2)
	{
		password1 = p1;
		password2 = p2;
	}
	@Override
	public boolean validate() 
	{
		String p1 = password1.getText().toString();
		String p2 = password2.getText().toString();
		if (p1.equals(p2))
		{
			return true;
		}
		//They are not the same
		password2.setError("Sorry, password values don't match!");
		return false;
	}
}//eof-class
