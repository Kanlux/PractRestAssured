import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
        NewsResponse createdNews = News.createNews();

        assertEquals("Title", createdNews.getTitle());
        assertEquals("Text", createdNews.getText());
        assertEquals(4732, createdNews.getAuthorId());
    }

    @Description("Неуспешное создание новости из-за отсутсвия всех нужных данных")
    @Test
    public void createNewsNegative() {
        RestAssured.baseURI = Contains.URI;
        RestAssured.basePath = "/posts";

        given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization", "Bearer " + Contains.tokenOfTest)
                .multiPart("text", "Text")
                .multiPart("file", new File("src/main/resources/12.jpg"), "image/jpeg")
                .multiPart("tags[]", "tag1")
                .when()
                .post()
                .then()
                .statusCode(400)

                .body("statusCode", equalTo(400));
    }
}
