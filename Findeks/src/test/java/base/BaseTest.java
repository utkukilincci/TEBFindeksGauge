package base;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import helper.ElementHelper;
import helper.StoreHelper;
import model.ElementInfo;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class BaseTest {
    public static WebDriver driver;
    static String oldTab;
    static ArrayList<String> newTab;

    @BeforeScenario
    public void setup(){
        System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.teb.com.tr/");
        oldTab = driver.getWindowHandle();
    }

    public WebElement findElement(String key){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    public List<WebElement> findElements(String key){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        return driver.findElements(infoParam);
    }

    public void clickElement(String key){
        findElement(key).click();
    }

    public void hoverElement(String key) {
        Actions action = new Actions(driver);
        action.moveToElement(findElement(key)).build().perform();
    }

    public void assertElementText(String key, String expectedText){
        String actualText = findElement(key).getText();
        Assert.assertEquals(expectedText, actualText);
    }

    @AfterScenario
    public void tearDown(){
        driver.quit();
    }

    public static void setNewTab(ArrayList<String> newTab) {
        BaseTest.newTab = newTab;
    }

    public static ArrayList<String> getNewTab() {
        return newTab;
    }

    public static String getOldTab() {
        return oldTab;
    }
}