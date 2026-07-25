package Tests;

import Listeners.IInvoledMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvoledMethodListenerClass.class, ITestResultListenerClass.class})
public class TC02_LandingTest {
    @BeforeMethod
    public void setUp() throws IOException {
        setupDriver(DataUtils.getDataFromProperties("environments", "Browser"));
        LogsUtils.info("Starting the browser and navigating to the base URL");
        getDriver().get(DataUtils.getDataFromProperties("environments", "BASE_URL"));
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void addingAllProductToCartTC() throws IOException {
        LogsUtils.info("Entering valid credentials and clicking on the login button");
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getDataFromJson("validLogin", "username"))
                .enterPassword(DataUtils.getDataFromJson("validLogin", "password"))
                .clickOnLoginButton()
                .addAllProductsToCart();
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void addingRandomProductsToCartTC() throws FileNotFoundException {
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getDataFromJson("validLogin", "username"))
                .enterPassword(DataUtils.getDataFromJson("validLogin", "password"))
                .clickOnLoginButton()
                .addRandomProducts(3, 6);
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void clickOnCartIcon() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getDataFromJson("validLogin", "username"))
                .enterPassword(DataUtils.getDataFromJson("validLogin", "password"))
                .clickOnLoginButton()
                .clickOnCartIcon();
        Assert.assertTrue(Utility.VerifyURL(getDriver(), DataUtils.getDataFromProperties("environments", "CART_PAGE_URL")));
    }

    @AfterMethod
    public void quit() {
        quitDriver();

    }
}
