package api.tdd.pet_store;

import api.pojo_classes.pet_store.AddAPet;
import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.Tags;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.DataProviderUtil;

import java.util.Arrays;

public class AddPetToStoreWithLombokAndDataProvider {

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

    @Test(dataProvider = "DataFromExcel", dataProviderClass = DataProviderUtil.class)
    public void addPetWithDataProvider(String Pet_id, String Category_id, String Category_name, String Pet_Name,
                                       String Photo_URL, String Tag_Id, String Tag_Name, String Pet_Status) {
        Category category = Category
                .builder()
                .id((int )Double.parseDouble((Category_id)))
                .name(Category_name)
                .build();

        Tags tags = Tags
                .builder()
                .id((int )Double.parseDouble(Tag_Id))
                .name(Tag_Name)
                .build();

        AddAPet addAPet = AddAPet
                .builder()
                .id((int )Double.parseDouble(Pet_id))
                .category(category)
                .name(Pet_Name)
                .photoUrls(Arrays.asList(Photo_URL))
                .tags(Arrays.asList(tags))
                .status(Pet_Status)
                .build();


        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(addAPet)
                .when().post("/v2/pet")
                .then().log().all()
                .extract().response();
    }
}

