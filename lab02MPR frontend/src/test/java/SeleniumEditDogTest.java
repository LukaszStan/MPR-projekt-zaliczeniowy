import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class SeleniumEditDogTest {
    WebDriver driver;

    @BeforeEach
    public void setUp(){
        driver = new EdgeDriver();
    }

    @Test
    public void fillEditDogForm(){
        SeleniumEditDogPage page = new SeleniumEditDogPage(driver);
        page.open();
        page.fillInNames();
        page.clickSubmitButton();

        Assertions.assertThat(driver.getCurrentUrl()).contains("index");
        Assertions.assertThat(driver.getPageSource()).contains("EditDog");
    }
}
