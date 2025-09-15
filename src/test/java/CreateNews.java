import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;

@Epic(" Создание новости")
@Owner("Sergey Bordiyan")
public class CreateNews {

    @Description("Успешное создание новости авторизованного пользователя")
    @Test
    public void createNewsSuccess() {
        NewsResponse createdNews = Methods.createNews();

        assertEquals("Title", createdNews.getTitle());
        assertEquals("Text", createdNews.getText());
    }

    @Description("Неуспешное создание новости из-за отсутсвия всех нужных данных")
    @Test
    public void createNewsNegative() {
        RestAssured.basePath = "/posts";

        given()
                .spec(Specification.requestSpecMulti())
                .auth().oauth2(Constants.tokenOfTest)
                .multiPart("text", "Text")
                .multiPart("file", new File("src/main/resources/12.jpg"), "image/jpeg")
                .multiPart("tags[]", "tag1")
                .when()
                .post()
                .then()
                .spec(Specification.responseSpec400())
                .body("statusCode", equalTo(400));
    }
}
