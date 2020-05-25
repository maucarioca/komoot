package com.komoot.pageobjects;

import org.openqa.selenium.WebDriver;

public abstract class MasterPage {

	private static WebDriver driver;

	protected MasterPage(WebDriver driver) {
		setDriver(driver);
	}

	public static  WebDriver getDriver() {
		return MasterPage.driver;
	}

	private void setDriver(WebDriver driver) {
		MasterPage.driver = driver;
	}

	public String getPageTitle() {
		return getDriver().getTitle();
	}

	public String getPageCurrentUrl() {
		return getDriver().getCurrentUrl();
	}
}
