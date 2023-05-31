package demo.resources;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;


public   class Keywords {

    public static void  fillCart(WebDriver driver,List<String> items,List<CartElement> cartList) {
        int listSize= items.size();

        for (int i=0;i<listSize;i++) {
            driver.findElement(By.className("prompt")).sendKeys(items.get(i));
            driver.findElement(By.xpath("//*[@id='food-search']//tbody//tr[1]")).click();

            float kcalText=Float.parseFloat(driver.findElement(By.xpath("//*[@id='food-search']//tbody//tr[1]//td[2]")).getText());
            float proteinText=Float.parseFloat(driver.findElement(By.xpath("//*[@id='food-search']//tbody//tr[1]//td[3]")).getText());
            float fatText=Float.parseFloat(driver.findElement(By.xpath("//*[@id='food-search']//tbody//tr[1]//td[4]")).getText());
            float carbsText=Float.parseFloat(driver.findElement(By.xpath("//*[@id='food-search']//tbody//tr[1]//td[5]")).getText());

            CartElement cartElem = new CartElement(kcalText,proteinText,fatText,carbsText);

            cartList.add(cartElem);
            driver.findElement(By.xpath("//*[@class='remove icon']")).click(); 
        }
    }


    public static CartElement getTotalCartElementProperties(WebDriver driver) {
        float kcalText=Float.parseFloat(driver.findElement(By.id("total-kcal")).getText());
        float proteinText=Float.parseFloat(driver.findElement(By.id("total-protein_g")).getText());
        float fatText=Float.parseFloat(driver.findElement(By.id("total-fat_g")).getText());		
        float carbsText=Float.parseFloat(driver.findElement(By.id("total-carbohydrate_g")).getText());		
        CartElement cartElem = new CartElement(kcalText,proteinText,fatText,carbsText);

        return cartElem;
    }

    public static CartElement getActualTotalCartElementProperties(List<CartElement> cartList ) {
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

        CartElement cartElem = new CartElement(sumKcal,sumProtein,sumFat,sumCarbs);
        return cartElem;
    }

    public static void emptyCart(WebDriver driver,int listSize) {
        String xpathString;

        for (int i=0;i<listSize;i++) {
            xpathString="//*[text()='Selected foods']/ancestor::thead/following-sibling::tbody/tr[1]";
            driver.findElement(By.xpath(xpathString)).click();
        }
    }

    public static String takeScreenShot(WebDriver driver,String testCaseName) throws IOException {
        File screenShot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File file= new File(System.getProperty("user.dir")+"/reports/"+testCaseName+".png");
        FileUtils.copyFile(screenShot, file);
        return System.getProperty("user.dir")+"/reports/"+testCaseName+".png";
    }
}
