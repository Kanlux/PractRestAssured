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
}
