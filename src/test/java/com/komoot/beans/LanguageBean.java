package com.komoot.beans;

import com.opencsv.bean.CsvBindByName;

public class LanguageBean extends MasterBean {

	@CsvBindByName(column = "languageValue", required = true)
	private String languageValue;

	@CsvBindByName(column = "expectedTitle", required = true)
	private String expectedTitle;

	public String getLanguageValue() {
		return languageValue;
	}

	public String getExpectedTitle() {
		return expectedTitle;
	}
}
