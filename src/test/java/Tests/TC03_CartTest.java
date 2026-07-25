package Tests;

import Listeners.IInvoledMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvoledMethodListenerClass.class, ITestResultListenerClass.class})
public class TC03_CartTest {
    @BeforeMethod
    public void setUp() throws IOException {
        setupDriver(DataUtils.getDataFromProperties("environments", "Browser"));
        LogsUtils.info("Starting the browser and navigating to the base URL");
        getDriver().get(DataUtils.getDataFromProperties("environments", "BASE_URL"));
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void comparingPricesTC() throws IOException {
        LogsUtils.info("Entering valid credentials and clicking on the login button");
        String totalPrice = new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getDataFromJson("validLogin", "username"))
                .enterPassword(DataUtils.getDataFromJson("validLogin", "password"))
                .clickOnLoginButton()
                .addRandomProducts(3, 6)
                .getTotalPriceOfSelectedProducts();
        new P02_LandingPage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(new P03_CartPage(getDriver()).comparingPrices(totalPrice));
    }


    @AfterMethod
    public void quit() {
        quitDriver();

    }
}
