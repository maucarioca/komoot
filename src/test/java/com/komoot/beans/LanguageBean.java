package com.komoot.beans;

import com.opencsv.bean.CsvBindByName;

public class LanguageBean {

	@CsvBindByName(column = "languageValue", required = true)
	private String languageValue;

	@CsvBindByName(column = "expectedTitle", required = true)
	private String expectedTitle;

	public static String[] getHeaders(){
		return new String[] {"languageValue","expectedTitle"};
	}

	public String getLanguageValue() {
		return languageValue;
	}

	public String getExpectedTitle() {
		return expectedTitle;
	}
	
	@Override
	public String toString() {
		return "[languageValue:" + getLanguageValue() + ", expectedTitle:" + getExpectedTitle() + "]";
	}
}
