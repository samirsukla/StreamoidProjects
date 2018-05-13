package com.services.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.services.utility.CheckWidgetPresentStatus;
import com.services.utility.GUICheckForSimilarProducts;
import com.services.utility.InitialSetup;
import com.services.utility.RestAPICheckForSimilarProducts;
import com.services.utility.clickonFirstProduct;

public class TestPlanetFashionInfo {
	
	
	public WebDriver driver;
	static Properties props;
	static FileInputStream finput;
	static String className;
	static String folderName;
	static String mainCategoryName;
	static WebElement widgetElement;
	static String status;
	static String product_id;
	static WebElement similar_widget ;
	
	static clickonFirstProduct clickfirst;
	static CheckWidgetPresentStatus checkStatus;
	static InitialSetup initSet;
	static RestAPICheckForSimilarProducts checkProducts;
	static GUICheckForSimilarProducts checkGUI;
	
	
	@BeforeClass
	public void setUp() throws IOException {
		
		initSet = new InitialSetup();
		driver = initSet.initialSetup();
		clickfirst = new clickonFirstProduct();
		className = this.getClass().getSimpleName();
		folderName = className.replace("Test", " ").replace("Info", " ").trim();
		checkStatus = new CheckWidgetPresentStatus();
		checkProducts = new RestAPICheckForSimilarProducts();
		checkGUI = new GUICheckForSimilarProducts();
		
	}
  @Test
  public void testPlanetFashion() throws Exception {
	  
	    String url = initSet.getUrl("Planet_Fashion");
		driver.get(url);
	  
		for(int i=2;i<=4; i++) {
		
		mainCategoryName = driver.findElement(By.xpath(".//*[@id='nav-bar']/li["+i+"]/a")).getText();
		int j=1;
		
		List<WebElement> sub_categ_div = driver.findElements(By.xpath(".//*[@id='nav-bar']/li["+i+"]/div/div/div/div"));
		
		for(int x=1; x<=sub_categ_div.size(); x++) {
			WebElement main_category = driver.findElement(By.xpath(".//*[@id='nav-bar']/li["+i+"]/a"));
			clickfirst.moveToElement_only(main_category, driver);
			Thread.sleep(2000);
			
			try {
				while(driver.findElement(By.xpath(".//*[@id='nav-bar']/li["+i+"]/div/div/div/div["+x+"]/div/ul/li/a["+j+"]/span")).isDisplayed()) {
					
					WebElement main_category1 = driver.findElement(By.xpath(".//*[@id='nav-bar']/li["+i+"]/a"));
					WebElement product_category = driver.findElement(By.xpath(".//*[@id='nav-bar']/li["+i+"]/div/div/div/div["+x+"]/div/ul/li/a["+j+"]/span"));
					String prodCategoryName = clickfirst.getCategoryName(product_category);
					
					clickfirst.moveToElementandClick(main_category1, product_category, driver);
					
					WebDriverWait wait = new WebDriverWait(driver,10);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[1]/div[2]/div/div[6]/div/div/div[1]/div/div[1]/div[1]/div/div/ul/li[2]/a/div/img")));
					
					WebElement first_element_link = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[2]/div/div[6]/div/div/div[1]/div"));
					product_id = first_element_link.getAttribute("id").replaceAll("product_wrap_", "");
					
					clickfirst.clickOnPeopleProduct(driver);
					
					Thread.sleep(3000);
					
					if(driver.findElement(By.className("planet_fashion_vertical_container")).isDisplayed())
					{
						status = "passed";
						List<String> similar_product_id = checkProducts.similarProducts(product_id,"planet_fashion");
						boolean isDisplaying = checkGUI.getIdsFromGUIforPlanetFashion(similar_product_id,driver);
						
						checkStatus.checkStatusAndTakeScreenshot(driver, folderName, mainCategoryName, prodCategoryName, status,
								product_id,isDisplaying);
					}
					else
					{
						status = "failed";
						boolean isDisplaying = false;
						
						checkStatus.checkStatusAndTakeScreenshot(driver, folderName, mainCategoryName, prodCategoryName, status,
								product_id,isDisplaying);
						
					}
					
					j++;

					driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div/div[1]/div/a/img")).click();
					WebElement main_category2 = driver.findElement(By.xpath(".//*[@id='nav-bar']/li["+i+"]/a"));
					clickfirst.moveToElement_only(main_category2, driver);
					
					}
					
				}
			catch(Exception e) {
				j=1;
				driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div/div[1]/div/a/img")).click();
				WebElement main_category2 = driver.findElement(By.xpath(".//*[@id='nav-bar']/li["+i+"]/a"));
				clickfirst.moveToElement_only(main_category2, driver);
				
			}
			}
			
		
		}
	  
		}
  
  @AfterClass
  public void tearDown() {
	  
	  driver.close();
  }
}
