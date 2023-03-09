package api.tdd.pet_store;

import api.pojo_classes.pet_store.AddAPet;
import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.Tags;
import api.pojo_classes.pet_store.UpdateAPet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.Arrays;
import java.util.Calendar;

public class AddPetToStoreWithLombok {

    Response response;

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


        // getting the pet id from the response body
        int actualPetId = response.jsonPath().getInt("id");
        int actualTagsId0 = response.jsonPath().getInt("tags[0].id");

        // getting the pet id from the request body
        int expectPetId = addAPet.getId();
        int expectTagsId0 = tags0.getId();


        Assert.assertEquals(actualPetId, expectPetId);

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
