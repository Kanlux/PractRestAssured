import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.UUID;

import static io.restassured.RestAssured.given;

@Epic("Регистрация пользователя")
@Owner("Sergey Bordiyan")
public class RegistrationTest {

    @Description("Успешная регистрация пользователя с валидным паролем и email")
    @Test
    public void successReg() {
        Specification.installSpecification(Specification.requestSpecJson(), Specification.responseSpec201());
        String randomEmail = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@nv.dunice.net";
        RegisterResponse newUser = new RegisterResponse(randomEmail, Contains.password);

        RegisterRequest response = given()
                .body(newUser)
                .post("/auth/signup")
                .then()
                .statusCode(201)
                .extract()
                .as(RegisterRequest.class);

        System.out.println("Response body:\n" + response.getUser().toString());

        Assert.assertEquals(response.getUser().getEmail(), randomEmail);
        System.out.println("Регистрация успешна. Токен: " + response.getAccessToken());
    }

    @Description("Неуспешная регистрация пользователя с неуникальными значениями")
    @Test
    public void negativeReg() {
        Specification.installSpecification(Specification.requestSpecJson(), Specification.responseSpec400());
        RegisterResponse newUser = new RegisterResponse(Contains.duplicateEmail, "password123");

        Response response = RestAssured
                .given()
                .body(newUser)
                .post("/auth/signup")
                .then()
                .statusCode(400)
                .log().all()
                .extract()
                .response();

        String errorMessage  = response.jsonPath().getString("message");
        Assert.assertEquals("Validation error", errorMessage)  ;
    }
}