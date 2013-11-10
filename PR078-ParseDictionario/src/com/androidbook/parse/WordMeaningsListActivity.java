package com.androidbook.parse;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/*
 * Lists a set of meanings for words.
 * The intent will carry the word for which we seek meanings.
 */
public class WordMeaningsListActivity 
extends BaseListActivity
{
	Word parceledWord;
    public WordMeaningsListActivity() {
		super("WordMeaningsListActivity");
	}
	@Override     
    protected void onCreate(Bundle savedInstanceState)
    {         
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.wml_layout);
    	parceledWord = this.getParceledWordFromIntent();
    	
    	//See if this works
    	ListView lv = getListView();
    	//lv.addFooterView(getFooterView());
    	
    	//No need to do this anymore
    	//setInitialView(lv);
    	lv.setItemsCanFocus(true);
    	
    	populateWordHeader(parceledWord);
    	populateWordMeaningsList(parceledWord);
    }
	
    private View getFooterView()
    {
       LayoutInflater lif = LayoutInflater.from(this);
       View v = lif.inflate(R.layout.wml_footer_layout, null);
       return v;
    }    
    //Assume that no rows are found
    //Locate the text id in the initial view and set it 
    //to an error message.
    private void setErrorView(String errorMessage)
    {
    	TextView emptyView = 
    		(TextView)findViewById(android.R.id.empty);
    	emptyView.setText(errorMessage);
    }
    
    private void setEmptyViewToNoRows()
    {
    	TextView emptyView = 
    		(TextView)findViewById(android.R.id.empty);
    	emptyView.setText("No Meanings available for this word");
    }
    
	private Word getParceledWordFromIntent()
	{
		Intent i = this.getIntent();
		if (i == null)		{
			throw new RuntimeException("Sorry no intent found");
		}
		//intent is available
		String wordId = i.getStringExtra(Word.PARCELABLE_WORD_ID);
		if (wordId == null) {
			throw new RuntimeException("word id not found");
		}
		
		reportTransient("wordid found:" + wordId);
		ParseObjectWrapper pow = 
			(ParseObjectWrapper)i.getParcelableExtra(Word.t_tablename);
		
		if (pow == null) {
			throw new RuntimeException("ParceledWord not found");
		}
		Word parceledWord = new Word(pow);
		
		
		reportTransient("Parcelled word recovered");
		return parceledWord;
	}
    /*
     * word is a parse wrapped object
     * Get all the meanings for this word
     * It is a parceled word. Almost like a word but not fully!
     */
    private void populateWordMeaningsList(Word word)
    {
    	ParseQuery query = new ParseQuery(WordMeaning.t_tablename);
    	query.whereEqualTo(WordMeaning.f_word, word.po);
    	query.orderByDescending(WordMeaning.f_createdAt);
    	
    	//Include who created me
    	query.include(WordMeaning.f_createdBy);
    	
    	//Include who the parent word is
    	query.include(WordMeaning.f_word);
    	
    	//How can I include the owner of the word
    	query.include(WordMeaning.f_word + "." + Word.f_createdBy);
    	
    	this.turnOnProgressDialog("Going to get word meanings for:" + word.getWord(), 
    			"Patience. Be Right back");
    	query.findInBackground(new FindCallback() {
    	  public void done(List<ParseObject> objects, ParseException e) {
    		turnOffProgressDialog();
    	    if (e == null) {
    	        // The query was successful.
    	    	successfulQuery(objects);
    	    } else {
    	        // Something went wrong.
    	    	queryFailure(e);
    	    }
    	  }
    	});    	
    }
    private void successfulQuery(List<ParseObject> objects)
    {
    	this.setEmptyViewToNoRows();
    	ArrayList<WordMeaning> wordMeaningList = new ArrayList<WordMeaning>();
    	for(ParseObject po: objects)
    	{
    		WordMeaning wordMeaning = new WordMeaning(po);
    		wordMeaningList.add(wordMeaning);
    	}
    	
  	   WordMeaningListAdapter listItemAdapter = 
		   new WordMeaningListAdapter(this
		           ,wordMeaningList
		           ,this);
  	   this.setListAdapter(listItemAdapter);
    }
    private void queryFailure(ParseException x)
    {
    	this.setErrorView(x.getMessage());
    }
    //respond to button
    public void createWordMeaning(View v)
    {
		reportTransient("Going to create a word meaning");
		Intent i = new Intent(this,CreateAMeaningActivity.class);
		i.putExtra(Word.t_tablename,parceledWord);
		startActivity(i);    	
    }
    private void populateWordHeader(Word pword)
    {
    	this.setTitle("Meanings for:" + pword.getWord());
    }
   
}//eof-class

