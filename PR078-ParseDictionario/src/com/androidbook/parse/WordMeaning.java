package com.androidbook.parse;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class WordMeaning extends ParseObjectWrapper
{
	//Design the table first
	public static String t_tablename = "WordMeaningObject";
	public static String f_word = "word";
	public static String f_meaning = "meaning";
	
	public WordMeaning(String wordMeaning, Word inParentWord)
	{
		super(t_tablename);
		setMeaning(wordMeaning);
		setWord(inParentWord);
	}

	//Make sure there is a way to construct with a straight 
	//Parse object
	public WordMeaning(ParseObject po)
	{
		//Create a check in the future if it is not of the same type
		super(po);
	}
	public void setMeaning(String meaning)
	{
		po.put(f_meaning, meaning);
	}
	public void setWord(Word word)
	{
		po.put(f_word, word.po);
	}
	public String getMeaning()
	{
		return po.getString(f_meaning);
	}
	public Word getWord()
	{
		return new Word(po.getParseObject(f_word));
	}
}
