package com.androidbook.parse;

public class StringUtils {

	public static boolean invalidString(String s)
    {
    	return !validString(s);
    }
    public static boolean validString(String s)
    {
    	if (s == null)
    	{
    		return false;
    	}
    	if (s.trim().equalsIgnoreCase(""))
    	{
    		return false;
    	}
    	return true;
    }	
}
