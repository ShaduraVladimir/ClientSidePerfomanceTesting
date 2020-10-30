package pages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import util.Constants;

import static util.WebDriverHolder.getDriver;

public class HomePage extends AbstractPage {

    public HomePage() {
        super();
    }

    public HomePage openMainPage() {
        wait.until(ExpectedConditions.elementToBeClickable(computerCategory));
        Assert.assertEquals(getDriver().getCurrentUrl(),Constants.LOGIN_URL);
        perfNavigationTiming.writeToJson("MainPage");
        return this;
    }
}
