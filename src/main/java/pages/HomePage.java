/**
 * 
 */
package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

/**
 * @author gaurav.marathe
 *
 */
public class HomePage extends TestBase {
	WebDriver driver;
	@FindBy(xpath = "//td[contains(text(),'User: Naveen K')]")
	@CacheLookup
	WebElement userNameLabel;

	@FindBy(xpath = "//a[contains(text(),'Contacts')]")
	WebElement contactsLink;

	@FindBy(xpath = "//a[contains(text(),'New Contact')]")
	WebElement newContactLink;

	@FindBy(xpath = "//a[contains(text(),'Deals')]")
	WebElement dealsLink;

	@FindBy(xpath = "//a[contains(text(),'Tasks')]")
	WebElement tasksLink;

	By homePageMenu = By.xpath("//*[@class='menusubnav']/li/a");

	// Initializing the Page Objects:
	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver=driver;

	}

	public String verifyHomePageTitle() {
		return driver.getTitle();
	}

	public void selectOptionFromMenu(String optionUWantToSelect) {

		List<WebElement> subMenu;
		subMenu = driver.findElements(homePageMenu);

		for (WebElement prtsubmenu : subMenu) {
			if (prtsubmenu.getText().equalsIgnoreCase(optionUWantToSelect)) {
				prtsubmenu.click();
				break;
			}

		}

	}

}
