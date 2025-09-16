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
    public void successGetInfUser() {
        Methods.createUser();

        UserData response = RestAssured
                .given()
                .spec(Specification.requestSpecJson())
                .get("/users/" + Constants.userId)
                .then()
                .spec(Specification.responseSpec200())
                .extract()
                .as(UserData.class);

        Assert.assertNotNull(response.getEmail());
    }

    @Description("Неспешное получение информации о пользователе по неверному ID")
    @Test
    public void negativeGetInfUser() {
        Specification.installSpecification(Specification.requestSpecJson(), Specification.responseSpec200());

        Response response = RestAssured
                .given()
                .get("users/" + Constants.invalidId)
                .then()
                .extract()
                .response();

        Assert.assertTrue(response.getBody().asString().isEmpty());
    }
}
