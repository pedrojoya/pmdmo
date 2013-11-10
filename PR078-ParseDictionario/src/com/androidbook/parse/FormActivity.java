package com.androidbook.parse;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

/*
 * Uses the field validator framework to do form validation
 * for derived activities.
 * Most useful for activities that are mainly forms.
 * 
 * What it does
 * ************
 * 1. overrides the setContentView() to call
 * initializeFormFields()
 * 2. In that function gather your fields and
 * register them as validators
 * 3. Knows how to validate the validator set
 * 
 * How to use it
 * ****************
 * 1. Extend FormActivity
 * 2. Override initializeFormFields()
 * 3. Gather your fields in step2
 * 4. Register fields through addValidator()
 * 5. Call validate() of form activity
 * 6. The field validators will set errors based on Android framework
 * 
 * initializeFormFields()
 * ************************************** 
 * int min=5;
 * int max=10;
 * String error = "Should be between 5 and 10";
 * MinMaxValidator mmv = new MinMaxValidator(5,10,error);
 * 
 * TextView control; boolean required=true;
 * Field f = new Field(control, true);
 * f.addValidator(mmv);
 * f.addValidatort(..others..);
 * 
 * See SignupSuccessActivity to see how this is used
 * 
 */
public abstract class FormActivity 
extends BaseActivity
{
	public FormActivity(String inTag) {
		super(inTag);
	}

	protected abstract void initializeFormFields();
	
	private ArrayList<IValidator> ruleSet = new ArrayList<IValidator>();
	
	public void addValidator(IValidator v)
	{
		ruleSet.add(v);
	}
	public boolean validateForm()
	{
		boolean finalResult = true;
		for(IValidator v: ruleSet)
		{
			boolean result = v.validate();
			if (result == false)
			{
				finalResult = false;
			}
			//if true go around
			//if all true it should stay true
		}
		return finalResult;
	}

	@Override
	public void setContentView(int viewid) {
		super.setContentView(viewid);
		initializeFormFields();
	}
}//eof-class
