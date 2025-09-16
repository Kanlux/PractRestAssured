import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@Epic("Изменение новости")
@Owner("Sergey Bordiyan")
public class ChangeNews {

    @BeforeAll
    static void login() {
        Methods.createUser();
    }

    @Description("Успешное изменение новости с вводом всех нужных данных")
    @Test
    public void successChangeNews() {
        NewsResponse createdNews = Methods.createNews();
        String newsId = String.valueOf(createdNews.getId());

        RestAssured.basePath = "/posts/{id}";
        NewsResponse response =
        given()
                .spec(Specification.requestSpecMulti())
                .pathParam("id", newsId)
                .auth().oauth2(Constants.tokenOfTest)
                .multiPart("title", "Hi")
                .multiPart("text", "Hello World!")
                .multiPart("file", new File("src/main/resources/12.jpg"), "image/jpeg")
                .when()
                .patch()
                .then()
                .spec(Specification.responseSpec200())
                .extract()
                .as(NewsResponse.class);

        Assert.assertEquals(response.getText(), "Hello World!");
    }

    @Description("Неуспешное изменение новости с вводом не всех нужных данных")
    @Test
    public void unsuccessChangeNews() {
        RestAssured.basePath = "/posts/{id}";
                given()
                        .spec(Specification.requestSpecMulti())
                        .pathParam("id", Constants.invalidId)
                        .auth().oauth2(Constants.tokenOfTest)
                        .multiPart("title", "Hi")
                        .multiPart("text", "Hello World!")
                        .multiPart("file", new File("src/main/resources/12.jpg"), "image/jpeg")
                        .when()
                        .patch()
                        .then()
                        .statusCode(404).log().all()
                        .body("message", equalTo("Not Found"));
    }
}
