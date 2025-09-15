import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class News {
    private int id;
    private String title;
    private String text;
    private String coverPath;
    private int authorId;
    private String createdAt;
    private String updatedAt;
    private int rating;
    private int commentsCount;
    private Author author;
    private List<NewsTag> tags;
    private List<News> posts;

    public News() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getRating() {
        return rating;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public Author getAuthor() {
        return author;
    }

    public List<NewsTag> getTags() {
        return tags;
    }

    public List<News> getPosts() {
        return posts;
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
}
