package api.tdd.pet_store;

import api.pojo_classes.pet_store.AddAPet;
import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.Tags;
import api.pojo_classes.pet_store.UpdateAPet;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.Arrays;
import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AddPetToStoreWithLombok {

    static Logger logger = LogManager.getLogger(AddPetToStoreWithLombok.class);

    Response response;

    @BeforeSuite
    public void testStarts() {
        logger.info("Starting the test suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Starting the API test");
        // By having RestAssured URI set implicitly in to rest assured
        // we just add path to the post call
        RestAssured.baseURI = ConfigReader.getProperty("PetStoreBaseURI");
    }

    @Test
    public void addPetToStore() {

        Category category = Category
                .builder()
                .id(10)
                .name("horse")
                .build();

        Tags tags0 = Tags
                .builder()
                .id(15)
                .name("unicorn")
                .build();

        Tags tags1 = Tags
                .builder()
                .id(16)
                .name("pearl")
                .build();

        AddAPet addAPet = AddAPet
                .builder()
                .id(8)
                .category(category)
                .name("rainbow")
                .photoUrls(Arrays.asList("My horse's Photo URL"))
                .tags(Arrays.asList(tags0, tags1))
                .status("available")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(addAPet)
                .when().post("/v2/pet")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();

        int statusCode = response.statusCode();
        System.out.println("Post status code is " + statusCode);

        String body = response.asString();
        System.out.println("My POST response body: " + body);


        // getting the pet id from the response body
        int actualPetId = response.jsonPath().getInt("id");
        int actualTagsId0 = response.jsonPath().getInt("tags[0].id");

        int actualPetIdWithJayWay = JsonPath.read(response.asString(), "id");
        logger.info("My id with jayway is " + actualPetIdWithJayWay);

        int actualTagsId0WithJayWay = JsonPath.read(response.asString(), "tags[0].id");
        logger.info("My pet tag id with jayway is " + actualTagsId0WithJayWay);

        int actualCategoryIdWithJayWay = JsonPath.read(response.asString(), "category.id");
        logger.info("Category id from the response is : " + actualCategoryIdWithJayWay);

        // getting the pet id from the request body
//        int expectPetId = addAPet.getId();
        int expectPetId = 3;
        int expectTagsId0 = tags0.getId();

        // we are logging the information
        logger.info("My actual pet id is " + actualPetId);

        // we are debugging the assertion
        logger.debug("The actual pet id should be " + expectPetId + " but we found " + actualPetId);
//        Assert.assertEquals(actualPetId, expectPetId);

        // Assertion with Hamcrest
        assertThat(
                //reason why we are asserting
                "I am checking if expected value is matching with actual one",
                //actual value
                actualPetIdWithJayWay,
                //expected value
                is(expectPetId)
        );

        logger.debug("The actual category id should be " + category.getId() + " but we found " + actualCategoryIdWithJayWay);
        assertThat(
                // Reason why you are doing the validation
                "I am validating the category id",
                // actual value
                actualCategoryIdWithJayWay,
                // expected value
                is(category.getId())
        );


        System.out.println("Update the pet");

        Category updateCategory = Category
                .builder()
                .id(11)
                .name("horse")
                .build();

        UpdateAPet updateAPet = UpdateAPet
                .builder()
                .id(8)
                .category(updateCategory)
                .name("rainbow")
                .photoUrls(Arrays.asList("My horse's Photo URL"))
                .tags(Arrays.asList(tags0, tags1))
                .status("unavailable")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(updateAPet)
                .when().put("/v2/pet")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();

    }

}
