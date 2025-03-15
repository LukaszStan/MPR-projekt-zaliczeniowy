import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class SeleniumAddDogTest {
    WebDriver driver;

    @BeforeEach
    public void setUp(){
        driver = new EdgeDriver();
    }

    @AfterEach
    public void teardown(){driver.close();}

    @Test
    public void fillAddDogForm(){
        SeleniumAddDogPage page = new SeleniumAddDogPage(driver);
        page.open();
        page.fillInNames();
        page.clickSubmitButton();

        Assertions.assertThat(driver.getCurrentUrl()).contains("index");
        Assertions.assertThat(driver.getPageSource()).contains("TestDog");
    }
}
