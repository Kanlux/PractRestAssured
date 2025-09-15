import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class CommentResponse {
    private int id;
    private String text;
    private int authorId;
    private int postId;
    private Author author;
    private String createdAt;
    private String updatedAt;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getPostId() {
        return postId;
    }

    public Author getAuthor() {
        return author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public static CommentResponse createComm() {
        NewsResponse createdNews = News.createNews();
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
