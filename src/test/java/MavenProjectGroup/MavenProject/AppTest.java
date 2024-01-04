package MavenProjectGroup.MavenProject;

import static org.testng.Assert.assertFalse;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Listeners;

@Listeners(MavenProjectGroup.MavenProject.EventListner.class)
public class AppTest 
{
	WebDriver driver;
	String homePageTitle;
	String aboutMe;
	Integer randomNumber;

	// SoftAssert objSoftAssert;
	// Initializing all the required variable
	@Parameters({ "browser" })
	@BeforeTest
	public void SetUp(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		System.out.println("Test is running in " + browser + "browser");
		Reporter.log("Test is running in " + browser + "browser");

		driver.manage().window().maximize();
		driver.get("http://eaapp.somee.com/");

		Random random = new Random();
		randomNumber = random.nextInt(100);

		homePageTitle = "Home - Execute Automation Employee App";
		aboutMe = "ExecuteAutomation Employee Application v1.0 is a simple web application for showing very few functionality of Employee details.";
	}

	@DataProvider(name = "RegistrationCredentials")
	public Object[][] DataForTest() {
		return new Object[][] {
				{ "manchu" + randomNumber, "Manchana1@", "Manchana1@", "manchu" + randomNumber + "@gmail.com" },
				{ "man" + randomNumber, "Manchana1@", "Manchana1@", "man" + randomNumber + "@gmail.com" } };
	}

	@DataProvider(name = "LoginCredentials")
	public Object[][] DataForLoginTest() {
		return new Object[][] { { "manchu" + randomNumber, "Manchana1@" }, { "man" + randomNumber, "Manchana1@" } };
	}

	@DataProvider(name = "browser")
	public Object[][] DataForBrowserTest() {
		return new Object[][] { { "chrome" }, { "firefox" } };
	}

	// User should register with valid data
	@Parameters({ "userNameData", "passwordData", "confirmPasswordData", "emailData" })
//	@Test(dataProvider="RegistrationCredentials", description="This is a test to verify user resistration in Eapp.")
	@Test(description = "This is a test to verify user resistration in Eapp.")
	public void UserRegistration(String userName, String password, String confirmPassword, String email) {
		SoftAssert ObjSoftAssert = new SoftAssert();
		driver.findElement(By.id("registerLink")).click();

		ObjSoftAssert.assertNotEquals("", userName, "Username is Blank!!");
		driver.findElement(By.id("UserName")).sendKeys(userName);
		Reporter.log("Username inserted in username text box.");

		ObjSoftAssert.assertNotEquals("", password, "Password is Blank!!");
		driver.findElement(By.id("Password")).sendKeys(password);
		Reporter.log("Password inserted in password text box.");

		ObjSoftAssert.assertNotEquals("", confirmPassword, "Confirm Password is Blank!!!");
		driver.findElement(By.id("ConfirmPassword")).sendKeys(confirmPassword);
		Reporter.log("Confirm password is inserted in confirm password text box.");

		driver.findElement(By.name("Email")).sendKeys(email);
		Reporter.log("Email inserted in email text box.");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.findElement(By.xpath("/html/body/div[2]/form/div[6]/div/input")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"logoutForm\"]/ul/li[2]/a")));

		 List<WebElement> IfRegister = driver.findElements(By.xpath("//*[@id=\"logoutForm\"]/ul/li[2]/a"));
		 Reporter.log("Ifregister ..." + IfRegister.get(0).getText());

		// Soft assertion
		ObjSoftAssert.assertEquals(IfRegister.isEmpty(), false, "Log Off Button is not available!!!");
//		ObjSoftAssert.assertTrue(IfRegister.size() > 0, "This Assertion is Fail!!!");

		// Hard Assertion
		/*
		 * assertTrue(IfRegister.size()>0); assertFalse(IfRegister.size()==0);
		 * assertEquals(IfRegister.isEmpty(), false); assertEquals(IfRegister.size()==0,
		 * false);
		 */

		if (!IfRegister.isEmpty()) {
			IfRegister.get(0).click();
		}
		ObjSoftAssert.assertAll("This Is asserting for all Soft Assertion");
		// To print the message in report and console, add true
		Reporter.log("This is End of First Test Case and verified User Registration and printed in console as well", 1,
				true);

	}

	// User should able to redirect to home page of eapp
	@Test(priority = 3, description = "This is a test to verify Eapp home page")
	public void EappHomePage() {
		SoftAssert objSoftAssert = new SoftAssert();
		driver.findElement(By.xpath("//div[1]/div[2]/ul/li[1]/a")).click();
		// Check if image is visible
		Boolean ifImage = driver.findElement(By.xpath("//div[2]/div[1]/table/tbody/tr/td/img")).isDisplayed();
		objSoftAssert.assertTrue(ifImage, "Home page image is not visible!");
		// assertEquals(ifImage, true, "Home page image is not visible from hard
		// assert!");
		objSoftAssert.assertAll();
		Reporter.log("Eapp home page is verified!");
	}

	@Test(priority = 4, description = "This is a test to verify Eapp about page.")
	public void EappAboutPage() {
		SoftAssert objSoftAssert = new SoftAssert();
		driver.get("http://eaapp.somee.com/Home/About");
		WebElement paragraph = driver.findElement(By.xpath("//div[2]/p"));
		objSoftAssert.assertEquals(paragraph.getText(), aboutMe, "The paragraph text is missed match!");
		objSoftAssert.assertAll();
		Reporter.log("About page is verified!");
	}

	@Test(priority = 5, description = "This is a test to verify Eapp employee list page.")
	public void EappEmployeeList() {
		SoftAssert objSoftAssert = new SoftAssert();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// driver.get("http://eaapp.somee.com/Employee");
		driver.findElement(By.xpath("//li[3]/a")).click();
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));
		String firstName = element.findElements(By.tagName("td")).get(0).getText();
		objSoftAssert.assertNotNull(firstName, "The firstname is present as " + firstName);
		Reporter.log("The firstname is present as " + firstName);
		objSoftAssert.assertAll();
		Reporter.log("Employee list page is verified!");
	}

	@Parameters({ "userNameData", "passwordData" })
//	@Test(priority = 2, dependsOnMethods = "UserRegistration", description="This is a test to verify Eapp login page.", dataProvider ="LoginCredentials")
	@Test(priority = 2, dependsOnMethods = "UserRegistration", description = "This is a test to verify Eapp login page.")
	public void EappLogin(String userName, String password) {
		SoftAssert objSoftAssert = new SoftAssert();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.findElement(By.id("loginLink")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("UserName")));

		objSoftAssert.assertNotEquals("", userName, "Username is Blank!!");
		driver.findElement(By.id("UserName")).sendKeys(userName);
		objSoftAssert.assertNotEquals("", password, "Password is Blank!!");
		driver.findElement(By.id("Password")).sendKeys(password);

		// Why this wait always gives error although it pass without the wait condition?
//		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.findElement(By.xpath("//div[4]//input")).click();
//		wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\\\"logoutForm\\\"]/ul/li[1]/a")));

		List<WebElement> ifUserName = driver.findElements(By.xpath("//*[@id=\"logoutForm\"]/ul/li[1]/a"));
		// Soft assertion
		objSoftAssert.assertEquals("Hello " + userName + "!", ifUserName.get(0).getText(),
				"Hi Username, is not available!!!");
		objSoftAssert.assertAll();
		Reporter.log(" Verified logging to username " + ifUserName.get(0).getText());
	}

	// Ending the test
	@AfterTest
	public void TearDown() {
		driver.close();
	}
}

