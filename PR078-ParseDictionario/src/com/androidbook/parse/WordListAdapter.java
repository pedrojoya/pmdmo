package com.androidbook.parse;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.ParseException;

//ArrayAdapter holds the data/rows in a list or array
//it passes this row object to getView with a position
public class WordListAdapter 
extends ArrayAdapter<Word>
implements View.OnClickListener
{

	private static String tag = "WordListAdapter";
	
	//String[] wordArray;
	Context ctx = null;
	LayoutInflater lif = null;
	IReportBack reportBack = null;
	
	public WordListAdapter(Context context, 
			List<Word> wordList,
			IReportBack inReportBack)
	{
	  	  super(context
	  			   ,R.layout.word_list_item_relative_layout
//	  			   ,R.layout.word_list_item_layout
	  			   ,R.id.wordListRowId
	  			   ,wordList);
	  	  ctx = context;
	      lif = LayoutInflater.from(ctx);
	      reportBack = inReportBack;
	}
	
	//This class saves references to the buttons that are displayed
	//for each row. if there are 10 rows then there are 10 buttons
	//and 10 instances of view holder
	static class ViewHolder
	{
		public ViewHolder(View rowView, View.OnClickListener ocl)
		{
			delButton =
				(Button)rowView.findViewById(R.id.wordListRowDelBtnId);
			playButton =
				(Button)rowView.findViewById(R.id.wordListRowSlvBtnId);
			textView =
				(TextView)rowView.findViewById(R.id.wordListRowWordId);
			
			contextView =
				(TextView)rowView.findViewById(R.id.wordListRowContextId);
			
			delButton.setOnClickListener(ocl);
			playButton.setOnClickListener(ocl);
			textView.setOnClickListener(ocl);
		}
		public TextView textView;
		public TextView contextView;
		public Button delButton;
		public Button playButton;
		
		//this is the object
		public Word wordAtThisRow;
	}

	//For a given position return a view
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder vh;
		View thisView;
		if (convertView == null)
		{
			//create the view
			View rowView =
				lif.inflate(R.layout.word_list_item_relative_layout,null);
			vh = new ViewHolder(rowView,this);
			rowView.setTag(vh);
			thisView = rowView;
		}
		else
		{
			//populate the view
			vh = (ViewHolder)convertView.getTag();
			thisView = convertView;
		}
		//got a view holder
		//and a view.
		
		Word curword = this.getItem(position);
		vh.textView.setText(curword.toString());
		vh.contextView.setText(curword.getCreatedAtAsString());
		
		//Notice how we are placing the tags
		//in the respective buttons
		vh.delButton.setTag(curword);
		vh.playButton.setTag(curword);
		
		vh.wordAtThisRow = curword;
		return thisView;
	}
	@Override
	public void onClick(View v) 
	{
		if (v.getId() == R.id.wordListRowDelBtnId)
		{
			Word word = (Word)v.getTag();
			Log.d(tag, "Delete Click detected for word:" + v.getTag());
			deleteWord(word);
			this.notifyDataSetChanged();
		}
		else if (v.getId() == R.id.wordListRowSlvBtnId)
		{
			Word word = (Word)v.getTag();
			Log.d(tag, "Play Click detected for word:" + v.getTag());
			gotoPlay(word);
		}
		else if (v.getId() == R.id.wordListRowId)
		{
			Log.d(tag, "TextView detected for word:");
		}
	}

	private Word curWordToDelete = null;
	private void deleteWord(Word wordRef)
	{
		curWordToDelete = wordRef;
		reportBack.reportTransient("Delete Button Clicked:" + wordRef.toString());
	    reportBack.turnOffProgressDialog();
		wordRef.getParseObject().deleteInBackground(new DeleteCallback() {
			@Override
			public void done(ParseException e) {
			    reportBack.turnOffProgressDialog();
			    if (e == null) {
			    	deleteSuccessful(curWordToDelete);
			    }
			    else {
			    	deleteFailed(e,curWordToDelete);
			    }
			    curWordToDelete = null;
			}
		});
	}
	
	private void deleteFailed(ParseException x, Word wordRef)
	{
		String message = x.getMessage();
		reportBack.alert("Delete failed", "Not able to delete the word:" + wordRef.getWord());
	}
	private void deleteSuccessful(Word wordRef)
	{
		reportBack.reportTransient("Word deleted successfull:" + wordRef.getWord());
		this.remove(wordRef);
	}
	
	private void gotoPlay(Word wordRef)
	{
		Activity baseActivity = (Activity)reportBack;
		//noop
		reportBack.reportTransient("Play Button Clicked:" + wordRef.toString());
		Intent i = new Intent(baseActivity,WordMeaningsListActivity.class);
		i.putExtra(Word.PARCELABLE_WORD_ID, wordRef.po.getObjectId());
		i.putExtra(Word.t_tablename,wordRef);
		baseActivity.startActivity(i);
	}
}//eof-class
