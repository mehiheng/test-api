package exercises;

import dataentities.Address;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;


@Tag("demo")
public class RestAssuredExamples {

    private static String myAuthenticationToken;

    /*
    @BeforeClass
    public static void retrieveToken() {

        myAuthenticationToken =

            given().
                auth().
                preemptive().
                basic("username", "password").
            when().
                get("https://my.secure/api").
            then().
                extract().
                path("");
    }
    */

    /*
    Main Points:
    1. Basic Test for common API
    2. API Test with Authentication handle
    3. Junit Multi Parameters Testing
    4. API Test With Object Serilization automaticly
    5. Assertion with hamcrest framework
     */

    @Test
    public void usePreviouslyStoredAuthToken() {

        given().
                auth().
                oauth2(myAuthenticationToken).
                when().
                get("https://my.very.secure/api").
                then().
                assertThat().
                statusCode(200);
    }

    static Stream<Arguments> driverDataProvider() {
        return Stream.of(
                Arguments.of("hamilton", "44"),
                Arguments.of("max_verstappen", "33")
        );
    }

    @Test
    public void validateCountryForZipCode() {

        given().
                when().
                get("http://api.zippopotam.us/us/90210").           // Do a GET call to the specified resource
                then().
                assertThat().                                         // Assert that the value of the element 'country'
                body("country", equalTo("United States"));  // in the response body equals 'United States'
    }

    @Test
    public void checkResponseHeaders() {

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
    public void useQueryParameter() {

        given().
                queryParam("text", "testcase").
                when().
                get("http://md5.jsontest.com").
                then().
                assertThat().
                body("md5", equalTo("7489a25fc99976f06fecb807991c61cf"));
    }

    @Test
    public void usePathParameter() {

        given().
                pathParam("driver", "max_verstappen").
                when().
                get("http://ergast.com/api/f1/drivers/{driver}.json").
                then().
                assertThat().
                body("MRData.DriverTable.Drivers.permanentNumber[0]", equalTo("33"));
    }

    @ParameterizedTest
    @MethodSource("driverDataProvider")
    public void checkPermanentNumberForDriver(String driverName, String permanentNumber) {

        given().
                pathParam("driver", driverName).
                when().
                get("http://ergast.com/api/f1/drivers/{driver}.json").
                then().
                assertThat().
                body("MRData.DriverTable.Drivers.permanentNumber[0]", equalTo(permanentNumber));
    }

    @Test
    public void useBasicAuthentication() {

        given().
                auth().
                preemptive().
                basic("username", "password").
                when().
                get("https://my.secure/api").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void useOAuthAuthentication() {

        given().
                auth().
                oauth2("myAuthenticationToken").
                when().
                get("https://my.very.secure/api").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void checkResponseTimeForApiCall() {

        given().
                when().
                get("http://ergast.com/api/f1/circuits/monza.json").
                then().
                assertThat().
                time(lessThan(100L), TimeUnit.MILLISECONDS);
    }

    private static ResponseSpecification responseSpec;

    @BeforeAll
    public static void createResponseSpec() {

        responseSpec =
                new ResponseSpecBuilder().
                        expectStatusCode(200).
                        expectContentType(ContentType.JSON).
                        build();
    }

    @Test
    public void useResponseSpec() {

        given().
                when().
                get("http://api.zippopotam.us/us/90210").
                then().
                spec(responseSpec).
                and().
                body("country", is("United States"));
    }

    private static RequestSpecification requestSpec;

    @BeforeAll
    public static void createRequestSpec() {

        requestSpec =
                new RequestSpecBuilder().
                        setBaseUri("http://ergast.com").
                        setBasePath("/api/f1").
                        build();
    }

    @Test
    public void useRequestSpec() {

        given().
                spec(requestSpec).
                when().
                get("/circuits/monza.json").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void serializeAddressToJson() {

        Address myAddress = new Address("My street", 1, 1234, "Amsterdam");

        given().
                body(myAddress).
                when().
                post("http://localhost:9876/address").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void deserializeJsonToAddress() {

        Address myAddress =

                given().
                        when().
                        get("http://localhost:9876/address").
                        as(Address.class);

        assertThat(myAddress.getCity(), is("Amsterdam"));
    }
}