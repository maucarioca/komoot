package com.komoot.tests;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;

import org.hamcrest.CoreMatchers;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.komoot.beans.PlaceOnMapBean;
import com.komoot.beans.PlaceOrAddressBean;
import com.komoot.pageobjects.HomePage;
import com.komoot.pageobjects.RoutePlannerPage;
import com.opencsv.exceptions.CsvException;

import util.WebDriverUtils;

public class RoutePlannerTests extends MasterTests {

	private HomePage homePage;
	private RoutePlannerPage routePlannerPage;

	@DataProvider(name = "place_on_map_test_data_provider")
	public Iterator<PlaceOnMapBean> placesOrAddressesTestDataProvider() throws IOException, CsvException, NoSuchFieldException, SecurityException {
		return getDataProviderContent("place_on_map_test_data_provider.csv", new PlaceOnMapBean());
	}

	@DataProvider(name = "place_or_address_test_data_provider")
	public Iterator<PlaceOrAddressBean> placesOnMapTestDataProvider() throws IOException, CsvException, NoSuchFieldException, SecurityException {
		return getDataProviderContent("place_or_address_test_data_provider.csv", new PlaceOrAddressBean());
	}

	@BeforeTest
	public void setUpTest() {
		WebDriverUtils.navigateTo(getDriver(),getUrl());
		homePage = HomePage.getInstance(getDriver());
		routePlannerPage = RoutePlannerPage.getInstance(getDriver());
		WebDriverUtils.click(getDriver(), homePage.getMenuBarWidget().getMenuItemElement("Route planner"));
		WebDriverUtils.waitForLoad(getDriver());
	}

	@Test (groups="routePlannerTests", dataProvider="place_on_map_test_data_provider")
	public void checkExpectedPlacesOnMap(PlaceOnMapBean placeItem)  {
		WebDriverUtils.click(getDriver(), routePlannerPage.getTxtSearchForPlaceOrAddress());
		WebDriverUtils.waitForElementBeVisible(getDriver(), routePlannerPage.getPlacesOnMapWidget().getWrappedElement());
		assertThat(routePlannerPage.getPlacesOnMapWidget().checkPlaceInList(placeItem.getPlaceValue()), CoreMatchers.equalTo(true));
	}

	@Test (groups="routePlannerTests")
	public void checkNotExistentPlaceInTheMap()  {
		WebDriverUtils.click(getDriver(), routePlannerPage.getTxtSearchForPlaceOrAddress());
		WebDriverUtils.waitForElementBeVisible(getDriver(), routePlannerPage.getPlacesOnMapWidget().getWrappedElement());
		routePlannerPage.getPlacesOnMapWidget().checkPlaceInList("any wrong place");
	}

	@Test (groups="routePlannerTests", dataProvider="place_or_address_test_data_provider")
	public void searchAndValidateResults(PlaceOrAddressBean placeOrAddress)  {
		WebDriverUtils.click(getDriver(), routePlannerPage.getTxtSearchForPlaceOrAddress());
		WebDriverUtils.sendKeys(getDriver(), routePlannerPage.getTxtSearchForPlaceOrAddress(), placeOrAddress.getPlaceOrAdddressValue());
		WebDriverUtils.waitForElementBeClickable(getDriver(), routePlannerPage.getPlacesOrAdressesWidget().getWrappedElement());
		routePlannerPage.getPlacesOrAdressesWidget().checkSearchResults(placeOrAddress.getPlaceOrAdddressValue());
	}

	@Test (groups="routePlannerTests")
	public void searchAndValidateNoResults()  {
		WebDriverUtils.click(getDriver(), routePlannerPage.getTxtSearchForPlaceOrAddress());
		WebDriverUtils.sendKeys(getDriver(), routePlannerPage.getTxtSearchForPlaceOrAddress(), "trying to find an inexistent place");
		try {
			WebDriverUtils.waitForElementBeClickable(getDriver(), routePlannerPage.getPlacesOrAdressesWidget().getWrappedElement());
			assertTrue("Adresses list was found and it's a failure.", false);
		} catch (NoSuchElementException e) {
			assertTrue("Adresses list wasn't found as expected.", true);
		}
	}

	@AfterTest
	public void tearDownTest(){
		homePage = null;
		routePlannerPage = null;
	}
}
