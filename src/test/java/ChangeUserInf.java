import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Изменение информации о пользователе")
@Owner("Sergey Bordiyan")
public class ChangeUserInf {

    @Description("Успешное изменение информации о пользователе по ID")
    @Test
    public void successChange() {
        RegisterRequest registerRequest = Methods.createUser();

        String token = registerRequest.getAccessToken();
        String userId = String.valueOf(registerRequest.getUser().getId());

        UpdateUser update = new UpdateUser(Constants.firstName, Constants.lastName);

        RestAssured.basePath = "/users/{id}";
        UserData user = given()
                .spec(Specification.requestSpecJson())
                .auth().oauth2(token)
                .pathParam("id", userId)
                .body(update)
                .patch()
                .then()
                .statusCode(200)
                .extract()
                .as(UserData.class);

        Assert.assertEquals(user.getFirstName(), Constants.firstName);
        Assert.assertEquals(user.getLastName(), Constants.lastName);
    }

    @Description("Неуспешное изменение информации о пользователе неверному по ID")
    @Test
    public void negativeChange() {
        RegisterRequest registerRequest = Methods.createUser();

        String token = registerRequest.getAccessToken();

        UpdateUser updateUser = new UpdateUser (Constants.firstName, Constants.lastName);

        String fakeId = UUID.randomUUID().toString();

        given()
                .baseUri(Constants.URI)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(updateUser)
                .patch("/users/" + fakeId)
                .then()
                .statusCode(400)
                .log().all()
                .body("message", equalTo("Validation failed (numeric string is expected)"));
    }
}