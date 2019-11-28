package testCase;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.TestBase;
import pages.LoginPage;

public class LoginWithValidCredentials extends TestBase {
	LoginPage loginPage;

	@BeforeClass
	public void init() throws IOException {
		initialization();
	}

	@Test
	public void loginToAppllication() {
		String userName = prop.getProperty("username");
		String password = prop.getProperty("password");
		loginPage = new LoginPage(driver);
		loginPage.LoginToApplication(userName, password);
	}

}
