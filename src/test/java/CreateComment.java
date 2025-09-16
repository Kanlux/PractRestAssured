import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsIterableContaining.hasItems;

@Epic("Добавление комментария к новости")
@Owner("Sergey Bordiyan")
public class CreateComment {

    @Description("Успешное добавление комментария")
    @Test
    public void givenLoginAndId_WhenAddComment_ThenSuccess() {
        RegisterRequest login = Methods.createUser();
        NewsResponse newsId = Methods.createNews(login.getAccessToken());
        CommentResponse createdComment = Methods.createComm(login.getAccessToken(), newsId.getId());

        Assert.assertEquals(createdComment.getText(), "Comm");
    }

    @Description("Неуспешное добавление комментария из-за отсутсвия текста")
    @Test
    public void unsuccessCreateCommentNoText() {
        RegisterRequest login = Methods.createUser();
        NewsResponse createdNews = Methods.createNews(login.getAccessToken());
        String newsId = String.valueOf(createdNews.getId());
        CommentRequest comment = new CommentRequest(Integer.parseInt(newsId));

        RestAssured.basePath = Constants.basePathComments;
        given()
                .spec(Specification.requestSpecJson())
                .auth().oauth2(login.getAccessToken())
                .body(comment)
                .when()
                .post()
                .then()
                .spec(Specification.responseSpec400())
                .body("message", hasItems("text should not be empty", "text must be a string"));
    }
}
