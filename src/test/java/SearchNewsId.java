import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("Получение определенной новости по ID")
@Owner("Sergey Bordiyan")
public class SearchNewsId {

    @Test
    @Description("Успешное получение определенной новости по верному ID")
    public void successSearchNewsId() {
        NewsResponse createdNews = News.createNews();
        String newsId = String.valueOf(createdNews.getId());

        RestAssured.baseURI = Contains.URI;
        RestAssured.basePath = "/posts/ " + newsId;
        NewsResponse response =
        given().contentType(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .log().all()
                .extract().as(NewsResponse.class);

        Assert.assertEquals(response.getId(), Integer.parseInt(newsId));
    }

    @Description("Неуспешное получение определенной новости по неверному ID")
    @Test
    public void unsuccessSearchNewsId() {
        News.createNews();

        RestAssured.baseURI = Contains.URI;
        RestAssured.basePath = "/posts/ " + Contains.invalidId;
        given().contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(404)

                .body("statusCode", equalTo(404));
    }
}
