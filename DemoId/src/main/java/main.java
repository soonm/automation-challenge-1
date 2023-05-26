import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import io.github.bonigarcia.wdm.WebDriverManager;


public class main {
	WebDriver driver;
	Properties properties;
	List<cartElement> cartList;
	String itemsProperty;
	List<String> items;
    
	
	@BeforeMethod
	public void SetUp() throws IOException{
		
		properties = new Properties();
        FileInputStream fis = new FileInputStream("config.properties");
        properties.load(fis);
        
        String browser = properties.getProperty("browser");
        int implicitWaitTime= Integer.valueOf(properties.getProperty("implicitWaitTime"));

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver= new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Invalid browser: " + browser);
        }
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
		driver.get(properties.getProperty("URL"));
		
		cartList =new ArrayList<cartElement>();
		itemsProperty = properties.getProperty("items");
	    items = Arrays.asList(itemsProperty.split(";"));
		
	}
	
	@Test
	public void fillCart() throws IOException {
		 
		 keywords.fillCart(driver, items, cartList);
		 
		 cartElement actualTotalProperties= keywords.getActualTotalCartElementProperties(cartList);
		 cartElement totalProperties= keywords.getTotalCartElementProperties(driver);

	     Assert.assertEquals(totalProperties.getKcal(), actualTotalProperties.getKcal(), "KCal total sum does not match");
	     Assert.assertEquals(totalProperties.getProtein(), actualTotalProperties.getProtein(), "Protein total sum does not match");
	     Assert.assertEquals(totalProperties.getFat(), actualTotalProperties.getFat(), "Fat total sum does not match");
	     Assert.assertEquals(totalProperties.getCarbs(), actualTotalProperties.getCarbs(), "Carbs total sum does not match");

		
	}
	
	@Test 
	public void emptyCard() {
		
		keywords.fillCart(driver, items, cartList);
		keywords.emptyCart(driver, cartList.size());
		cartElement totalProperties= keywords.getTotalCartElementProperties(driver);
		Assert.assertEquals(totalProperties.getKcal(), 0.00, "KCal total sum does not match");
		Assert.assertEquals(totalProperties.getProtein(), 0.00, "Protein total sum does not match");
		Assert.assertEquals(totalProperties.getFat(), 0.00, "Fat total sum does not match");
		Assert.assertEquals(totalProperties.getCarbs(), 0.00, "Carbs total sum does not match");
		
		
	}
	
	@AfterMethod
	public void tearDown(){
		
		driver.quit();
	}


}
