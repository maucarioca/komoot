package com.komoot.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.komoot.pageobjects.widgets.MenuBarWidget;
import com.komoot.pageobjects.widgets.PlacesAndAdressesWidget;
import com.komoot.pageobjects.widgets.PlacesOnMapWidget;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class RoutePlannerPage extends MasterPage {

	private static RoutePlannerPage routePlannerPage = null;

	@FindBy(xpath = "//input[@placeholder='Search For Place Or Address']")
	private WebElement txtSearchForPlaceOrAddress;

	@FindBy(xpath="//div[@class='print:tw-hidden']")
	private MenuBarWidget menuBarWidget;

	@FindBy(xpath = "//div[@class='o-list-ui o-list-ui--border o-list-ui--separator o-list-ui--flush-vertical']")
	private PlacesOnMapWidget placesOnMapWidget;

	@FindBy(xpath = "//ul[@class='o-list-ui o-list-ui--separator']")
	private PlacesAndAdressesWidget placesOrAdressesWidget;

	private RoutePlannerPage(WebDriver driver) {
		super(driver);
	}

	public static RoutePlannerPage getInstance(WebDriver driver) {
		if (routePlannerPage == null) {
			routePlannerPage = new RoutePlannerPage(driver);
			PageFactory.initElements(new AppiumFieldDecorator(getDriver()), routePlannerPage);
		}
		return routePlannerPage; 
	}

	public WebElement getTxtSearchForPlaceOrAddress() {
		return txtSearchForPlaceOrAddress;
	}

	public MenuBarWidget getMenuBarWidget() {
		return menuBarWidget;
	}

	public PlacesOnMapWidget getPlacesOnMapWidget()  {
		return placesOnMapWidget;
	}

	public PlacesAndAdressesWidget getPlacesOrAdressesWidget() {
		return placesOrAdressesWidget;
	}
}
