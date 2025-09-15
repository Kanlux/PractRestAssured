import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
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
        RegisterRequest registerRequest = UpdateUser.createUser();

        String token = registerRequest.getAccessToken();
        String userId = String.valueOf(registerRequest.getUser().getId());

        UpdateUser update = new UpdateUser(Contains.firstName, Contains.lastName);

        UserData user = given()
                .baseUri(Contains.URI)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(update)
                .patch("/users/" + userId)
                .then()
                .statusCode(200)
                .extract()
                .as(UserData.class);

        Assert.assertEquals(user.getFirstName(), Contains.firstName);
        Assert.assertEquals(user.getLastName(), Contains.lastName);
    }

    @Description("Неуспешное изменение информации о пользователе неверному по ID")
    @Test
    public void negativeChange() {
        RegisterRequest registerRequest = UpdateUser.createUser();

        String token = registerRequest.getAccessToken();

        UpdateUser updateUser = new UpdateUser (Contains.firstName, Contains.lastName);

        String fakeId = UUID.randomUUID().toString();

        given()
                .baseUri(Contains.URI)
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