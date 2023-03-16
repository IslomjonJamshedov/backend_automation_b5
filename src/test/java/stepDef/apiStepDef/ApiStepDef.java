package stepDef.apiStepDef;

import api.pojo_classes.go_rest.CreateGoRestUserWithLombok;
import api.tdd.go_rest.GoRestWithLombok;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import utils.ConfigReader;

import static org.hamcrest.Matchers.equalTo;

public class ApiStepDef {

    static Logger logger = LogManager.getLogger(GoRestWithLombok.class);

    Response response;
    Faker faker = new Faker();

    int goRestId;

    @Given("I send a POST request with body")
    public void iSendAPOSTRequestWithBody() {

        RestAssured.baseURI = ConfigReader.getProperty("GoRestBaseURI");

        CreateGoRestUserWithLombok createUser = CreateGoRestUserWithLombok
                // with the help of the Lombok, we are assigning the values to variables
                //coming from Bean class
                .builder()
                .name("Tech Global")
                .email(faker.internet().emailAddress())
                .gender("female")
                .status("active")
                .build();

        response = RestAssured
                .given().log().all()
//                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(createUser)
                .when().post("/public/v2/users")
                .then().log().all()
                .extract().response();
    }

    @Then("Status code is {int}")
    public void statusCodeIs(int statusCode) {

        Assert.assertEquals(response.statusCode(), statusCode);
    }
}
