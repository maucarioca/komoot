package com.komoot.beans;

import com.opencsv.bean.CsvBindByName;

public class PlaceOnMapBean extends MasterBean {

	@CsvBindByName(column = "placeValue", required = true)
	private String placeValue;

	public String getPlaceValue() {
		return placeValue;
	}
}
