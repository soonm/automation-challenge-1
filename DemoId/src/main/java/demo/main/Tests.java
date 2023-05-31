package demo.main;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import demo.resources.CartElement;
import demo.resources.Keywords;
import demo.resources.BaseTest;

public class Tests{
    public WebDriver driver;
	BaseTest objinit;
	
	@BeforeMethod
	public void SetUp() throws IOException {
		objinit = new BaseTest();		
		driver=objinit.Init();
	}

	@Test
	public void fillCart() throws IOException {
		Keywords.fillCart(driver, objinit.getItems(), objinit.getCartList());
		CartElement actualTotalProperties= Keywords.getActualTotalCartElementProperties(objinit.getCartList());
		CartElement totalProperties= Keywords.getTotalCartElementProperties(driver);
	    Assert.assertEquals(totalProperties.getKcal(), actualTotalProperties.getKcal(), "KCal total sum does not match");
	    Assert.assertEquals(totalProperties.getProtein(), actualTotalProperties.getProtein(), "Protein total sum does not match");
	    Assert.assertEquals(totalProperties.getFat(), actualTotalProperties.getFat(), "Fat total sum does not match");
	    Assert.assertEquals(totalProperties.getCarbs(), actualTotalProperties.getCarbs(), "Carbs total sum does not match");
	}
	
	@Test 
	public void emptyCart() throws IOException {
		Keywords.fillCart(driver, objinit.getItems(), objinit.getCartList());
		Keywords.emptyCart(driver, objinit.getCartList().size());
		CartElement totalProperties= Keywords.getTotalCartElementProperties(driver);
		Assert.assertEquals(totalProperties.getKcal(), 0.00, "KCal total sum does not match");
		Assert.assertEquals(totalProperties.getProtein(), 0.00, "Protein total sum does not match");
		Assert.assertEquals(totalProperties.getFat(), 0.00, "Fat total sum does not match");
		Assert.assertEquals(totalProperties.getCarbs(), 0.00, "Carbs total sum does not match");
	}
	
	@AfterMethod()
	public void TearDown() {
		driver.quit();
	}
}
