import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

@Epic("Авторизация пользователя")
@Owner("Sergey Bordiyan")
public class GetUser {

    @Description("Успешное получение информации о пользователе по ID")
    @Test
    public void successGet() {
        Specification.installSpecification(Specification.requestSpecJson(), Specification.responseSpec200());

        UserData response = RestAssured
                .given()
                .get("/users/4618")
                .then()
                .log().all()
                .extract()
                .as(UserData.class);

        Assert.assertNotNull(response.getEmail());
    }

    @Description("Неспешное получение информации о пользователе по неверному ID")
    @Test
    public void negativeGet() {
        Specification.installSpecification(Specification.requestSpecJson(), Specification.responseSpec200());

        Response response = RestAssured
                .given()
                .get("users/99999")
                .then()
                .extract()
                .response();

        Assert.assertTrue(response.getBody().asString().isEmpty());
    }
}
