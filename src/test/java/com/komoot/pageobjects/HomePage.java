package com.komoot.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage extends MasterPage {

	private static HomePage homePage;

	@FindBy(xpath = "//span[@class='tw-font-bold tw-mr-1']")
	private WebElement selectionLanguage;

	@FindBy(xpath = "//a[@data-name='locale']")
	private List<WebElement> languageItems;

	private HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}

	public static HomePage getInstance(WebDriver driver) {
		if (homePage == null) 
			homePage = new HomePage(driver); 

		return homePage; 
	}

	public void selectLanguage(String languageValue) {
		waitForElementToBeClickable(selectionLanguage).click();
		for(WebElement element : languageItems) {
			if (element.getText().contentEquals(languageValue)) {
				waitForElementToBeClickable(element).click();
				waitForLoad();
				break;
			}
		}
	}

	public void navigateTo(String url) {
		getDriver().get(url);
		waitForLoad();
	}
}
