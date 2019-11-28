/**
 * 
 */
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;
import excelReader.Excel_Reader;

/**
 * @author gaurav.marathe
 *
 */
public class AddNewCustomerPage extends TestBase{
	WebDriver driver;
	@FindBy(xpath = "//input[@name='name']")
	WebElement name;
	@FindBy(xpath = "//*[@type='radio'][@value='m']")
	WebElement male;
	@FindBy(xpath = "//*[@type='radio'][@value='f']")
	WebElement female;
	@FindBy(xpath = "//*[@type='date']")
	WebElement date;
	@FindBy(xpath = "//input[@name='city']")
	WebElement city;
	@FindBy(xpath = "//input[@name='state']")
	WebElement state;
	@FindBy(xpath = "//input[@name='pinno']")
	WebElement pin;
	@FindBy(xpath = "//input[@name='telephoneno']")
	WebElement telephoneno;
	@FindBy(xpath = "//input[@name='emailid']")
	WebElement emailid;
	@FindBy(xpath = "/html/body/table/tbody/tr/td/table/tbody/tr[14]/td[2]/input[1]")
	WebElement Submit;
	@FindBy(xpath = "//input[@name='password']")
	WebElement Password;
	@FindBy(xpath = "//*[text()='Address']//following::textarea")
	WebElement Address;
	Excel_Reader excel2;
	
	String CustomerTestDatasheet="CustomerTestData";
	String UsernamePasswrdCredential="LoginTestData";
		
	
	public AddNewCustomerPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}

	public void setCustomerName(String customerName) {

		// enter Customer Name
		name.sendKeys(customerName);
	}

	public void setGenderRadioButton(String Gender) {

		if (Gender.equalsIgnoreCase("Male")) {

			male.click();
		} else {
			female.click();
		}

	}

	public void setDateOfBirth(String DateOfBirth) {
		date.sendKeys(DateOfBirth);

	}

	public void setAddress(String address) {

		Address.sendKeys(address);

	}

	public void setCity(String cityName) {

		// Enter City
		city.sendKeys(cityName);

	}

	public void setState(String stateName) {

		// Enter State
		state.sendKeys(stateName);

	}

	public void setPinNo(String pinNo) {
		// Enter PIN
		pin.sendKeys(pinNo);

	}

	public void setPhoneNo(String phoneNo) {
		// Enter Mobile Number
		telephoneno.sendKeys(phoneNo);

	}

	public void setEmailId(String emailId) {
		// Enter Email Id
		emailid.sendKeys(emailId);

	}

	public void setPassword(String password) {
		// enterPassword
		Password.sendKeys(password);
	}

	public void clickSubmitButton() {
		
		Submit.click();

	}
	public void createNewCustomerWithData(int takedataColumn) {		
		String path = System.getProperty("user.dir") + "/src/main/java/data/" + "TestData"+".xlsx";					
		excel2 = new Excel_Reader(path);
		String[][] logindata = getData("TestData", "LoginTestData");
		setCustomerName(excel2.getCustomerData(CustomerTestDatasheet, "CustomerName", takedataColumn));
		setGenderRadioButton(excel2.getCustomerData(CustomerTestDatasheet, "Gender", takedataColumn));
		setDateOfBirth(excel2.getCustomerData(CustomerTestDatasheet, "DateofBirth", takedataColumn));
		setAddress(excel2.getCustomerData(CustomerTestDatasheet, "Address", takedataColumn));
		setCity(excel2.getCustomerData(CustomerTestDatasheet, "City", takedataColumn));
		setState(excel2.getCustomerData(CustomerTestDatasheet, "State", takedataColumn));
		setPinNo(excel2.getCustomerData(CustomerTestDatasheet, "Pin", takedataColumn));
		setPhoneNo(excel2.getCustomerData(CustomerTestDatasheet, "MobileNumber", takedataColumn));		
		setEmailId(logindata[2][0]);
		setPassword(logindata[1][1]);		
		clickSubmitButton();
	}

	
	
}
