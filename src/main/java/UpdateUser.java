import io.restassured.http.ContentType;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class UpdateUser {
    String firstName;
    String lastName;

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
}