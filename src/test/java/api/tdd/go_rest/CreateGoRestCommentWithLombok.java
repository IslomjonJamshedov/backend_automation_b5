package api.tdd.go_rest;

import api.pojo_classes.go_rest.go_rest_comments.CommandsPagination;
import api.pojo_classes.go_rest.go_rest_comments.CommentData;
import api.pojo_classes.go_rest.go_rest_comments.Links;
import api.pojo_classes.go_rest.go_rest_comments.Meta;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import javax.xml.ws.Response;

public class CreateGoRestCommentWithLombok {
    static Logger logger = LogManager.getLogger(CreateGoRestCommentWithLombok.class);

    Response response;

    @BeforeTest
    public void beforeTest() {
        System.out.println("Starting the API test");
        // By having RestAssured URI set implicitly in to rest assured
        // we just add path to the post call
        RestAssured.baseURI = ConfigReader.getProperty("GoRestBaseURI");
    }

    @Test
    public void createGoRestComment(){
        Links links = Links
                .builder()
                .previous("No Previous Data")
                .current("https://gorest.co.in/public-api/comments?pages=1")
                .next("https://gorest.co.in/public-api/comments?page=2")
                .build();

        CommandsPagination commandsPagination = CommandsPagination
                .builder()
                .total(2000)
                .pages(2000)
                .page(5)
                .limit(10)
                .links(links)
                .build();

        Meta meta = Meta
                .builder()
                .Pagination(commandsPagination).build();

        CommentData commentData0 = CommentData
                .builder()
                .id(2020)
                .post_id(5585)
                .name("Tech Global")
                .email("kesha.runolfsdottir@hotmail.com")
                .body("com.github.javafaker.Faker@77f905e3")
                .build();

        CommentData commentData1 = CommentData
                .builder()
                .id(2020)
                .post_id(5585)
                .name("Tech Global")
                .email("kesha.runolfsdottir@hotmail.com")
                .body("com.github.javafaker.Faker@77f905e3")
                .build();

        CommentData commentData2 = CommentData
                .builder()
                .id(2020)
                .post_id(5585)
                .name("Tech Global")
                .email("kesha.runolfsdottir@hotmail.com")
                .body("com.github.javafaker.Faker@77f905e3")
                .build();

    }
}
