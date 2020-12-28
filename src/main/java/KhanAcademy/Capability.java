package KhanAcademy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class Capability {

	public static String appPackage;
	public static String appActivity;
	public static String deviceName;
	public static String chromedriverExecutable;
	public AppiumDriverLocalService service;

	// Creating this method to start my appium through nodejs//this is given by
	// appium server
	public AppiumDriverLocalService startserver() {
		boolean flag = checkifserverisRunning(4723);
		if(!flag) {
			//service = AppiumDriverLocalService.buildDefaultService();
			service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
					.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
					.withAppiumJS(new File("C:\\Users\\ibmadmin\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
					.withIPAddress("0.0.0.0").usingPort(4723));
			service.start();
		}

		return service;
	}

	public static boolean checkifserverisRunning(int port) {
		boolean isserverRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();

		} catch (Exception e) {
			isserverRunning = true;
		} finally {
			serverSocket = null;
		}
		return isserverRunning;
	}

	public static void startEmulator() throws IOException, InterruptedException {
		Runtime.getRuntime().exec(System.getProperty("user.dir") + "//src//test//resources//Emulator.bat");
		Thread.sleep(8000);
	}
	
	public static AndroidDriver<AndroidElement> capability(String appPackage, String appActivity, String deviceName,
			String chromedriverExecutable) throws IOException, InterruptedException {

		// FileReader fis = new
		// FileReader(System.getProperty("C:/Users/ibmadmin/workspace/AppiumFramework/src/main/java/global.properties"));
		FileReader fis = new FileReader(System.getProperty("user.dir") + "//src//test//resources//global.properties");
		Properties pro = new Properties();
		pro.load(fis);
		appPackage = pro.getProperty("appPackage");
		appActivity = pro.getProperty("appActivity");
		deviceName = pro.getProperty("deviceName");
		if(deviceName.contains("Rajat")){
			startEmulator();
		}
		chromedriverExecutable = pro.getProperty("chromedriverExecutable");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromedriverExecutable);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"),
				cap);
		return driver;
	}

}
