package com.komoot.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.komoot.pageobjects.widgets.MenuBarWidget;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import util.WebDriverUtils;

public class HomePage extends MasterPage {

	private static HomePage homePage = null;

	@FindBy(xpath="//div[@class='print:tw-hidden']")
	private MenuBarWidget menuBarWidget;

	private HomePage(WebDriver driver) {
		super(driver);
	}

	public static HomePage getInstance(WebDriver driver) {
		if (homePage == null) {
			homePage = new HomePage(driver);
			PageFactory.initElements(new AppiumFieldDecorator(getDriver()), homePage);
		}
		return homePage; 
	}

	public void selectLanguage(String languageValue) {
		WebDriverUtils.clickJs(getDriver(), homePage.menuBarWidget.getSelectionLanguage());
		WebDriverUtils.click(getDriver(), homePage.menuBarWidget.getLanguageItemElement(languageValue));
		WebDriverUtils.waitForLoad(getDriver());
		if (!languageValue.contentEquals("English")) {
			WebDriverUtils.sleep(5000);
			WebDriverUtils.click(getDriver(), homePage.menuBarWidget.getBtnNoSwitchLanguage());
		}
	}
	
	public MenuBarWidget getMenuBarWidget() {
		return menuBarWidget;
	}
}
