import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
        RestAssured.baseURI = Contains.URI
        ;
        RestAssured.basePath = "/posts";
        given().contentType(ContentType.JSON)
                .queryParam("search", "Hello")
                .when().get()
                .then().statusCode(200).log().all()
                .extract().as(NewsResponse.class);

        String text = response.getText();

        Assert.assertEquals(text, response.getText());

        System.out.println(response);
    }
}
