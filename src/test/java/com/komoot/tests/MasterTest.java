package com.komoot.tests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.util.ResourceUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.komoot.beans.LanguageBean;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class MasterTest {

	private static WebDriver driver;
	private static Properties properties;

	@BeforeSuite
	public static void setupClass() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.properties"));
		provideBrowser();
	}

	@AfterSuite(alwaysRun = true)
	public static void tearDownClass() throws ClassNotFoundException {
		if (!Objects.isNull(getDriver()))
			getDriver().quit();
	}

	private static void provideBrowser() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		if (StringUtils.equalsAny(getBrowserName(), "gc", "chrome","googlechrome","google_chrome","gchrome")) {
			provideChromeBrowser();	
		} else if (StringUtils.equalsAny(getBrowserName(), "ff", "firefox","mozilla","mozilla_firefox","mozillafirefox")) {
			provideFirefoxBrowser();
		}	else if (StringUtils.equalsAny(getBrowserName(), "ie", "iexplore","internetexplorer","internet_explorer")) {
			provideIEBrowser();
		}
		getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		getDriver().manage().window().maximize();
	}

	private static void provideIEBrowser() throws IOException {
		Runtime.getRuntime().exec("taskkill /im IEDriverServer.exe /f");
		WebDriverManager.iedriver().clearPreferences();
		WebDriverManager.iedriver().setup();
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability("ie.enableFullPageScreenshot", false);
		capabilities.setCapability("ignoreProtectedModeSettings", true);
		capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.requireWindowFocus();
		options.merge(capabilities);
		setDriver(new InternetExplorerDriver(options));
	}

	private static void provideChromeBrowser() throws  IOException {
		Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");
		WebDriverManager.chromedriver().clearPreferences();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-gpu");
		DesiredCapabilities chrome = DesiredCapabilities.chrome();
		chrome.setJavascriptEnabled(true);
		options.setCapability(ChromeOptions.CAPABILITY, options);
		setDriver(new ChromeDriver(options));
	}

	private static void provideFirefoxBrowser() throws IOException {
		Runtime.getRuntime().exec("taskkill /im geckodriver.exe /f");
		WebDriverManager.firefoxdriver().clearPreferences();
		WebDriverManager.firefoxdriver().setup();
		FirefoxOptions options = new FirefoxOptions();
		options.setCapability("marionette", true);
		options.setCapability("browser.download.folderList", 2); 
		options.setCapability("browser.download.manager.showWhenStarting", false);
		options.setCapability("browser.helperApps.neverAsk.saveToDisk", true);
		options.setCapability("browser.privatebrowsing.autostart", true);
		setDriver(new FirefoxDriver(options));
	}

	private static void setDriver(WebDriver driver) {
		MasterTest.driver = driver;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static String getBrowserName() {
		return properties.get("browser").toString().toLowerCase();
	}

	public static String getEnvironmentName() {
		return properties.get("environment").toString().toLowerCase();
	}

	public static String getUrl() {
		return properties.get(getEnvironmentName() + ".url").toString();
	}

	public static <T> Iterator<T> getDataProviderContent(String resourceName, Class<? extends T> className) throws IOException, CsvException  {
		ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<T>();
		strategy.setType(className);        
		strategy.setColumnMapping(LanguageBean.getHeaders());

		File file = ResourceUtils.getFile(MasterTest.class.getClassLoader().getResource("testdata/" + resourceName));

		CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(new FileReader(file.getAbsolutePath()))
				.withMappingStrategy(strategy)
				.withSkipLines(1)
				.withIgnoreLeadingWhiteSpace(true)
				.build();

		return csvToBean.iterator();
	}
}
