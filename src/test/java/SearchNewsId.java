import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("Получение определенной новости по ID")
@Owner("Sergey Bordiyan")
public class SearchNewsId {

    @BeforeAll
    static void login() {
        Methods.createUser();
    }

    @Test
    @Description("Успешное получение определенной новости по верному ID")
    public void successSearchNewsId() {
        NewsResponse createdNews = Methods.createNews();
        String newsId = String.valueOf(createdNews.getId());

        RestAssured.basePath = "/posts/{id}";
        NewsResponse response =
        given().spec(Specification.requestSpecJson())
                .pathParam("id", newsId)
                .get()
                .then()
                .spec(Specification.responseSpec200())
                .extract().as(NewsResponse.class);

        Assert.assertEquals(response.getId(), Integer.parseInt(newsId));
    }

    @Description("Неуспешное получение определенной новости по неверному ID")
    @Test
    public void unsuccessSearchNewsId() {
        Methods.createNews();

        RestAssured.baseURI = Constants.URI;
        RestAssured.basePath = "/posts/{id}";
        given().contentType(ContentType.JSON)
                .pathParam("id", Constants.invalidId)
                .when()
                .get()
                .then()
                .statusCode(404)
                .body("statusCode", equalTo(404));
    }
}
