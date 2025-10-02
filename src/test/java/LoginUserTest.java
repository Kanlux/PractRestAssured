import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

@Epic("Авторизация пользователя")
@Owner("Sergey Bordiyan")
public class LoginUserTest {

    @Description("Успешная авторизация зарегистрированного пользователя с валидными данными")
    @Test
    public void successLogByValidData() {
        Methods.createUser();
        LoginRequest login = new LoginRequest(Constants.email, Constants.password);

        LoginResponse response = RestAssured.given()
                .spec(Specification.requestSpecJson())
                .body(login)
                .post(Constants.basePathUsersLogin)
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        Assert.assertEquals(response.getUser().getEmail(), Constants.email);
    }

    @Description("Неуспешная авторизация незарегистрированного пользователя")
    @Test
    public void unsuccessLogOfUnregisteredUser() {
        Specification.installSpecification(Specification.requestSpecJson(), Specification.responseSpec401());
        LoginRequest login = new LoginRequest("serjik@mail.com", "StrongPassword");

        Response response = RestAssured.given()
                .body(login)
                .post(Constants.basePathUsersLogin)
                .then()
                .statusCode(401)
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 401);

        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "Unauthorized");
    }
}