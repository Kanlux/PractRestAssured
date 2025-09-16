import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Удаление комментария")
@Owner("Sergey Bordiyan")
public class DeleteComment {

    @Description("Успешное удаление комментария по ID")
    @Test
    public void givenLogin_WhenDeleteComment_ThenSuccess() {
        RegisterRequest login = Methods.createUser();
        NewsResponse newsId = Methods.createNews(login.getAccessToken());
        CommentResponse createdComm = Methods.createComm(login.getAccessToken(), newsId.getId());
        RestAssured.basePath = "/comments/{id}";
        String response =
                given()
                        .spec(Specification.requestSpecJson())
                        .pathParam("id", createdComm.getId())
                        .auth().oauth2(login.getAccessToken())
                        .when()
                        .delete()
                        .then()
                        .spec(Specification.responseSpec200())
                        .extract()
                        .asString();

        Assert.assertTrue(response.isEmpty(), "Response body should be empty after deletion");
    }

    @Description("Неуспешное удаление комментария из-за отсутствия авторизации")
    @Test
    public void unsuccessDeleteOfCommentNoToken() {
        RegisterRequest login = Methods.createUser();
        NewsResponse newsId = Methods.createNews(login.getAccessToken());
        CommentResponse createdComm = Methods.createComm(login.getAccessToken(), newsId.getId());
        RestAssured.basePath = "/comments/{id}";
        given()
                .spec(Specification.requestSpecJson())
                .pathParam("id", createdComm.getId())
                .when()
                .delete()
                .then()
                .spec(Specification.responseSpec401())
                .body("message", equalTo("Unauthorized"));
    }
}
