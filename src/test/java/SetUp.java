import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.ComputersPage;
import pages.DesktopsPage;
import pages.HomePage;
import pages.ProductPage;

import static util.Constants.*;
import static util.WebDriverHolder.getDriver;
import static util.WebDriverHolder.setDriver;

public class SetUp {

    private WebDriver driver;

    @BeforeClass
    protected void setUpBrowser() {
        WebDriverManager.chromedriver().setup();
        SCENARIO_NAME = this.getClass().getSimpleName();
        setDriver(BROWSER_FACTORY.startBrowser(BROWSER_NAME, driver));
        getDriver().get(LOGIN_URL);
        deleteJsonFiles();
    }

    @BeforeMethod
    protected void setUp() {
        HOME_PAGE = new HomePage();
        COMPUTERS_PAGE = new ComputersPage();
        DESKTOPS_PAGE = new DesktopsPage();
        PRODUCT_PAGE = new ProductPage();
    }

    @AfterClass
    protected void teardown() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }
}
