package com.komoot.beans;

import com.opencsv.bean.CsvBindByName;

public class PlaceOrAddressBean extends MasterBean {

	@CsvBindByName(column = "placeOrAddressValue", required = true)
	private String placeOrAddressValue;

	public String getPlaceOrAdddressValue() {
		return placeOrAddressValue;
	}
}
