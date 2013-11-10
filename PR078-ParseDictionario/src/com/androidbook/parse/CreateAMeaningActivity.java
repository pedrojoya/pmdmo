package com.androidbook.parse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.SaveCallback;

/*
 * Given a word create a meaning for it.
 * uses layout: create_word_meaning (cwm_)
 */
public class CreateAMeaningActivity 
extends FormActivity 
{
	//Fields
	TextView word;
	TextView wordDetail;
	EditText meaning;
	Word parceledWord;
	
	public CreateAMeaningActivity()	{
		super("CreateAMeaningActivity");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parceledWord = getParceledWordFromIntent();
		
		//Make sure this is one ofthe last calls
		setContentView(R.layout.create_word_meaning);
	}

	@Override
	protected void initializeFormFields() 
	{
		word = (TextView)findViewById(R.id.cwm_tv_word);
		wordDetail = (TextView)findViewById(R.id.cwm_tv_wordDetail);
		meaning = (EditText)findViewById(R.id.cwm_et_meaning);
		
		//Only meaning is a required field
		addValidator(new Field(meaning));
		
		//initialize word
		word.setText("Provide meaning for:" + getWordText(parceledWord));
		
		//initalize wordDetail
		wordDetail.setText(getWordDetail(parceledWord));
	}

	private String getWordText(Word pword)
	{
		return pword.getWord();
	}
	private String getWordDetail(Word pword)
	{
		String by = pword.getCreatedByUser().username;
		Date d = pword.getCreatedAt();
		
		DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
		String datestring =  df.format(d);
		
		return by + "/" + datestring;
	}
	private Word getParceledWordFromIntent()
	{
		Intent i = this.getIntent();
		if (i == null)		{
			throw new RuntimeException("Sorry no intent found");
		}
		//intent is available
		ParseObjectWrapper pow = 
			(ParseObjectWrapper)i.getParcelableExtra(Word.t_tablename);
		
		if (pow == null) {
			throw new RuntimeException("ParceledWord not found");
		}
		Word parceledWord = new Word(pow);
		
		reportTransient("Parcelled word recovered");
		return parceledWord;
	}
	
	public void createMeaning(View v)
	{
		if (validateForm() == false)
		{
			return;
		}
		String meaning = getMeaning();
		//form is valid
		WordMeaning wm = new WordMeaning(meaning, parceledWord);
		turnOnProgressDialog("Saving Word Meaning", "We will be right back");
		wm.po.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				turnOffProgressDialog();
				if (e == null)	{
					//no exception
					wordMeaningSavedSuccessfully();
				}
				else	{
					wordMeaningSaveFailed(e);
				}
			}
		});
	}
	
	private String getMeaning()
	{
		return this.meaning.getText().toString();
	}
	
	private void wordMeaningSaveFailed(ParseException e)
	{
		String error = e.getMessage();
		alert("Saving word failed", error);
	}
	private void wordMeaningSavedSuccessfully()
	{
		//gotoActivity(WordListActivity.class);
		//Don't finish it as back button is valid
		//finish();
		alert("word meaning saved", "Success");
	}
}//eof-class