/**
 * 
 */
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

/**
 * @author gaurav.marathe
 *
 */
public class LoginPage extends TestBase {
	WebDriver driver;
	@FindBy(name = "uid")
	WebElement usrName;
	@FindBy(name = "password")
	WebElement passWrd;
	@FindBy(name = "btnLogin")
	WebElement logInBttn;

	public LoginPage(WebDriver driver) {
	this.driver=driver;
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}

	// set User name
	public void setUserName(String userName) {

		usrName.sendKeys(userName);
	}
	// set Password

	public void setPassword(String password) {

		passWrd.sendKeys(password);
	}

	// Click On Login Button

	public void clickOnLoginButton() {

		logInBttn.click();
	}

	// Login to the Application

	public void LoginToApplication(String userName, String password) {

		setUserName(userName);
		setPassword(password);
		clickOnLoginButton();
	}

}
