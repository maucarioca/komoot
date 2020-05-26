package com.komoot.pageobjects.widgets;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.appium.java_client.pagefactory.Widget;

public class PlacesOnMapWidget extends Widget {

	@FindBy(xpath = "//div[@class='o-media o-media--small o-media--rev o-media--center']/div")
	private List<WebElement> placesOnMapItems;

	public PlacesOnMapWidget(WebElement element) {
		super(element);
	}

	public WebElement getPlaceInList (String placeItem) {
		return placesOnMapItems.stream().filter(t -> t.getText().contentEquals(placeItem)).findFirst().orElse(null);
	}

	public boolean checkPlaceInList(String placeItem) {
		return placesOnMapItems.stream().anyMatch(t -> t.getText().contentEquals(placeItem));
	}

	public List<String> getPlacesOnMapOptions() {
		return placesOnMapItems.stream().map(t -> t.getText()).collect(Collectors.toList());
	}
}
