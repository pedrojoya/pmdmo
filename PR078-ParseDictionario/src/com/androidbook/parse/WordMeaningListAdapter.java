package com.androidbook.parse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

//ArrayAdapter holds the data/rows in a list or array
//it passes this row object to getView with a position
public class WordMeaningListAdapter 
extends ArrayAdapter<WordMeaning>
implements View.OnClickListener
{

	private static String tag = "WordMeaningListAdapter";
	
	//String[] wordArray;
	Context ctx = null;
	LayoutInflater lif = null;
	IReportBack reportBack = null;
	
	public WordMeaningListAdapter(Context context, 
			List<WordMeaning> wordMeaningList,
			IReportBack inReportBack)
	{
	  	  super(context
	  			   ,R.layout.word_meaning_list_item_relative_layout
	  			   ,R.id.wml_RowWordMeaningId
	  			   ,wordMeaningList);
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
			textView =
				(TextView)rowView.findViewById(R.id.wml_RowWordMeaningId);
			textView.setOnClickListener(ocl);
			contextView =
				(TextView)rowView.findViewById(R.id.wml_RowContextId);
		}
		
		//Holds the meaning line
		public TextView textView;
		//Model Object representing word meaning
		public WordMeaning wordMeaningAtThisRow;
		
		//should be user/date who created this meaning
		public TextView contextView;
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
				lif.inflate(R.layout.word_meaning_list_item_relative_layout,null);
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
		
		WordMeaning curwordMeaning = this.getItem(position);
		vh.textView.setText(curwordMeaning.getMeaning());
		vh.contextView.setText(this.getContextFromWordMeaning(curwordMeaning));
		
		//Notice how we are placing the tags
		//in the respective buttons
		
		vh.wordMeaningAtThisRow = curwordMeaning;
		return thisView;
	}
	@Override
	public void onClick(View v) 
	{
	}
	
	private String getContextFromWordMeaning(WordMeaning m)
	{
		//who created it and when
		ParseUser pu = m.getCreatedBy();
		Date d = m.getCreatedAt();
		
		DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
		String datestring =  df.format(d);
		
		return pu.getUsername() + "/" + datestring;
	}
}//eof-class
