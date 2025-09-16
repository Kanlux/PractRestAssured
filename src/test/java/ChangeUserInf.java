import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Изменение информации о пользователе")
@Owner("Sergey Bordiyan")
public class ChangeUserInf {

    @BeforeAll
    static void login() {
        Methods.createUser();
    }

    @Description("Успешное изменение информации о пользователе по ID")
    @Test
    public void successChange() {
        UpdateUser update = new UpdateUser(Constants.firstName, Constants.lastName);

        RestAssured.basePath = "/users/{id}";
        UserData user = given()
                .spec(Specification.requestSpecJson())
                .auth().oauth2(Constants.tokenOfTest)
                .pathParam("id", Constants.userId)
                .body(update)
                .patch()
                .then()
                .spec(Specification.responseSpec200())
                .extract()
                .as(UserData.class);

        Assert.assertEquals(user.getFirstName(), Constants.firstName);
        Assert.assertEquals(user.getLastName(), Constants.lastName);
    }

    @Description("Неуспешное изменение информации о пользователе неверному по ID")
    @Test
    public void negativeChange() {
        UpdateUser updateUser = new UpdateUser (Constants.firstName, Constants.lastName);

        String fakeId = UUID.randomUUID().toString();

        RestAssured.basePath = "/users/{id}";
        given()
                .spec(Specification.requestSpecJson())
                .auth().oauth2(Constants.tokenOfTest)
                .pathParam("id", fakeId)
                .body(updateUser)
                .patch()
                .then()
                .statusCode(400)
                .log().all()
                .body("message", equalTo("Validation failed (numeric string is expected)"));
    }
}