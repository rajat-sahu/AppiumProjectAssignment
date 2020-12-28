package KhanAcademy;

import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static java.time.Duration.ofSeconds;

import java.io.IOException;


public class KhanAcademy extends Capability{
	AndroidDriver<AndroidElement>driver;
	
	@BeforeTest
	public void bt() throws IOException, InterruptedException {

		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(5000);
		service = startserver();
		driver = capability(appActivity,appPackage,deviceName,chromedriverExecutable);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		Thread.sleep(5000);
		AndroidElement dismiss = driver.findElementByAndroidUIAutomator("new UiSelector().clickable(true)");
		if(dismiss.isDisplayed()) {
			dismiss.click();
		}
	}
	
	@Test(priority=1)
	public void signIn() throws InterruptedException {
//		driver.findElement(By.xpath("//*[@text='Dismiss']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@text='Sign in']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@text= 'Continue with Google']")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("com.google.android.gms:id/container")).click();

	}
	
	@Test(dependsOnMethods = {"signIn"},enabled=false,priority=3) 
	public void join_class() {
		try {
			Thread.sleep(4000);
			driver.findElementByAndroidUIAutomator("text(\"Join class\")").click();
			Thread.sleep(2000);
			driver.findElement(MobileBy.AccessibilityId("e.g. ABC123 or teacher@example.com")).sendKeys("life@gmail.com");
			Thread.sleep(1000);
			driver.hideKeyboard();
			Thread.sleep(2000);
			try {
				driver.pressKey(new KeyEvent(AndroidKey.ENTER));
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				System.out.println("Unable to click on Add button)");
			}
			Thread.sleep(3000);
			AndroidElement teacherAdded = driver.findElement(MobileBy.id("android:id/alertTitle"));
			if(teacherAdded.isDisplayed()) {
				System.out.println("Teacher has been successfully added to join the class");
				driver.switchTo().alert().accept();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
//			service.stop();
		}
		
	}
	
	@Test(dependsOnMethods = {"signIn"},enabled=false,priority=5)
	public void editSettings() {
		try {
			Thread.sleep(2000);
			driver.findElement(MobileBy.AccessibilityId("Settings")).click();
			Thread.sleep(2000);
			driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Licenses\"))").click();
			Thread.sleep(2000);
			driver.navigate().back();
			Thread.sleep(2000);
			driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"About the team\"))").click();
//			driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Terms of service\"))").click();
			Thread.sleep(2000);
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			/*
			try {
				Set<String> contextNames =  driver.getContextHandles();
				for(String contextName : contextNames){
					System.out.println(contextName);
				}
				try {
					driver.context("WEBVIEW_chrome");
					System.out.println("Successfully switched to web view");
				}
				catch(Exception e){
					e.printStackTrace();
					System.out.println("Unable to switch to web view");
				}
				Thread.sleep(3000);
				boolean webElementIsDisplayed = driver.findElement(By.xpath("//a[@aria-label='khan Academy']")).isDisplayed();
				if(webElementIsDisplayed) {
					System.out.println("Successfully Switched to web view");
					Thread.sleep(10000);
//					driver.pressKey(new KeyEvent(AndroidKey.BACK));
					
				}
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Some Exception is there");
				Thread.sleep(1000);
				driver.navigate().back();
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
			}
			Thread.sleep(2000);
			try {
				driver.context("NATIVE_APP");
				Thread.sleep(2000);
				driver.context("NATIVE_APP");
				System.out.println("Successfully Switched to native View");
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("Successfully Switched to native View");
			}
			*/
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(dependsOnMethods={"signIn"},enabled=false,priority=7)
	public void manage_teachers() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(MobileBy.AccessibilityId("Settings")).click();
		Thread.sleep(2000);
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Manage teachers\"))").click();
		driver.findElements(MobileBy.AccessibilityId("Delete")).get(0).click();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		Thread.sleep(1000);
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
	}
	
	
	
	@Test(priority=10, enabled=true)
	public void selectMyCourse() throws InterruptedException {
		Thread.sleep(8000);
		driver.findElementsByAndroidUIAutomator("new UiScrollable(new UiSelector().className(\"android.view.ViewGroup\")).scrollIntoView(textMatches(\"My courses\"))");
//		Thread.sleep(8000);
//		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Select language\"))").click();
		driver.findElement(By.xpath("//*[@text='Edit']")).click();
		Thread.sleep(6000);
		driver.findElements(By.className("android.widget.Button")).get(5).click();
		Thread.sleep(2000);
		driver.findElements(MobileBy.className("android.view.ViewGroup")).get(4).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@text='Done']")).click();
		System.out.println(driver.findElements(By.className("android.widget.Button")).size());
//		driver.findElementsByAndroidUIAutomator("new UiScrollable(new UiSelector().className(\"android.view.ViewGroup\")).scrollIntoView(textMatches(\"My courses\"))");
//		int count=driver.findElements(By.className("android.view.ViewGroup")).size();
////		System.out.println(count);
//		//Want to click on the element which I wanted
//		for(int i=0; i<count; i++) {
//			String courseName = driver.findElements(By.className("android.widget.TextView")).get(i).getText();
//			if(courseName.contains("Class 5 math")) {
//				System.out.println(courseName);
////				driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(0).click();
//				driver.findElements(By.className("android.widget.Button")).get(i).click();
//				break;
//			}
//		}
		System.out.println("Test case executed");
	}
	
	@Test(enabled=false)
	public void testcase1() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Passing the data to the name with Id and sendkeys
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Arpita");
		//Select the radio button which is female by xpath
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		//work with drop down
		//I will click on the value which is by default
		driver.findElement(By.id("android:id/text1")).click();
		//then scroll and select Australia
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"))").click();
		//I want to verify(assert) to check the country is selected
		String country = driver.findElement(By.xpath("//*[@text='Australia']")).getText();
		String Expected = "Australia";
		Assert.assertEquals(country, Expected);
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
	}
	
	@Test(enabled=false)
	public void testcase2() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Passing the data to the name with Id and sendkeys
//		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Arpita");
		//Select the radio button which is female by xpath
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		//work with drop down
		//I will click on the value which is by default
		driver.findElement(By.id("android:id/text1")).click();
		//then scroll and select Australia
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"))").click();
		//I want to verify(assert) to check the country is selected
		String country = driver.findElement(By.xpath("//*[@text='Australia']")).getText();
		String Expected = "Australia";
		Assert.assertEquals(country, Expected);
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		//if you want to read the error message or intract with the error popups  the only way to intact is to use className
		//every error message is called toast message
		String errormsg = driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
		System.out.println(errormsg);
		String expected ="Please enter your name";
		Assert.assertEquals(errormsg, expected);
	}
	
	@Test(enabled=false)
	public void testcase3() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Passing the data to the name with Id and sendkeys
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Arpita");
		//Select the radio button which is female by xpath
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		//work with drop down
		//I will click on the value which is by default
		driver.findElement(By.id("android:id/text1")).click();
		//then scroll and select Australia
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"))").click();
		//I want to verify(assert) to check the country is selected
		String country = driver.findElement(By.xpath("//*[@text='Australia']")).getText();
		String Expected = "Australia";
		Assert.assertEquals(country, Expected);
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		//Find out how many products are there //select jordan 6 rings
		driver.findElementsByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView(textMatches(\"Jordan 6 Rings\"))");
		int count=driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
		System.out.println(count);
		//Want to click on the element which I wanted
		for(int i=0; i<count; i++) {
			String productName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
			if(productName.equals("Air Jordan 9 Retro")) {
				System.out.println(productName);
//				driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(0).click();
				driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(i).click();
				break;
			}
		}
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		Thread.sleep(3000);
		String checkoutname = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
		System.out.println(checkoutname);
//		Thread.sleep(3000);
		String expectedname = "Air Jordan 9 Retro";
		Thread.sleep(3000);
		Assert.assertEquals(expectedname, checkoutname);
	}
	
	@Test(enabled=false)
	public void testcase4() throws InterruptedException, IOException {
//		service = startserver();
//		driver = capability(appActivity,appPackage,deviceName,chromedriverExecutable);
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Passing the data to the name with Id and sendkeys
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Arpita");
		//Select the radio button which is female by xpath
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		//work with drop down
		//I will click on the value which is by default
		driver.findElement(By.id("android:id/text1")).click();
		//then scroll and select Australia
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"))").click();
		//I want to verify(assert) to check the country is selected
		String country = driver.findElement(By.xpath("//*[@text='Australia']")).getText();
		String Expected = "Australia";
		Assert.assertEquals(country, Expected);
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		//Not to scroll but to click on the first two add to cart button
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		Thread.sleep(3000);
		String amount1 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(0).getText();
		amount1 = amount1.substring(1);
		double amount1Value = Double.parseDouble(amount1);
		System.out.println(amount1Value);
		
		String amount2 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(1).getText();
		amount2 = amount2.substring(1);
		double amount2Value = Double.parseDouble(amount2);
		System.out.println(amount2Value);
		
		double TotalAmount = amount1Value + amount2Value;
		System.out.println(TotalAmount);
		String finalAmount = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
		finalAmount = finalAmount.substring(1);
		double finalTotal = Double.parseDouble(finalAmount);
//		Thread.sleep(3000);
		
//		Assert.assertEquals(finalTotal, TotalAmount);
		//checkbox and I want to tap on the checkbox
		//Longpress on please read terms and condition
		//Pop-up where I have to click on ok button
		//Then  I have to click on visit to website to complete the purchase
		AndroidElement checkbox = driver.findElement(By.className("android.widget.CheckBox"));
		TouchAction t = new TouchAction(driver);
		t.tap(tapOptions().withElement(element(checkbox))).perform();
		AndroidElement terms = driver.findElement(By.xpath("//*[@text='Please read our terms of conditions']"));
		t.longPress(longPressOptions().withElement(element(terms)).withDuration(ofSeconds(3))).release().perform();
		System.out.println(driver.findElement(By.id("android:id/message")).getText());
		driver.findElement(By.xpath("//*[@text='CLOSE']")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
		Thread.sleep(8000);
		//to shift from Native  app to web app in hybrid application
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
		    System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
		}
		driver.context("WEBVIEW_com.androidsample.generalstore");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys("IBM");
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys(Keys.RETURN);
		Thread.sleep(5000);
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		Thread.sleep(5000);
		driver.context("NATIVE_APP");
//		service.stop();
	}
	@AfterTest
	public void closeConnection() {
		service.stop();
	}
}
