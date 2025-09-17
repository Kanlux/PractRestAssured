import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {

    public static RequestSpecification requestSpecJson() {
        return new RequestSpecBuilder()
                .setBaseUri(Constants.URI)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification requestSpecMulti() {
        return new RequestSpecBuilder()
                .setBaseUri(Constants.URI)
                .setContentType(ContentType.MULTIPART)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseSpec200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseSpec400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseSpec401() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .log(LogDetail.ALL)
                .build();
    }

    public static void installSpecification(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}