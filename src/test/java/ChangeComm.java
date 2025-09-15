import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Изменение комментария")
@Owner("Sergey Bordiyan")
public class ChangeComm {

    @Description("Успешное изменение комментария по валидному ID")
    @Test
    public void successChangeComm() {
        CommentResponse createdComm = CommentResponse.createComm();
        String commId = String.valueOf(createdComm.getId());
        CommentRequest comment = new CommentRequest("Comment", Integer.parseInt(commId));

        RestAssured.baseURI = Contains.URI;
        RestAssured.basePath = "/comments/" + commId;
        CommentResponse response =
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + Contains.tokenOfTest)
                .body(comment)
                .when()
                .patch()
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .as(CommentResponse.class);

        Assert.assertEquals(response.getText(), "Comment");
    }

    @Description("Неуспешное изменение комментария из-за отсутствия авторизации")
    @Test
    public void unsuccessChangeComm() {
        CommentResponse createdComm = CommentResponse.createComm();
        String commId = String.valueOf(createdComm.getId());
        CommentRequest comment = new CommentRequest("Comment", Integer.parseInt(commId));

        RestAssured.baseURI = Contains.URI;
        RestAssured.basePath = "/comments/" + commId;
        given()
                .contentType(ContentType.JSON)
                .body(comment)
                .when()
                .patch()
                .then()
                .statusCode(401)
                .log().all()

                .body("message", equalTo("Unauthorized"));
    }
}
