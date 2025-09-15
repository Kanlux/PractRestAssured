import io.restassured.http.ContentType;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class UpdateUser {
    String firstName;
    String lastName;

    public UpdateUser() {

    }

    public UpdateUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static RegisterRequest createUser() {
        String randomEmail = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@nv.dunice.net";
        RegisterResponse newUser = new RegisterResponse(randomEmail, Contains.password);

        RegisterRequest response = given()
                .baseUri(Contains.URI)
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
}