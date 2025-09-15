import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@Epic("Удаление новости по ID")
@Owner("Sergey Bordiyan")
public class RemoveNews {

    @Description("Успешное удаление новости по верному ID")
    @Test
    public void successRemoveNews() {
        NewsResponse createdNews = News.createNews();
        String newsId = String.valueOf(createdNews.getId());

        RestAssured.baseURI = Contains.URI;
        RestAssured.basePath = "/posts/" + newsId;
        given()
                .header("Authorization", "Bearer " + Contains.tokenOfTest)
                .when()
                .delete()
                .then()
                .statusCode(200)
                .log().all();
    }

    @Description("Не успешное удаление новости по неверному ID")
    @Test
    public void unsuccessRemoveNews() {
        RestAssured.baseURI = Contains.URI;
        RestAssured.basePath = "/posts/999999";
        given()
                .header("Authorization", "Bearer " + Contains.tokenOfTest)
                .when()
                .delete()
                .then()
                .statusCode(404)
                .log().all()

                .body("message", equalTo("Not Found"));
    }
}
