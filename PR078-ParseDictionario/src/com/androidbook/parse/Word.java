package com.androidbook.parse;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseUser;

import converters.ValueField;

public class Word 
extends ParseObjectWrapper
{
	public static String t_tablename = "WordObject";
	public static String PARCELABLE_WORD_ID = "WordObjectId";
	
	//Only two fileds
	public static String f_word = "word";
	public static String f_meaning = "meaning";
	
	//Constructors: A new word from scratch
	public Word(String word, String meaning){
		super(t_tablename);
		setWord(word);
		setMeaning(meaning);
	}
	//Wrapping from a ParseObject gotten from the cloud
	public Word(ParseObject po)	{
		super(po);
	}
	//Recreated using a previously Parceled word
	public Word(ParseObjectWrapper inPow)	{
		super(inPow);
	}
	
	//Accessors
	public String getWord()	{
		return po.getString(f_word);
	}
	public void setWord(String in)	{
		po.put(f_word,in);
	}
	public String getMeaning()	{
		return po.getString(f_meaning);
	}
	public void setMeaning(String in)	{
		po.put(f_meaning,in);
	}
	public String toString()
	{
		String word = getWord();
		String user = getCreatedBy().getUsername();
		return word + "/" + user;
	}
    //have the children override this
	@Override
    public List<ValueField> getFieldList()
    {
    	ArrayList<ValueField> fields = new ArrayList<ValueField>();
    	fields.add(ValueField.getStringField(Word.f_word));
    	fields.add(ValueField.getStringField(Word.f_meaning));
    	return fields;
    }
}//eof-class
