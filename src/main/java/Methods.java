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

        Constants.userId = String.valueOf(response.getUser().getId());
        Constants.email = randomEmail;
        return response;
    }

    public static NewsResponse createNews(String token) {
        RestAssured.baseURI = Constants.baseURI;
        RestAssured.basePath = Constants.basePathPosts;

        return given()
                .contentType(ContentType.MULTIPART)
                .auth().oauth2(token)
                .multiPart("title", Constants.postTitle)
                .multiPart("text", Constants.postText)
                .multiPart("tags[]", Constants.postTag)
                .multiPart("file", new File(Constants.imageFile), "image/jpg")
                .when()
                .post()
                .then()
                .statusCode(201)
                .log().all()
                .extract()
                .as(NewsResponse.class);
    }

    public static CommentResponse createComm(String token, int id) {
        CommentRequest comment = new CommentRequest("Comm", id);

        RestAssured.baseURI = Constants.baseURI;
        RestAssured.basePath = Constants.basePathComments;
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
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
