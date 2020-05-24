package com.komoot.tests;

import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Iterator;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.komoot.beans.LanguageBean;
import com.komoot.pageobjects.HomePage;
import com.opencsv.exceptions.CsvException;

public class LanguageTest extends MasterTest {

	private HomePage homePage;

	@DataProvider(name = "language_test_data_provider")
	public Iterator<LanguageBean> languageTestDataProvider() throws IOException, CsvException, NoSuchFieldException, SecurityException {
		return getDataProviderContent("language_test_data.csv", LanguageBean.class);
	}

	@BeforeClass
	public void setUpPages() {
		homePage = HomePage.getInstance(getDriver());
		homePage.navigateTo(getUrl());
	}

	@Test (dataProvider="language_test_data_provider")
	public void changeLanguage(LanguageBean languageBean)  {
		homePage.selectLanguage(languageBean.getLanguageValue());
		assertThat(driver.getCurrentUrl(), CoreMatchers.equalTo(languageBean.getExpectedUrl()));
	}
}
