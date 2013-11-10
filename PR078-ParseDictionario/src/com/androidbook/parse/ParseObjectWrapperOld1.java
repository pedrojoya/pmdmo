package com.androidbook.parse;


import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseObjectWrapperOld1 
{
	public static String f_createdAt = "createdAt";
	public static String f_createdBy = "createdBy";
	
	public static String f_updatedAt = "updatedAt";
	public static String f_updatedBy = "updatedBy";
	
	public ParseObject po;
	public ParseObjectWrapperOld1(String tablename)
	{
		po = new ParseObject(tablename);
		po.put(f_createdBy, ParseUser.getCurrentUser());
	}
	public ParseObjectWrapperOld1(ParseObject in)
	{
		po = in;
	}
	//Accessors
	public ParseObject getParseObject() { return po; }
	String getTablename()
	{
		return po.getClassName();
	}
	public ParseUser getCreatedBy()
	{
		return po.getParseUser(f_createdBy);
	}
	public void setCreatedBy(ParseUser in)
	{
		po.put(f_createdBy, in);
	}
	public void setUpdatedBy()
	{
		po.put(f_updatedBy, ParseUser.getCurrentUser());
	}
	public ParseUser getLastUpdatedBy()
	{
		return (ParseUser)po.getParseObject(f_updatedBy);
	}
}//eof-class
