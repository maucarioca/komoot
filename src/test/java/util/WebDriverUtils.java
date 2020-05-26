package util;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.functions.ExpectedCondition;

public final class WebDriverUtils {

	private static final long MINIMAL_WAIT = 1000; //millis
	private static final long CLEAR_WAIT = 3000; //millis
	private static final long TIMEOUT = 30; // seconds
	private static final long POLLING_TIMEOUT = 3; //seconds

	private static AppiumFluentWait<WebDriver> wait = null;
	private static Actions actions = null;

	public static Boolean waitForLoad(WebDriver driver) {

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
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
				return ((JavascriptExecutor) driver).executeScript("return document.readyState")
						.toString().equals("complete");
			}
		};

		return getWaitInstance(driver).until(jQueryLoad) && getWaitInstance(driver).until(jsLoad);
	}

	public static WebElement waitElementBeAvailable(WebDriver driver, By by) {
		if (waitForLoad(driver)) {
			WebElement element = getWaitInstance(driver).until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(by)));
			getActionsInstance(driver).moveToElement(element).perform();
			return element;
		}

		return null;
	}

	public static WebElement waitForElementBeVisible(WebDriver driver, WebElement element) {
		if (waitForLoad(driver)) {
			element = getWaitInstance(driver).until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
			getActionsInstance(driver).moveToElement(element).perform();
			return element;
		}

		return null;
	}

	public static WebElement waitForElementBeClickable(WebDriver driver, WebElement element) {
		if (waitForElementBeVisible(driver, element) != null) {
			element =  getWaitInstance(driver).until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
			getActionsInstance(driver).moveToElement(element).perform();
			return element;
		}

		return null;
	}

	private static FluentWait<WebDriver> getWaitInstance(WebDriver driver){
		if (wait==null) {
			return new AppiumFluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(TIMEOUT))
					.pollingEvery(Duration.ofSeconds(POLLING_TIMEOUT))
					.ignoring(NoSuchElementException.class);
		}
		return wait;
	}

	private static Actions getActionsInstance(WebDriver driver){
		if (actions==null) {
			return new Actions(driver);
		}
		return actions;
	}

	public static void navigateTo(WebDriver driver, String url) {
		driver.get(url);
		WebDriverUtils.waitForLoad(driver);
	}

	public static void sleep(){
		sleep(2000);
	}

	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) { 
		}
	}

	public static void click(WebDriver driver, WebElement element) {
		sleep(MINIMAL_WAIT);
		element = waitForElementBeClickable(driver, element);
		element.click();
	}

	public static void click2(WebDriver driver, WebElement element) {
		sleep(MINIMAL_WAIT);
		element = waitForElementBeClickable(driver, element);
		getActionsInstance(driver).moveToElement(element).click(element).build().perform();
	}

	public static void clickJs(WebDriver driver, WebElement element) {
		sleep(MINIMAL_WAIT);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public static void contextClick(WebDriver driver, WebElement element) {
		sleep(MINIMAL_WAIT);
		element = waitForElementBeClickable(driver, element);
		getActionsInstance(driver).moveToElement(element).contextClick(element).build().perform();
	}

	public static void sendKeys(WebDriver driver, WebElement element, String text) {
		sleep(MINIMAL_WAIT);
		element = waitForElementBeClickable(driver, element);
		element.clear();
		sleep(CLEAR_WAIT);
		element.sendKeys(text);
	}

	public static void sendKeys2(WebDriver driver, WebElement element, String text) {
		sleep(MINIMAL_WAIT);
		element = waitForElementBeClickable(driver, element);
		element.clear();
		sleep(CLEAR_WAIT);
		getActionsInstance(driver).sendKeys(element, text).build().perform();
	}
	
	public static void sendKeysJs(WebDriver driver, WebElement element, String text) {
		sleep(MINIMAL_WAIT);
		element = waitForElementBeClickable(driver, element);
		element.clear();
		sleep(CLEAR_WAIT);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='"+ text +"';", element);
	}
	
	public static void sendKeysNoClear(WebDriver driver, WebElement element, String text) {
		sleep(MINIMAL_WAIT);
		element = waitForElementBeClickable(driver, element);
		element.sendKeys(text);
	}

	public static String getText(WebDriver driver, WebElement element) {
		sleep(MINIMAL_WAIT);
		element = waitForElementBeVisible(driver, element);
		return element.getText().trim(); 
	}
}
