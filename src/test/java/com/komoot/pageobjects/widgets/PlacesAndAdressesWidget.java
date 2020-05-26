package com.komoot.pageobjects.widgets;

import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.CoreMatchers;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.appium.java_client.pagefactory.Widget;
import util.Utility;

public class PlacesAndAdressesWidget extends Widget {

	@FindBy(xpath = "//div[@class='o-media o-media--small']/div[@class='o-media__body']/div")
	private List<WebElement> placesAndAdressesItems;

	public PlacesAndAdressesWidget(WebElement element) {
		super(element);
	}

	public WebElement getPlaceOrAddressInList(String placeOrAddressItem) {
		return placesAndAdressesItems.stream().filter(t -> t.getText().contentEquals(placeOrAddressItem)).findFirst().orElse(null);
	}

	public Boolean isPlaceOrAddressInList(String placeOrAddressItem) {
		return placesAndAdressesItems.stream().filter(t -> t.getText().contentEquals(placeOrAddressItem)).findFirst().isPresent();
	}

	public void checkSearchResults(String placeOrAddressItem) {
		placesAndAdressesItems.stream().forEach(t -> assertThat(Utility.deAccent(t.getText()).trim().toLowerCase(), CoreMatchers.containsString(Utility.deAccent(placeOrAddressItem).trim().toLowerCase())));
	}

	public List<String> getAllPlacesOrAdressesItems() {
		return placesAndAdressesItems.stream().map(t -> t.getText()).collect(Collectors.toList());
	}
}
