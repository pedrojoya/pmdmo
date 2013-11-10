package com.androidbook.parse;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.SaveCallback;

public class CreateAWordActivity 
extends FormActivity 
{
	EditText word;
	EditText meaning;
	public CreateAWordActivity() 
	{
		super("CreateAWordActivity");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_word);
	}

	@Override
	protected void initializeFormFields() 
	{
		word = (EditText)findViewById(R.id.word);
		meaning = (EditText)findViewById(R.id.meaning);
		
		addValidator(new Field(word));
		//meaning is optional
	}

	private String getWord()
	{
		return word.getText().toString();
	}
	private String getMeaning()
	{
		String m = meaning.getText().toString();
		if (validString(m) == true)
		{
			return m;
		}
		return "";
	}
	public void createWord(View v){
		if (validateForm() == false) {
			return;
		}
		//form is valid
		String sWord = getWord();
		String sMeaning = getMeaning();
		
		Word w = new Word(sWord, sMeaning);
		turnOnProgressDialog("Saving Word", "We will be right back");
		w.po.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				turnOffProgressDialog();
				if (e == null)	{
					//no exception
					wordSavedSuccessfully();
				}
				else	{
					wordSaveFailed(e);
				}
			}
		});
		
	}//eof-login
	
	private void wordSaveFailed(ParseException e)
	{
		String error = e.getMessage();
		alert("Saving word failed", error);
	}
	private void wordSavedSuccessfully()
	{
		gotoActivity(WordListActivity.class);
		//Don't finish it as back button is valid
		//finish();
	}
}//eof-class