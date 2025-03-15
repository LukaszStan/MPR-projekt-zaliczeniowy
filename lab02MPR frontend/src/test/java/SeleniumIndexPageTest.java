import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.List;

public class SeleniumIndexPageTest {
    WebDriver driver;

    @BeforeEach
    public void setUp(){
        driver = new EdgeDriver();
    }

    @Test
    public void verifyIndexPageContent(){
        driver.get("http://localhost:8081/index");

        WebElement table = driver.findElement(By.tagName("table"));
        Assertions.assertThat(table).isNotNull();

        List<WebElement> rows = table.findElements(By.tagName("tr"));
        Assertions.assertThat(rows.size()).isGreaterThan(1);

        WebElement firstRow = rows.get(0);
        List<WebElement> headers = firstRow.findElements(By.tagName("th"));
        Assertions.assertThat(headers).hasSize(3);

        Assertions.assertThat(headers.get(0).getText()).isEqualTo("ID");
        Assertions.assertThat(headers.get(1).getText()).isEqualTo("Name");
        Assertions.assertThat(headers.get(2).getText()).isEqualTo("Breed");
    }
}
