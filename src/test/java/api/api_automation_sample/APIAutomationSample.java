package api.api_automation_sample;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class APIAutomationSample {
    public static void main(String[] args) {

        /**
         * Response is an interface coming from the RestAssured Library
         * The Response variable "response" stores all the components of API calls
         * including the request and response
         * RestAssured is written with BDD flow
         *
         */
        Response response;

        Faker faker = new Faker();

        // Creating the post request
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .body("{\n" +
                        "    \"name\": \"" + faker.name().fullName() + "\",\n" +
                        "    \"gender\": \"male\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().post("https://gorest.co.in/public/v2/users")
                .then().log().all().extract().response();

//        System.out.println(response.asString());

        int postId = response.jsonPath().getInt("id");

        System.out.println("Id is coming from response " + postId);

        // Creating the get request to fetch specific user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .when().get("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();

//        response = RestAssured
//                .given().log().all()
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
//                .when().get("https://gorest.co.in/public/v2/users")
//                .then().log().all().extract().response();

        // Creating the PUT request to update the existing user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .body("{\n" +
                        "    \"name\": \"" + faker.name().fullName() + "\",\n" +
                        "    \"gender\": \"male\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().put("https://gorest.co.in/public/v2/users/"+ postId)
                .then().log().all().extract().response();


        // Creating the get request to update the existing user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .body("{\n" +
                        "    \"name\": \"" + faker.name().fullName() + "\",\n" +
                        "    \"gender\": \"male\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().patch("https://gorest.co.in/public/v2/users/"+ postId)
                .then().log().all().extract().response();

        int patchId = response.jsonPath().getInt("id");
        Assert.assertEquals(postId, patchId, "Expected id "+postId+ " we found "+ patchId);

        // Creating the get request to delete the specific user
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .when().delete("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();
    }
}
