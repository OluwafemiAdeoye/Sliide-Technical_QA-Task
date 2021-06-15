package com.oluwafemi;

import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import com.microsoft.appcenter.appium.Factory;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class sliideQA {
    private static EnhancedAndroidDriver<MobileElement> driver;

    @BeforeClass
    public void SetUp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("deviceName", "Device");
//        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("fullReset", "false");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("appPackage", "com.test.news");
        capabilities.setCapability("appActivity", "com.test.news.features.login.presentation.LoginActivity");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
//        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
//        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        URL url = new URL("http://127.0.0.1:4723");
        driver = Factory.createAndroidDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Rule
    public TestWatcher watcher = Factory.createWatcher();
    @Test(priority = 1)
    public void logIn() {
        MobileElement userName = (MobileElement) driver.findElement(new By.ById("com.test.news:id/editTextUserName"));
        userName.sendKeys("user");

        MobileElement password = (MobileElement) driver.findElement(new MobileBy.ById("com.test.news:id/editTextPassword"));
        password.sendKeys("password");

        MobileElement loginButton = (MobileElement) driver.findElement(new MobileBy.ById("com.test.news:id/buttonLogin"));
        loginButton.click();
        try {
            MobileElement newsClick = (MobileElement) driver.findElements(new By.ByClassName("androidx.recyclerview.widget.RecyclerView")).get(1);
            newsClick.click();

        }
        catch(Exception e) {
            String noNetworkText= "Failed to load news";
            String currentText = driver.findElementByXPath("//*[@text= 'Failed to load news']").getText();
            Assert.assertEquals(noNetworkText, currentText);
        }

    }

    @AfterTest
    public void TearDown() {
        driver.quit();
    }
}
