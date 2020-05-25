package com.komoot.tests;

import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Iterator;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.komoot.beans.PlaceOnMapBean;
import com.komoot.pageobjects.HomePage;
import com.komoot.pageobjects.RoutePlannerPage;
import com.opencsv.exceptions.CsvException;

import util.WebDriverUtils;

public class RoutePlannerTests extends MasterTests {

	private HomePage homePage;
	private RoutePlannerPage routePlannerPage;

	@DataProvider(name = "place_on_map_test_data_provider")
	public Iterator<PlaceOnMapBean> placesOnMapTestDataProvider() throws IOException, CsvException, NoSuchFieldException, SecurityException {
		return getDataProviderContent("places_on_map_test_data.csv", new PlaceOnMapBean());
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
	public void checkExpectedPlacesInTheMap(PlaceOnMapBean placeItem)  {
		WebDriverUtils.click(getDriver(), routePlannerPage.getTxtSearchForPlaceOrAddress());
		WebDriverUtils.waitElementBeVisible(getDriver(), routePlannerPage.getPlacesOnMampWidget().getWrappedElement());
		assertThat(routePlannerPage.getPlacesOnMampWidget().isPlaceInList(placeItem.getPlaceValue()), CoreMatchers.equalTo(Boolean.TRUE));
	}

	@AfterTest
	public void tearDownTest(){
		homePage = null;
		routePlannerPage = null;
	}
}
