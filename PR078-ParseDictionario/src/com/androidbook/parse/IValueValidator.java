package com.androidbook.parse;

public interface IValueValidator 
{
	boolean validateValue(String value);
	String getErrorMessage();
}
