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
    public void createNewsSuccessWithValidToken() {
        RegisterRequest login = Methods.createUser();
        NewsResponse createdNews = Methods.createNews(login.getAccessToken());

        assertEquals(Constants.postTitle, createdNews.getTitle());
        assertEquals(Constants.postText, createdNews.getText());
    }

    @Description("Неуспешное создание новости из-за отсутсвия всех нужных данных")
    @Test
    public void unsuccessСreateOfNewsNoRequiredData() {
        RegisterRequest login = Methods.createUser();
        RestAssured.basePath = Constants.basePathPosts;
        given()
                .spec(Specification.requestSpecMulti())
                .auth().oauth2(login.getAccessToken())
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
