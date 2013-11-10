package com.androidbook.parse;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class WordListActivity 
extends BaseListActivity
{
    public WordListActivity() {
		super("WordListActivity");
	}
	@Override     
    protected void onCreate(Bundle savedInstanceState)
    {         
    	super.onCreate(savedInstanceState);
    	
    	//this.setListAdapter(getAdapter());
    	ListView lv = this.getListView();
    	setInitialView(lv);
    	lv.setItemsCanFocus(true);
    	this.populateWordList();
    	
    }
    private void setInitialView(ListView lv)
    {
    	View beginView = this.getEmptyBeginView();
    	beginView.setVisibility(View.GONE);
        ((ViewGroup)(lv.getParent())).addView(beginView);
        lv.setEmptyView(beginView);    	
    }
    private void setErrorView(String errorMessage)
    {
    	TextView emptyView = 
    		(TextView)findViewById(R.id.EmptyWordListTextViewId);
    	emptyView.setText(errorMessage);
    }
    private void populateWordList()
    {
    	ParseQuery query = new ParseQuery(Word.t_tablename);
    	query.orderByDescending(Word.f_createdAt);
    	query.include(Word.f_createdBy);
    	
    	query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
    	query.setMaxCacheAge(100000L);
    	
    	this.turnOnProgressDialog("Going to get words", "Patience. Be Right back");
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
    	ArrayList<Word> wordList = new ArrayList<Word>();
    	for(ParseObject po: objects)
    	{
    		Word puw = new Word(po);
    		wordList.add(puw);
    	}
    	
  	  WordListAdapter listItemAdapter = 
		   new WordListAdapter(this
		           ,wordList
		           ,this);
  	  this.setListAdapter(listItemAdapter);
    }
    private void queryFailure(ParseException x)
    {
    	this.setErrorView(x.getMessage());
    }
    
    private View getEmptyBeginView()
    {
       LayoutInflater lif = LayoutInflater.from(this);
       View v = lif.inflate(R.layout.list_empty_begin_layout, null);
       return v;
    }    
}//eof-class

