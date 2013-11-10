package com.androidbook.parse;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
/*
 * What shoudl happen here?
 * 
 * This is a sign up activity.
 * 
 * 1. enter sign up info
 * 2. validate fields
 * 3. succeed
 * 4. fail
 * 
 * Failure
 * *********
 * 1. validate field level edits
 * 2. if account already exists offer to login/reset password
 * 
 * Success
 * *********
 * 1. Transfer to activity successful sign up
 * 
 */
public class SignupActivity 
extends FormActivity 
{
	ProgressDialog pd;
	private static String tag = "SignupActivity"; 
	
	//Form Fields
	EditText userid;
	EditText password1;
	EditText password2;
	EditText email;
	
	public SignupActivity()
	{
		super(tag);
	}
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
	}
	
	//from form fields
	@Override
	protected void initializeFormFields() 
	{
		this.reportBack("form initialized");
		userid = (EditText)findViewById(R.id.userid);
		password1 = (EditText)findViewById(R.id.password1);
		password2 = (EditText)findViewById(R.id.password2);
		email = (EditText)findViewById(R.id.email);
		
		//Setup the validators
		addValidator(new Field(userid));
		addValidator(new Field(password1));
		addValidator(new Field(password2));
		addValidator(new Field(email));
		addValidator(new PasswordFieldRule(password1,password2));
	}
	
	private boolean validateFields()
	{
		return validateForm();
	}

	private String getUserId()
	{
		return getStringValue(R.id.userid);
	}
	private String getUserEmail()
	{
		return getStringValue(R.id.email);
	}
	private String getPassword1()
	{
		return getStringValue(R.id.password1);
	}
	private String getPassword2()
	{
		return getStringValue(R.id.password2);
	}
	
	private TextView getTextView(int controlId)
	{
		TextView tv = (TextView)findViewById(controlId);
		if (tv == null)
		{
			throw new RuntimeException("Sorry Can't find the control id");
		}
		//view available
		return tv;
	}
	private String getStringValue(int controlId)
	{
		TextView tv = (TextView)findViewById(controlId);
		if (tv == null)
		{
			throw new RuntimeException("Sorry Can't find the control id");
		}
		//view available
		return tv.getText().toString();
	}

	public void signupButtonClick(View v)
	{
		if (validateFields() == false)
		{
			reportTransient("Make sure all fields have valid values");
			return;
		}
		//everything is good
		String userid = getUserId();
		String password = getPassword1();
		String email = getUserEmail();
		reportTransient("Going to sign up now");
		signup(userid, email, password);
	}
	
	private void signup(String userid, String email, String password)
	{
		ParseUser user = new ParseUser();
		user.setUsername(userid);
		user.setPassword(password);
		user.setEmail(email);

		//Show the progress dialog
		turnOnProgressDialog("Signup", "Please wait while we sign you up");
		
		//Go for signup with a callback
		user.signUpInBackground(new SignUpCallback() {
		  public void done(ParseException e) {
			turnOffProgressDialog();
		    if (e == null) {
		      // Hooray! Let them use the app now.
		    	signupSuccessful();
		    } else {
		      // Sign up didn't succeed. Look at the ParseException
		      // to figure out what went wrong
		      signupFailed(e);
		    }
		  }
		});		
		return;
	}//signup-method
	
	private void signupSuccessful()
	{
		//Go to signup successful page 
		//finish
		gotoActivity(SignupSuccessActivity.class);
		finish();
	}
	private void signupFailed(ParseException x)
	{
		//stay on the page
		//Put an error message for the exception
		String message = x.getMessage();
		alert("Signup", "Failed:" + message);
	}
}//eof-class