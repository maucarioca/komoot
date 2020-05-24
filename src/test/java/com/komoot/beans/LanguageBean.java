package com.komoot.beans;

import com.opencsv.bean.CsvBindByName;

public class LanguageBean extends MasterBean {

	@CsvBindByName(column = "languageValue", required = true)
	private String languageValue;

	@CsvBindByName(column = "expectedUrl", required = true)
	private String expectedUrl;

	public String getLanguageValue() {
		return languageValue;
	}

	public String getExpectedUrl() {
		return expectedUrl;
	}
}
