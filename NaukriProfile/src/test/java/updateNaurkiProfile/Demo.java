package updateNaurkiProfile;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Demo {
		private WebDriver driver;
		private WebDriverWait wait;
		private final String username = "rutujas0512@gmail.com";
	    private final String password = "Rutuja@0512";
	   
	
		@BeforeClass
		public void setup() {
			    WebDriverManager.chromedriver().setup();
//			    ChromeOptions options = new ChromeOptions();
//			    options.addArguments("--headless");
		        driver = new ChromeDriver(); // Initialize driver
		        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		        driver.manage().window().maximize();
		        System.out.println("WebDriver initialized successfully.");
		}
		
		@Test(priority=1)
		public void Login() {
			try {
				System.out.println("Navigating to Naukri.com");
				driver.get("https://www.naukri.com");
//				String title = driver.getTitle();
//				System.out.println("Title"+title);
			   Thread.sleep(2000);
			    WebElement loginbtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login_Layer")));
				loginbtn.click();
			   	WebElement UserName = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter your active Email ID / Username']")));
				UserName.sendKeys(username);

				WebElement Password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter your password']")));
				Password.sendKeys(password);
				
			     wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class= 'btn-primary loginButton' and contains(text(),'Login')]"))).click();
				 System.out.println("Logged in successfully");
			
			}
			catch(Exception e) {
				 System.err.println("An error occurred while Login: " + e.getMessage());
			}
		}
		
		@Test(priority=2)
		public void updateLocation() {
			try {
				
			    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='view-profile-wrapper']/a[@href= '/mnjuser/profile']"))).click();
			    System.out.println("Landed on Profile page");
			    JavascriptExecutor js = (JavascriptExecutor) driver;
			    Thread.sleep(3000);
			    js.executeScript("window.scrollBy(0, 8000)"); // Adjust scroll value if needed
			    Thread.sleep(2000); // Wait for elements to load
			    List<WebElement> elements = driver.findElements(By.xpath("//*[@class='edit icon']"));
			    System.out.println("Total edit icons found: " + elements.size());
			 
			    WebElement careerProfile=    new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='lazyDesiredProfile']//span[@class='edit icon']")));
			    careerProfile.click();
				System.out.println("Landed on career profile");
				
			    wait.until(ExpectedConditions.elementToBeClickable(By.id("locationSugg"))).click();
			    
//			    fetch and print all the cities
//			    List <WebElement> listofcities = driver.findElements(By.xpath("//div[@class='topCitiesSuggestions']"));
//			    System.out.println("List of cities- ");
//			    for(WebElement allcities:listofcities) {
//			    	System.out.println(allcities.getText());
//			    }
			    
			    //Remove Pune city 
			    driver.findElement(By.xpath("//li[text()='Pune']")).click();
			    driver.findElement(By.xpath("//span[text()='Preferred work location (Max 10)']")).click();
			    
			    //add city
			    wait.until(ExpectedConditions.elementToBeClickable(By.id("locationSugg"))).click();
			    driver.findElement(By.xpath("//*[text()='Pune']/i")).click(); 
			    driver.findElement(By.id("desiredProfileForm")).click();
			    System.out.println("Pune city added");
			    driver.findElement(By.id("saveDesiredProfile")).click();
			    WebElement ProfileStatus =  driver.findElement(By.xpath("//*[text()='Today']"));
			    String expectedText= "Today";
			    String actualText = ProfileStatus.getText();
			    System.out.println("Actual Text -"+actualText);
			    if( expectedText.equals(actualText)) {
			    	System.out.println("Profile status matches");
			    }
			    driver.findElement(By.xpath("//*[@class=\"nI-gNb-drawer__icon\"]")).click();	
		    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='nI-gNb-icnClass ni-gnb-icn ni-gnb-icn-logout']"))).click();	
		    	System.out.println("Location Updated - Profile Logged Out");
			   
				}
			catch(Exception e) {
				 System.err.println("An error occurred while updating profile location: " + e.getMessage());
			}
		}
		@AfterClass
		public void teardown() {
		    if (driver != null) {
		        driver.quit();
		        System.out.println("WebDriver closed successfully.");
		    }
		}

		
	}


