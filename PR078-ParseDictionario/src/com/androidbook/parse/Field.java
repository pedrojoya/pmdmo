package com.androidbook.parse;

import java.util.ArrayList;

import android.widget.TextView;

/*
 * A self validating entity. 
 * Implements IValidator.
 * 
 * You can attach any number of IValueValidators
 * Checks all the validators in the order indicated
 * Does a setError on the field if a validator fails
 * 
 * validation for the "required" field is built in.
 * 
 * See FormActivity to see how these fields are used
 * 
 */
public class Field
implements IValidator
{
     private TextView control;
     private boolean required = true;
     private ArrayList<IValueValidator> valueValidatorList 
     		= new ArrayList<IValueValidator>();

     public Field(TextView tv)
     {
    	 this(tv, true);
     }
     public Field(TextView tv, boolean inRequired)
     {
    	 control = tv;
    	 required = inRequired;
     }
	@Override
	public boolean validate() 
	{
		String value = getValue();
		if (StringUtils.invalidString(value))
		{
			//in valid string
			if (required)
			{
				warnRequiredField();
				return false;
			}
		}
		for(IValueValidator validator: valueValidatorList)
		{
			boolean result = validator.validateValue(getValue());
		    if (result == true) continue;
		    if (result == false)
		    {
		    	//this validator failed
		    	String errorMessage = validator.getErrorMessage();
		    	setErrorMessage(errorMessage);
		    	return false;
		    }
		}//eof-for
		//All validators passed
		return true;
	}//eof-validate
	
	
	private void warnRequiredField()
	{
		setErrorMessage("This is a required field");
	}
	public void setErrorMessage(String message)
	{
		control.setError(message);
	}
	public String getValue()
	{
		return this.control.getText().toString();
	}
}//eof-class
