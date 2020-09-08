package se.docker.alpine.build.gateway.api.v1;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Restful tests")
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("unittest")
public class PackageApiTest
{
    public static final String MY_PACKAGE = "myPackage";
    public static final String MY_PACKAGE_BASE64 = "bXlQYWNrYWdl";

    String id = "1";

    @DisplayName("Creates a member resource in the collection resource. " +
            "The URI of the created member resource is automatically assigned and returned " +
            "in the response Location header field. ")
    @Test
    @Order(1)
    public void testCollectionPostPackage()
    {
        given()
                .when().post("/v1/packages")
                .then()
                .header("Location", matchesPattern("http://localhost:[0-9]+/v1/packages/1"))
                .statusCode(201);
    }

    @DisplayName("Retrieves the URIs of the member resources of the collection resource in the response body")
    @Test
    @Order(2)
    public void testCollectionGetPackage()
    {
        given()
                .when().get("/v1/packages")
                .then()
                .statusCode(200)
                .body(matchesPattern("\\[http://localhost:[0-9]+/v1/packages/1\\]"));
    }


    @DisplayName("Replaces all the representations of the member source resource or create the member resource if it does not exist, with the representation in the request body.")
    @Test
    @Order(11)
    public void testMemberPutSourcePackage() throws IOException
    {
        Path sourcePathTar = Paths.get("src", "test", "resources", "testData", "source.tar");
        assertTrue(Files.exists(sourcePathTar));
        byte[] sourceTar = Files.readAllBytes(sourcePathTar);
        String originalInput = "test input";
        given().pathParam("id", id)
                .body(sourceTar)
                .when().put("/v1/packages/{id}/source")
                .then()
                .statusCode(200);
    }

    @DisplayName("Get a representation of the member source resource or create the member resource if it does not exist, with the representation in the request body.")
    @Test
    @Order(12)
    public void testMemberGetSourcePackage() throws IOException
    {
        //Path sourcePathTar = Paths.get("src", "test", "resources", "testData", "source.tar");
        //assertTrue(Files.exists(sourcePathTar));
        //byte[] sourceTar = Files.readAllBytes(sourcePathTar);

        String originalInput = "test input";
        given().pathParam("id", id)
                .when().get("/v1/packages/{id}/source")
                .then()
                .statusCode(200);
    }

    @DisplayName("Replaces all the representations of the member name resource or create the member resource if it does not exist, with the representation in the request body.")
    @Test
    @Order(15)
    public void testMemberPutNamePackage()
    {
        given()
                .pathParam("id", id)
                .body(MY_PACKAGE)
                .when().put("/v1/packages/{id}/name")
                .then()
                .statusCode(200);
    }

    @DisplayName("Replaces all the representations of the member resource or create the member resource if it does not exist, with the representation in the request body.")
    @Test
    @Order(17)
    public void testMemberGetNamePackage()
    {
        given()
                .pathParam("id", id)
                .when()
                .get("/v1/packages/{id}/name")
                .then()
                .body(is(MY_PACKAGE))
                .statusCode(200);
    }

    @DisplayName("Retrieves a representation of the member resource in the response body")
    @Test
    @Order(18)
    public void testMemberGetPackage()
    {
        given()
                .pathParam("id", id)
                .when().get("/v1/packages/{id}")
                .then()
                .statusCode(200)
                .body(is("{\"name\"" + ":\"" + MY_PACKAGE + "\"}"));
    }

    @DisplayName("Replace all the representations of the member resources of the collection resource with the representation in the request body, or create the collection resource if it does not exist.")
    @Test
    @Order(30)
    public void testCollectionPutPackage()
    {
        given()
                .pathParam("id", id)
                .when().put("/v1/packages/{id}")
                .then()
                .header("Location", matchesPattern("http://localhost:[0-9]+/v1/packages/1"))
                .statusCode(201);
    }

    @DisplayName("Retrieves a representation of the member resource in the response body")
    @Test
    @Order(31)
    public void testMemberGetNameEmptyPackage()
    {
        given()
                .pathParam("id", id)
                .when()
                .get("/v1/packages/{id}/name")
                .then()
                .body(is(""))
                .statusCode(200);
    }


}