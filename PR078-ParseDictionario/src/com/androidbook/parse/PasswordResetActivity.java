package com.androidbook.parse;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class PasswordResetActivity 
extends FormActivity 
{
	private EditText email;
	public PasswordResetActivity() {
		super("PasswordResetActivity");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_reset);
	}

	@Override
	protected void initializeFormFields() 
	{
		email = (EditText)findViewById(R.id.email);
		addValidator(new Field(email));
	}
	
	public void resetPassword(View v)
	{
		if (validateForm() == false){
			return;
		}
		String sEmail = email.getText().toString(); 
		turnOnProgressDialog("Reset Password","Wait while we send you email with password reset");
		//userid is there
		ParseUser.requestPasswordResetInBackground(sEmail,
                new RequestPasswordResetCallback() {
					public void done(ParseException e) {
						turnOffProgressDialog();
						if (e == null) {
							reportSuccessfulReset();
						} else {
							reportResetError(e);
						}
				}
		});
     }//eof-reset
	
	private void reportSuccessfulReset(){
		gotoActivity(PasswordResetSuccessActivity.class);
		finish();
	}
	private void reportResetError(ParseException e)
	{
		//stay on the page
		//Put an error message for the exception
		String message = e.getMessage();
		alert("Reset Password", "Failed:" + message);
	}
}//eof-class