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
}
