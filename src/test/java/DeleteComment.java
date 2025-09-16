import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Удаление комментария")
@Owner("Sergey Bordiyan")
public class DeleteComment {

    @BeforeAll
    static void login() {
        Methods.createUser();
    }

    @Description("Успешное удаление комментария по ID")
    @Test
    public void successDeleteCommit() {
        CommentResponse createdComm = Methods.createComm();
        String commId = String.valueOf(createdComm.getId());
        RestAssured.basePath = "/comments/{id}";
        String response =
                given()
                        .spec(Specification.requestSpecJson())
                        .pathParam("id", commId)
                        .auth().oauth2(Constants.tokenOfTest)
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
    public void unsuccessDeleteCommit() {
        CommentResponse createdComm = Methods.createComm();
        String commId = String.valueOf(createdComm.getId());
        RestAssured.basePath = "/comments/{id}";
        given()
                .spec(Specification.requestSpecJson())
                .pathParam("id", commId)
                .when()
                .delete()
                .then()
                .spec(Specification.responseSpec401())
                .body("message", equalTo("Unauthorized"));
    }
}
