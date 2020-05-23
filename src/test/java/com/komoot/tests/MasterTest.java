package com.komoot.tests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.util.ResourceUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.komoot.beans.LanguageBean;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public abstract class MasterTest {

	protected static Class<?> browserClass;
	protected static WebDriver driver;
	private static Properties properties;

	@BeforeSuite
	public static void setupClass() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.properties"));
		provideBrowser();
	}

	@AfterSuite(alwaysRun = true)
	public static void tearDownClass() throws ClassNotFoundException {
		if (!Objects.isNull(driver))
			driver.quit();
	}

	private static void provideBrowser() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		DriverManagerType driverManagerType = null;
		if (StringUtils.equalsAny(getBrowserName(), "gc", "chrome","googlechrome","google_chrome","gchrome")) {
			driverManagerType = DriverManagerType.CHROME;	
		} else if (StringUtils.equalsAny(getBrowserName(), "ff", "firefox","mozilla","mozilla_firefox","mozillafirefox")) {
			driverManagerType = DriverManagerType.FIREFOX;
		}	
		WebDriverManager.getInstance(driverManagerType).setup();
		browserClass =  Class.forName(driverManagerType.browserClass());
		driver = (WebDriver) browserClass.newInstance();
		driver.manage().window().maximize();
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
