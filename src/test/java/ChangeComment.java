import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Изменение комментария")
@Owner("Sergey Bordiyan")
public class ChangeComment {

    @Description("Успешное изменение комментария по валидному ID")
    @Test
    public void givenLoginAndId_WhenChangeComment_ThenSuccess() {
        RegisterRequest login = Methods.createUser();
        NewsResponse newsId = Methods.createNews(login.getAccessToken());
        CommentResponse createdComm = Methods.createComm(login.getAccessToken(), newsId.getId());
        int commId = createdComm.getId();
        CommentRequest comment = new CommentRequest("Comment", commId);

        //Если я помещаю в константы: basePathComments = "/comments/{id}" Мне выдаёт ошибку
        //Из-за этого оставил нетронутым

        RestAssured.basePath = "/comments/{id}";
        CommentResponse response = given()
                .spec(Specification.requestSpecJson())
                .auth().oauth2(login.getAccessToken())
                .pathParam("id", commId)
                        .body(comment)
                        .when()
                        .patch()
                        .then()
                        .spec(Specification.responseSpec200())
                        .extract()
                        .as(CommentResponse.class);

        Assert.assertEquals(response.getText(), "Comment");
    }

    @Description("Неуспешное изменение комментария из-за отсутствия авторизации")
    @Test
    public void unsuccessChangeOfCommentNoToken() {
        RegisterRequest login = Methods.createUser();
        NewsResponse newsId = Methods.createNews(login.getAccessToken());
        CommentResponse createdComm = Methods.createComm(login.getAccessToken(), newsId.getId());
        int commId = createdComm.getId();
        CommentRequest comment = new CommentRequest("Comment", commId);

        RestAssured.basePath = "/comments/{id}";
        given()
                .spec(Specification.requestSpecJson())
                .pathParam("id", commId)
                .body(comment)
                .when()
                .patch()
                .then()
                .spec(Specification.responseSpec401())
                .body("message", equalTo("Unauthorized"));
    }
}
