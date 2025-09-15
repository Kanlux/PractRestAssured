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
public class DeleteComm {

    @Description("Успешное удаление комментария по ID")
    @Test
    public void successDeleteCommit() {
        CommentResponse createdComm = CommentResponse.createComm();
        String commId = String.valueOf(createdComm.getId());
        RestAssured.baseURI = Contains.URI;
        RestAssured.basePath = "/comments/" + commId;
        String response =
        given()
                .header("Authorization", "Bearer " + Contains.tokenOfTest)
                .when()
                .delete()
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .asString();

        Assert.assertTrue(response.isEmpty(), "Response body should be empty after deletion");
    }

    @Description("Неуспешное удаление комментария из-за отсутствия авторизации")
    @Test
    public void unsuccessDeleteCommit() {
        CommentResponse createdComm = CommentResponse.createComm();
        String commId = String.valueOf(createdComm.getId());
        RestAssured.baseURI = Contains.URI;
        RestAssured.basePath = "/comments/" + commId;
        given()
                .when()
                .delete()
                .then()
                .statusCode(401)
                .log().all()

                .body("message", equalTo("Unauthorized"));
    }
}
