package variousConcepts;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class LearnTestNG {
	WebDriver driver;
	
	By User_name_field =By.xpath("//input[@id='username']");
	By password_field = By.xpath("//input[@id='password']");
	By signin_field = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By Dashboard_header_field = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	
	By customers_Field = By.linkText("Customers");
	By Add_Customer_Field = By.xpath("//a[contains(text(),'Add Customer')]");
	
	By Full_name_Field = By.xpath("//input[@id='account']");
	By Company_Dropdown = By.xpath("//select[@id='cid']");
	By Email_Field = By.xpath("//input[@id='email']");
	By Phone_Field = By.xpath("//input[@id='phone']");
	By Address_Field = By.xpath("//input[@id='address']");
	By City_Field = By.xpath("//input[@id='city']");
	By State_Field = By.xpath("//input[@id='state']");
	By Zip_Field = By.xpath("//input[@id='zip']");
	By Confirm_password_Field = By.xpath("//input[@id='cpassword']");
	By tags_Field = By.xpath("//select[@id='tags']");
	By Submit_Save_Field = By.xpath("//button[@id='submit']");
	By Country_Dropdown = By.xpath("//select[@id='country']");
	By Currency_Dropdown = By.xpath("//select[@id='currency']");
	By group_Dropdown = By.xpath("//select[@id='group']");
	
	//Test data
	
	String userName = "demo@techfios.com";
	String password = "abc123";
	String DashboardHeader = "Dashboard";
	String Add_Customer_title = "Contacts - iBilling";
	String Full_name = "Deepika";
	String Email = "deepaakb123@gmail.com";
	String Phone = "659435123";
	String Address = "12,Ashfront Bottomway";
	String City = "Atlanta";
	String State = "Georgia";
	String zipcode = "30338";
	String password_new = "abc1234";
	String confirm_password = "abc1234";
	String[] tags_array = {"Hello","Hi"};
	
	String browser = null;
	String url;
	
	@BeforeClass
	public void readConfig() {
		
		//to read config.properties file
		//InputStream //BufferedReader //Scanner //FileReader
		
		try {
			
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			//we need Properties class to read properties file
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	@BeforeMethod
	public void init() {
		
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if(browser.equalsIgnoreCase("firefox")) {
			//Running through Firefox
			System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
	}
	//If there are 2 @Tests then it automatically performs alphabetical order.(2 seperate window execution) we can change the seq by setting priority
	@Test(priority=1)
	public void loginTest() {
		
		driver.findElement(User_name_field).sendKeys(userName);
		driver.findElement(password_field).sendKeys(password);
		driver.findElement(signin_field).click();

		Assert.assertEquals(DashboardHeader, driver.findElement(Dashboard_header_field).getText(),"dashboard not available");
	}
	@Test(priority=2)
	public void addcustomer() {
		loginTest();
		driver.findElement(customers_Field).click();
		driver.findElement(Add_Customer_Field).click();
			
		//Another way of performing assertion
		Boolean fullnameField = driver.findElement(Full_name_Field).isDisplayed();
//		Assert.assertTrue("Add customer page not available ", fullnameField);
		Assert.assertTrue(fullnameField, "true");
		
		
		
		
		driver.findElement(Full_name_Field).sendKeys(Full_name+ generateRandomNumber(999));// To create unique Test data everytime, creating a random number and attaching  
		driver.findElement(Email_Field).sendKeys(generateRandomNumber(9999) +Email);// For email we cant add random number at end, so we add it in the start
		driver.findElement(Phone_Field).sendKeys(Phone);
		driver.findElement(Address_Field).sendKeys(Address);
		driver.findElement(City_Field).sendKeys(City);
		driver.findElement(State_Field).sendKeys(State);
		driver.findElement(Zip_Field).sendKeys(zipcode);
		driver.findElement(tags_Field).sendKeys(tags_array);
		
		selectFromDropDown(Company_Dropdown,"Tesla");
		selectFromDropDown(Country_Dropdown, "India");
		selectFromDropDown(group_Dropdown, "Selenium");
		selectFromDropDown(Currency_Dropdown, "USD");
		
		//WebElement zip_Element = driver.findElement(Zip_Field);
		web_Driver_wait_method(Full_name_Field);
		
		
		
	}
	private void web_Driver_wait_method(By Field) {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Field));
	}
	public int generateRandomNumber(int RangeBound) {
		Random rand = new Random();
		int randomNumber = rand.nextInt(RangeBound);
		return randomNumber;
	}
	public void selectFromDropDown(By byLocator, String visibleText) {
		Select sel = new Select(driver.findElement(byLocator));
		sel.selectByVisibleText(visibleText);
	}
	
}
