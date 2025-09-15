import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.io.File;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class Methods {

    public static RegisterRequest createUser() {
        String randomEmail = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@nv.dunice.net";
        RegisterResponse newUser = new RegisterResponse(randomEmail, Constants.password);

        RegisterRequest response = given()
                .baseUri(Constants.URI)
                .contentType(ContentType.JSON)
                .body(newUser)
                .post("auth/signup")
                .then()
                .extract()
                .response()
                .as(RegisterRequest.class);

        System.out.println("Регистрация успешна. Токен: " + response.getAccessToken());
        System.out.println("ID: " + response.getUser().getId());
        return response;
    }

    public static NewsResponse createNews() {
        RestAssured.baseURI = "https://api.news.academy.dunice.net";
        RestAssured.basePath = "/posts";

        return given()
                .contentType(ContentType.MULTIPART)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDczMiwiaWF0IjoxNzU3Njg3ODYzLCJleHAiOjE3NTg4OTc0NjN9.GnuqTTCws3cd3nqieqVuI1xKfiBPpFh8r31rV9wN0_M")
                .multiPart("title", "Title")
                .multiPart("text", "Text")
                .multiPart("tags[]", "tag1")
                .multiPart("file", new File("src/main/resources/12.jpg"), "image/jpeg")
                .when()
                .post()
                .then()
                .statusCode(201)
                .log().all()
                .extract()
                .as(NewsResponse.class);
    }

    public static CommentResponse createComm() {
        NewsResponse createdNews = Methods.createNews();
        String newsId = String.valueOf(createdNews.getId());

        CommentRequest comment = new CommentRequest("Comm", Integer.parseInt(newsId));

        RestAssured.baseURI = "https://api.news.academy.dunice.net";
        RestAssured.basePath = "/comments/";

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDczMiwiaWF0IjoxNzU3Njg3ODYzLCJleHAiOjE3NTg4OTc0NjN9.GnuqTTCws3cd3nqieqVuI1xKfiBPpFh8r31rV9wN0_M")
                .body(comment)
                .when()
                .post()
                .then()
                .statusCode(201)
                .log().all()
                .extract()
                .as(CommentResponse.class);
    }
}
