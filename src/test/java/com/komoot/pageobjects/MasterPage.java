package com.komoot.pageobjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import io.appium.java_client.functions.ExpectedCondition;

public abstract class MasterPage{

	private static final long TIMEOUT = 30; // seconds
	private static final long POLLING_TIMEOUT = 3; //seconds

	private static WebDriver driver;
	private static FluentWait<WebDriver> wait;
	private static Actions actions;

	protected MasterPage(WebDriver driver) {
		setDriver(driver);
		wait  = new FluentWait<WebDriver>(getDriver())
				.withTimeout(Duration.ofSeconds(TIMEOUT))
				.pollingEvery(Duration.ofSeconds(POLLING_TIMEOUT))
				.ignoring(NoSuchElementException.class);
		actions = new Actions(getDriver());
	}

	public static  WebDriver getDriver() {
		return driver;
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

	public Boolean waitForLoad() {

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long)((JavascriptExecutor)getDriver()).executeScript("return jQuery.active") == 0);
				}
				catch (Exception e) {
					// no jQuery present
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)getDriver()).executeScript("return document.readyState")
						.toString().equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

	private WebElement waitElementBeAvailable(WebElement element) {
		if (waitForLoad()) {
			element = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
			actions.moveToElement(element).perform();
			return element;
		}

		return null;
	}

	protected WebElement waitForElementToBeClickable(WebElement element) {
		if (waitElementBeAvailable(element) != null) {
			element =  wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
			actions.moveToElement(element).perform();

			return element;
		}

		return null;
	}
}
