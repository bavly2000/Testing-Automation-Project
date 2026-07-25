package Tests;

import Listeners.IInvoledMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvoledMethodListenerClass.class, ITestResultListenerClass.class})
public class TC01_LoginTest {
    @BeforeSuite
    public void clearAllureResults() throws Exception {
        FileUtils.deleteDirectory(new File("target/allure-results"));
        System.out.println("✅ Old Allure results deleted. Fresh run started.");
    }

    @BeforeMethod
    public void setUp() throws IOException {
        setupDriver(DataUtils.getDataFromProperties("environments", "Browser"));
        LogsUtils.info("Starting the browser and navigating to the base URL");
        getDriver().get(DataUtils.getDataFromProperties("environments", "BASE_URL"));
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void validLoginTC() throws IOException {
        LogsUtils.info("Entering valid credentials and clicking on the login button");
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getDataFromJson("validLogin", "username"))
                .enterPassword(DataUtils.getDataFromJson("validLogin", "password"))
                .clickOnLoginButton();
        Assert.assertEquals(getDriver().getCurrentUrl(), DataUtils.getDataFromProperties("environments", "HOME_URL"));
    }


    @AfterMethod
    public void quit() {
        quitDriver();

    }
}
