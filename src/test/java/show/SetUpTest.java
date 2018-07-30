package show;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class SetUpTest {

    @Test
    void first_time_test_baidu() {
        given().
                when().
                get("http://api.zippopotam.us/us/90210").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON);
    }

    @Test
    void first_time_test_baidu_1() {
        given().
                when().
                get("http://baidu.com").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.HTML)
                .body(containsString("baidu"));

    }


    @Test
    void first_time_test_baidu_2() {
        given().
                when().log().all().
                get("http://v3.wufazhuce.com:8000/api/onelist/idlist/").
                then().log().all().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).assertThat()
                .body("data", hasItems("4845"))
                .body("data[4]", is("4842"))
                .body("data.size()", is(10));


    }
}





