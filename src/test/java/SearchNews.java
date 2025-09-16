import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

@Epic("Получение всех новостей")
@Owner("Sergey Bordiyan")
public class SearchNews {

    @Description("Успешное получение всех новостей")
    @Test
    public void successSearch() {
        NewsResponse response = new NewsResponse();
        ;
        RestAssured.basePath = "/posts";
        given().spec(Specification.requestSpecJson())
                .queryParam("search", "Hello")
                .when().get()
                .then().spec(Specification.responseSpec200())
                .extract().as(NewsResponse.class);

        String text = response.getText();

        Assert.assertEquals(text, response.getText());
    }
}
