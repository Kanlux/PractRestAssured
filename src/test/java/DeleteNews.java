import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@Epic("Удаление новости по ID")
@Owner("Sergey Bordiyan")
public class DeleteNews {

    @Description("Успешное удаление новости по верному ID")
    @Test
    public void successRemoveNewsByCorrectId() {
        RegisterRequest login = Methods.createUser();
        NewsResponse createdNews = Methods.createNews(login.getAccessToken());
        String newsId = String.valueOf(createdNews.getId());

        RestAssured.basePath = "/posts/{id}";
        given()
                .spec(Specification.requestSpecJson())
                .pathParam("id", newsId)
                .auth().oauth2(login.getAccessToken())
                .when()
                .delete()
                .then()
                .spec(Specification.responseSpec200());
    }

    @Description("Не успешное удаление новости по неверному ID")
    @Test
    public void unsuccessRemoveNewsByIncorrectId() {
        RegisterRequest login = Methods.createUser();
        RestAssured.basePath = "/posts/{id}";
        given()
                .spec(Specification.requestSpecJson())
                .auth().oauth2(login.getAccessToken())
                .pathParam("id", Constants.invalidId)
                .when()
                .delete()
                .then()
                .statusCode(404)
                .log().all()
                .body("message", equalTo("Not Found"));
    }
}
