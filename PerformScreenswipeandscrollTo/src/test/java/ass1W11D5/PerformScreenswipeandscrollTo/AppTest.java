package ass1W11D5.PerformScreenswipeandscrollTo;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private AndroidDriver driver;

	@BeforeSuite
	public void setUp() throws MalformedURLException, InterruptedException {

		// Setting up desire caps using DesireCapabilities class
		// Create an object for Desired Capabilities
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

		// Set capabilities
		desiredCapabilities.setCapability("appium:app",
				"C:\\Users\\lo0ol\\Downloads\\QA_class_app_resources.zip, attachment\\QA class app resources\\ApiDemos-debug.apk");
		desiredCapabilities.setCapability("appium:deviceName", "23b9cb400c1c7ece");
		desiredCapabilities.setCapability("appium:platformName", "Android");
		desiredCapabilities.setCapability("appium:platformVersion", "10");
		desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
		desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);
		desiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
		// Java package of the Android app you want to run
		desiredCapabilities.setCapability("appium:appPackage", "io.appium.android.apis");
		// Activity name for the Android activity you want to launch from your package
		desiredCapabilities.setCapability("appium:appActivity", "io.appium.android.apis.ApiDemos");

		System.out.println("Finshed: desiredCapabilities");

		// Initialize the driver object with the URL to Appium Server and
		// passing the capabilities
		URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AndroidDriver(remoteUrl, desiredCapabilities);
		System.out.println("Finshed: driver");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	/**
	 * Test Scroll
	 */
	@Test(priority = 1)
	public void TestScroll() throws InterruptedException {
		// Perform the action on the element
		// click on "View"
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		System.out.println("Finshed: Views");
		Thread.sleep(2000);

		// scroll down to the "WebView2" element
		((JavascriptExecutor) driver).executeScript("mobile: scroll", ImmutableMap.builder().put("direction", "down")
				.put("strategy", "accessibility id").put("selector", "WebView2").put("percent", 3.0).build());
		System.out.println("Finshed: scroll Down");
		Thread.sleep(2000);

		// scroll up to the "Gallery" element
		((JavascriptExecutor) driver).executeScript("mobile: scroll", ImmutableMap.builder().put("direction", "up")
				.put("strategy", "accessibility id").put("selector", "Gallery").put("percent", 3.0).build());
		System.out.println("Finshed: scroll Up");
		Thread.sleep(2000);

		// click on "Gallery" using Actions
		WebElement elementGallery = driver.findElement(AppiumBy.accessibilityId("Gallery"));
		Actions action = new Actions(driver);
		action.moveToElement(elementGallery).click().build().perform();
		System.out.println("Finshed: Gallery action");
		Thread.sleep(2000);

	}

	/**
	 * Test Swipe
	 */
	@Test(priority = 2, dependsOnMethods = { "TestScroll" })
	public void TestSwipe() throws InterruptedException {
		// click on "Photos" using Actions
		WebElement elementPhotos = driver.findElement(AppiumBy.accessibilityId("1. Photos"));
		Actions action = new Actions(driver);
		action.moveToElement(elementPhotos).click().build().perform();
		System.out.println("Finshed: Photos action");
		Thread.sleep(5000);

		// take image xpath
		RemoteWebElement image = (RemoteWebElement) driver.findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.Gallery/android.widget.ImageView[3]"));
		System.out.println("Finshed: image");

		// swipe right to the photo number 2
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture",
				ImmutableMap.builder().put("direction", "right").put("elementId", image.getId()).put("maxSwipes", 10)
						.put("percent", 0.0).build());
		System.out.println("Finshed: swipe right");
		Thread.sleep(2000);

		// click on image
		image.click();
		System.out.println("Finshed: click on image");
		Thread.sleep(2000);

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		System.out.println("Finshed: quit");
	}
}
