import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsIterableContaining.hasItems;

@Epic("Добавление комментария к новости")
@Owner("Sergey Bordiyan")
public class CreateComm {

    @Description("Успешное добавление комментария")
    @Test
    public void successCreateNewsComm() {
        CommentResponse createdComment = CommentResponse.createComm();

        Assert.assertEquals(createdComment.getText(), "Comm");
    }

    @Description("Неуспешное добавление комментария из-за отсутсвия текста")
    @Test
    public void unsuccessCreateNewsComm() {
        NewsResponse createdNews = News.createNews();
        String newsId = String.valueOf(createdNews.getId());
        CommentRequest comment = new CommentRequest(Integer.parseInt(newsId));

        RestAssured.baseURI = Contains.URI;
        RestAssured.basePath = "/comments";
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + Contains.tokenOfTest)
                        .body(comment)
                        .when()
                        .post()
                        .then()
                        .statusCode(400)
                        .log().all()

                        .body("message", hasItems("text should not be empty", "text must be a string"));
    }
}
