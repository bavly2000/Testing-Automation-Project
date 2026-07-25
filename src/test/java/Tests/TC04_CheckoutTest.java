package Tests;

import Listeners.IInvoledMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvoledMethodListenerClass.class, ITestResultListenerClass.class})
public class TC04_CheckoutTest {


    @BeforeMethod
    public void setUp() throws IOException {
        setupDriver(DataUtils.getDataFromProperties("environments", "Browser"));
        LogsUtils.info("Starting the browser and navigating to the base URL");
        getDriver().get(DataUtils.getDataFromProperties("environments", "BASE_URL"));
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void checkoutStepOneTC() throws IOException {
        LogsUtils.info("Entering valid credentials and clicking on the login button");
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getDataFromJson("validLogin", "username"))
                .enterPassword(DataUtils.getDataFromJson("validLogin", "password"))
                .clickOnLoginButton()
                .addRandomProducts(3, 6)
                .clickOnCartIcon()
                .clickOnCheckoutButton()
                .fillingInformationForm(DataUtils.getDataFromJson("information", "firstname"),
                        DataUtils.getDataFromJson("information", "lastname"),
                        DataUtils.getDataFromJson("information", "zipcode"))
                .clickOnContinueButton();
        Assert.assertTrue(Utility.VerifyURL(getDriver(), DataUtils.getDataFromProperties("environments", "OVERVIEW_PAGE_URL")),
                "The URL is not as expected after clicking on the continue button.");
    }


    @AfterMethod
    public void quit() {
        quitDriver();

    }
}
