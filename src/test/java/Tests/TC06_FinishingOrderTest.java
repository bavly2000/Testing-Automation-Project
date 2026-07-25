package Tests;

import Listeners.IInvoledMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P06_FinishingOrderPage;
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
public class TC06_FinishingOrderTest {
    @BeforeMethod
    public void setUp() throws IOException {
        setupDriver(DataUtils.getDataFromProperties("environments", "Browser"));
        LogsUtils.info("Starting the browser and navigating to the base URL");
        getDriver().get(DataUtils.getDataFromProperties("environments", "BASE_URL"));
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void finishOrderTC() throws IOException {
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
                .clickOnContinueButton()
                .clickOnFinishButton();
        Assert.assertTrue(new P06_FinishingOrderPage(getDriver()).checkVisibilityOfThanksMessage());
    }


    @AfterMethod
    public void quit() {
        quitDriver();

    }
}
