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
		System.out.println("Test case executed");
	}

	@AfterTest
	public void closeConnection() {
		service.stop();
	}
}
