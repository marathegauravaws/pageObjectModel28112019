/**
 * 
 */
package testCase;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import base.TestBase;
import pages.AddNewCustomerPage;
import pages.HomePage;
import pages.LoginPage;

/**
 * @author gaurav.marathe
 *
 */
public class LoginAndCreateNewCustomer extends TestBase {
	LoginPage loginPage;
	AddNewCustomerPage addCust;
	HomePage home;

	@BeforeClass
	public void init() throws IOException {
		initialization();
	}

	@Test
	public void enterValidData() {
		String userName = prop.getProperty("username");
		String password = prop.getProperty("password");
		LoginPage login = new LoginPage(driver);
		log("Username and Password Entry is in process");
		login.LoginToApplication(userName, password);
		log("Username and Password Entry done");

	}

	@Test(dependsOnMethods = { "enterValidData" })
	public void createNewCustomer() {
		home = new HomePage(driver);
		log("Navigating to New Customer");
		home.selectOptionFromMenu("New Customer");
		Assert.assertEquals("Add New Customer", getTitleOfSelectedOption(driver));
		log("Add New Customer Page Opened");
		log("Entering all valid details and creating new Customer");
		addCust = new AddNewCustomerPage(driver);
		addCust.createNewCustomerWithData(1);
		log("New Customer Creation Done");

	}

}
