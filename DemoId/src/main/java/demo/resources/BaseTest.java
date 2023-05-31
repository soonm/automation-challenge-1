package demo.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	private List<CartElement> cartList;
	private List<String> items;

	public WebDriver Init() throws IOException{
		WebDriver driver;
		Properties properties = new Properties();
		
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
		
		cartList =new ArrayList<CartElement>();
		String itemsProperty = properties.getProperty("items");
	    items = Arrays.asList(itemsProperty.split(";"));
	    
	    return driver;	
	}
	
	public List<String> getItems() {
		return items;
	}
	
	public List<CartElement> getCartList() {
		return cartList;
	}
}
