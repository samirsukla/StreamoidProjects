package com.services.utility;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckWidgetPresentStatus {
	
	
	public void checkStatusAndTakeScreenshot(WebDriver driver,String folderName,String mainCategoryName, String 
			prodCategoryName,WebElement e) throws IOException{
		takeScreenShot scrshot;
		String status ="";
		if(driver.findElement(By.className("allen_solly_vertical_container")).isDisplayed()) {
			status = "passed";
			scrshot = new takeScreenShot();
			scrshot.captureScreenShot(driver,folderName,mainCategoryName,prodCategoryName,status);
		}else {
			status = "failed";
			scrshot = new takeScreenShot();
			scrshot.captureScreenShot(driver,folderName,mainCategoryName,prodCategoryName,status);
		}
	}
}