import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Изменение комментария")
@Owner("Sergey Bordiyan")
public class ChangeComment {

    @BeforeAll
    static void login() {
        Methods.createUser();
    }

    @Description("Успешное изменение комментария по валидному ID")
    @Test
    public void successChangeComm() {
        CommentResponse createdComm = Methods.createComm();
        int commId = createdComm.getId();
        CommentRequest comment = new CommentRequest("Comment", commId);

        RestAssured.basePath = "/comments/{id}";
        CommentResponse response = given()
                .spec(Specification.requestSpecJson())
                .auth().oauth2(Constants.tokenOfTest)
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
    public void unsuccessChangeComm() {
        CommentResponse createdComm = Methods.createComm();
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
