package test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import library.SelectBrowser;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AddToCart;
import pages.CheckCartProduct;
import pages.CheckoutPage;
import pages.Registration;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class CheckoutTest { 

    WebDriver driver;

    Registration registration;
    CheckoutPage checkoutPage;
    AddToCart addToCart;
    private static ExtentHtmlReporter htmlReporter;
    private static ExtentReports extent;
    private static ExtentTest test;

    @BeforeSuite
    public void setUpReport(){
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/TestReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "Nabila.home-server.local");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "Nabila");
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("AutomationTesting Google download pictures report");
        htmlReporter.config().setReportName("Google Search and Download Pictures Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);
    }
    @BeforeTest
    public void openBrowser() throws IOException {
        driver = SelectBrowser.StartBrowser("Chrome");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
        driver.get("https://www.alexandnova.com/");
    }
    @Test(priority = 1)
    public void giftCardCheck() throws InterruptedException, IOException {
        test = extent.createTest("giftCardCheck", "Test Passed");
        addToCart = new AddToCart(driver);
        addToCart.clickSelectProduct();
        Thread.sleep(4000);
        addToCart.selectSize();
        Thread.sleep(4000);
        addToCart.selectColor();
        Thread.sleep(4000);
        checkoutPage = new CheckoutPage(driver);
        //checkoutPage.scrollDown();
        addToCart.addToCart();
        Thread.sleep(4000);
        addToCart.clickCart();
        Thread.sleep(4000);
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickCheckout();
        checkoutPage.giftCartBoxCheck();

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file,new File("src/test/resources/screenShots/image.png"));
    }
    //Verify that all the below options should be displayed : Credit Card (Visa/Master), Debit Card (Visa/MasterCard/Maestro), Paid by Paypal, Paid by shop pay, Paid by zip
    @Test(priority = 2)
    public void paymentOptions() throws InterruptedException, IOException {
        test = extent.createTest("paymentOptions", "Test Passed");
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.setSendEmail("nab3@email.com");
        checkoutPage.setSendFirstName("nabi1");
        checkoutPage.setSendLastName("azz");
        checkoutPage.setSendAddress("9056 E denver");
        checkoutPage.setSendCity("Denver");
        checkoutPage.setSendZip("80014");
        Thread.sleep(4000);
        checkoutPage.clickContinue();
        Thread.sleep(4000);
        checkoutPage.clickContinueToPay();
        Thread.sleep(4000);
        checkoutPage.assertVisaCard();

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file,new File("src/test/resources/screenShots/image.png"));
    }
    //Try completing the check-out process by leaving any mandatory field blank in the credit card payment information and shipping or billing address.
    @Test(priority = 3)
    public void completingCheckOutProcess() throws InterruptedException, IOException {
        test = extent.createTest("completingCheckOutProcess", "Test Passed");
        checkoutPage = new CheckoutPage(driver);
        Thread.sleep(20000);
        checkoutPage.switchFrameCard();
        checkoutPage.sendCreditCardNumber("3698 521476 9874");
        Thread.sleep(20000);
        checkoutPage.switchFrameName();
        checkoutPage.sendName("john fink");
        Thread.sleep(20000);
        checkoutPage.switchFrameDate();
        checkoutPage.sendExpDate("06/22");
        checkoutPage.switchFrameCode();
        checkoutPage.sendSecurityCode("222");
        driver.switchTo().parentFrame();
        checkoutPage.clickOnPayNow();
        checkoutPage.getErrorMessage();

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file,new File("src/test/resources/screenShots/image.png"));
    }
    @Test(priority = 3)
    public void emptyCheckOutProcess() throws InterruptedException, IOException {
        test = extent.createTest("completingCheckOutProcess", "Test Passed");
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.switchFrameCard();
        checkoutPage.sendCreditCardNumber("");
        Thread.sleep(20000);
        checkoutPage.switchFrameName();
        Thread.sleep(20000);
        checkoutPage.sendName("");
        checkoutPage.switchFrameDate();
        checkoutPage.sendExpDate("");
        checkoutPage.switchFrameCode();
        checkoutPage.sendSecurityCode("");
        driver.switchTo().parentFrame();
        checkoutPage.clickOnPayNow();
        checkoutPage.getErrorMessage();

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file,new File("src/test/resources/screenShots/image.png"));
    }
//*[@id="google-analytics-sandbox"]
    @AfterSuite
    public void tearDown()
    {
        extent.flush();
    }
}


