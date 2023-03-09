package api.tdd.go_rest;

import api.pojo_classes.go_rest.CreateGoRestUserWithLombok;
import api.pojo_classes.go_rest.UpdateGoRestUserWithLombok;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.hamcrest.core.IsEqual.equalTo;

public class GoRestWithLombok {

    Response response;
    /**
     * ObjectMapper is a class coming form fasterxml to convert Java object to Json
     */
    ObjectMapper objectMapper = new ObjectMapper();

    Faker faker = new Faker();

    int goRestId;

    int expectedGoRestId;
    String expectedGoRestName;
    String expectedGoRestEmail;
    String expectedGoRestGender;
    String expectedGoRestStatus;

    @BeforeTest
    public void beforeTest() {
        System.out.println("Starting the API test");
        // By having RestAssured URI set implicitly in to rest assured
        // we just add path to the post call
        RestAssured.baseURI = ConfigReader.getProperty("GoRestBaseURI");
    }

    @Test
    public void goRestCRUDWithLombok() throws JsonProcessingException {
        // Creating a POJO (Bean) object

        CreateGoRestUserWithLombok createUser = CreateGoRestUserWithLombok
                // with the help of the Lombok, we are assigning the values to variables
                //coming from Bean class
                .builder()
                .name("Tech Global")
                .email(faker.internet().emailAddress())
                .gender("female")
                .status("active")
                .build();

        System.out.println("Creating the user");

        response = RestAssured
                .given().log().all()
//                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(createUser)
//                .when().post("https://gorest.co.in/public/v2/users")
                .when().post("/public/v2/users")
                .then().log().all()
                //validating the status code with rest assured
                .and().assertThat().statusCode(201)
                //validating the response time is less than the specified one
                .time(Matchers.lessThan(3000L))
                //validating the value from the body with hamcrest
                .body("name", equalTo("Tech Global"))
                //validating the response content type
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("Fetching the user");
        goRestId = response.jsonPath().getInt("id");

        response = RestAssured
                .given().log().all()
//                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().get("/public/v2/users/" + goRestId)
                .then().log().all()
                //validating the status code with rest assured
                .and().assertThat().statusCode(200)
                //validating the response time is less than the specified one
                .time(Matchers.lessThan(3000L))
                //validating the value from the body with hamcrest
                .body("name", equalTo("Tech Global"))
                //validating the response content type
                .contentType(ContentType.JSON)
                .extract().response();


        System.out.println("Updating the user");
        UpdateGoRestUserWithLombok updateGoRestUserWithLombok = UpdateGoRestUserWithLombok
                //building the update java body
                .builder()
                .email(faker.internet().emailAddress())
                .gender("male")
                .status("inactive")
                .build();

        response = RestAssured
                .given().log().all()
//                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(updateGoRestUserWithLombok)
                .when().put("/public/v2/users/" + goRestId)
                .then().log().all()
                //validating the status code with rest assured
                .and().assertThat().statusCode(200)
                //validating the response time is less than the specified one
                .time(Matchers.lessThan(3000L))
                //validating the value from the body with hamcrest
                .body("name", equalTo("Tech Global"))
                //validating the response content type
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("Deleting the user");

        /** -----------------------DELETE-----------------------*/

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().delete("/public/v2/users/" + goRestId)
                .then().log().all()
                .and().assertThat().statusCode(204)
                //validating the response time is less than the specified one
                .time(Matchers.lessThan(3000L))
                .extract().response();

    }
}