import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

@Epic("Авторизация пользователя")
@Owner("Sergey Bordiyan")
public class GetUserData {

    @Description("Успешное получение информации о пользователе по ID")
    @Test
    public void successGetUserDataByValidId() {
        RegisterRequest login = Methods.createUser();

        UserData response = RestAssured
                .given()
                .spec(Specification.requestSpecJson())
                .get(Constants.basePathUsers + login.getUser().getId())
                .then()
                .spec(Specification.responseSpec200())
                .extract()
                .as(UserData.class);

        Assert.assertNotNull(response.getEmail());
    }

    @Description("Неспешное получение информации о пользователе по неверному ID")
    @Test
    public void unsuccessGetUserDataByInvalidId() {
        Specification.installSpecification(Specification.requestSpecJson(), Specification.responseSpec200());

        Response response = RestAssured
                .given()
                .get(Constants.basePathUsers + Constants.invalidId)
                .then()
                .extract()
                .response();

        Assert.assertTrue(response.getBody().asString().isEmpty());
    }
}
