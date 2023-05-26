import java.text.DecimalFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public   class keywords {

	public static void  fillCart(WebDriver driver,List<String> items,List<cartElement> cartList) {
		
		int listSize= items.size();

		for (int i=0;i<listSize;i++) {
			 
			 driver.findElement(By.className("prompt")).sendKeys(items.get(i));
			 driver.findElement(By.xpath("//*[@id='food-search']//tbody//tr[1]")).click();
			 
			 float kcalText=Float.parseFloat(driver.findElement(By.xpath("//*[@id='food-search']//tbody//tr[1]//td[2]")).getText());
			 float proteinText=Float.parseFloat(driver.findElement(By.xpath("//*[@id='food-search']//tbody//tr[1]//td[3]")).getText());
			 float fatText=Float.parseFloat(driver.findElement(By.xpath("//*[@id='food-search']//tbody//tr[1]//td[4]")).getText());
			 float carbsText=Float.parseFloat(driver.findElement(By.xpath("//*[@id='food-search']//tbody//tr[1]//td[5]")).getText());

			 cartElement cartElem = new cartElement(kcalText,proteinText,fatText,carbsText);

			 cartList.add(cartElem);
			 
			 driver.findElement(By.xpath("//*[@class='remove icon']")).click(); 
			 
		 }
	}
	
	
	public static cartElement getTotalCartElementProperties(WebDriver driver) {
		
		
		
		float kcalText=Float.parseFloat(driver.findElement(By.id("total-kcal")).getText());
		float proteinText=Float.parseFloat(driver.findElement(By.id("total-protein_g")).getText());
		float fatText=Float.parseFloat(driver.findElement(By.id("total-fat_g")).getText());		
		float carbsText=Float.parseFloat(driver.findElement(By.id("total-carbohydrate_g")).getText());		
		cartElement cartElem = new cartElement(kcalText,proteinText,fatText,carbsText);
		
		return cartElem;
	}
	
	public static cartElement getActualTotalCartElementProperties(List<cartElement> cartList ) {
		
		DecimalFormat df = new DecimalFormat("#.##");
		float sumKcal=0;
		float sumProtein=0;
		float sumFat=0;
		float sumCarbs=0;

		
		for (int i=0;i<cartList.size();i++) {
			
			sumKcal=cartList.get(i).getKcal()+sumKcal;
			sumProtein=cartList.get(i).getProtein()+sumProtein;
			sumFat=cartList.get(i).getFat()+sumFat;
			sumCarbs=cartList.get(i).getCarbs()+sumCarbs;
			
		}
		
		String formattedSumKcal = df.format(sumKcal);
		String formattedSumProtein = df.format(sumProtein);
		String formattedSumFat = df.format(sumFat);
		String formattedSumCarbs = df.format(sumCarbs);
        
        
        sumKcal = Float.parseFloat(formattedSumKcal);
        sumProtein = Float.parseFloat(formattedSumProtein);
        sumFat = Float.parseFloat(formattedSumFat);
        sumCarbs = Float.parseFloat(formattedSumCarbs);
		
		cartElement cartElem = new cartElement(sumKcal,sumProtein,sumFat,sumCarbs);
		return cartElem;
		
	}
	
	public static void emptyCart(WebDriver driver,int listSize) {
		String xpathString;
		
		for (int i=0;i<listSize;i++) {
			
			xpathString="//*[text()='Selected foods']/ancestor::thead/following-sibling::tbody/tr[1]";
			driver.findElement(By.xpath(xpathString)).click();
		}
		
		
	}
	

}
