package com.komoot.pageobjects.widgets;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.appium.java_client.pagefactory.Widget;

public class MenuBarWidget extends Widget {

	@FindBy(xpath = "//a[@class='c-logo c-logo--responsive tw-flex-none tw-mr-3']")
	private WebElement logo;

	@FindBy(xpath="//ul[@class='tw-m-0 tw-hidden md:tw-inline-flex tw-pr-6']/li/a")
	private List<WebElement> menus;

	@FindBy(xpath = "//button[@class='tw-overflow-hidden tw-inline-flex tw-items-center tw-text-white hover:tw-text-white-dark']/span")
	private WebElement btnNoSwitch;

	@FindBy(xpath = "//span[@class='icon-user-lang tw-inline-flex tw-items-center tw-justify-center tw-text-inherit tw-mr-2 tw-text-white-40']")
	private WebElement selectionLanguage;

	@FindBy(xpath = "//a[@data-name='locale']")
	private List<WebElement> languageItems;

	@FindBy(xpath = "//button[@class='tw-inline-flex tw-justify-center tw-items-center c-btn c-btn--primary-inv']/span")
	private WebElement btnSignUpOrLogin;

	public MenuBarWidget(WebElement element) {
		super(element);
	}

	public WebElement getLanguageItemElement (String languageValue) {
		return languageItems.stream().filter(t -> t.getText().contentEquals(languageValue)).findFirst().get();
	}

	public WebElement getMenuItemElement (String menuText) {
		return menus.stream().filter(t -> t.getText().contentEquals(menuText)).findFirst().get();
	}

	public WebElement getSelectionLanguage() {
		return selectionLanguage;
	}

	public List<String> getMenus() {
		return menus.stream().map(t -> t.getText()).collect(Collectors.toList());
	}

	public List<String> getlanguageItems() {
		return languageItems.stream().map(t -> t.getText()).collect(Collectors.toList());
	}
	
	public WebElement getBtnNoSwitchLanguage() {
		return btnNoSwitch;
	}
}
