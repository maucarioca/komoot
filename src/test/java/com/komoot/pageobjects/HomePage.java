package com.komoot.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage extends MasterPage {
	
	private static HomePage homePage;

	private HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}
	
	public void navigateTo(String url) {
		getDriver().get(url);
	}

	public static HomePage getInstance(WebDriver driver) {
		if (homePage == null) 
			homePage = new HomePage(driver); 
  
        return homePage; 
	}
}
