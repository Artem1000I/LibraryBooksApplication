import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class DeleteBook extends AbstractTest{

    @BeforeAll
    static void setUp(){

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void DeleteBooks(){
        given()

                .when()
                .contentType(ContentType.JSON)
                .delete("http://localhost:5000/api/books/1")

                .then()
                .log().all()
                .assertThat()
                //.cookie("cookieName", "cookieValue")
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .body("result", Matchers.is(true));
    }


    @Test
    void DeleteBookInvalidId(){
        given()

                .when()
                .contentType(ContentType.JSON)
                .delete("http://localhost:5000/api/books/199")

                .then()
                .log().all()
                .assertThat()
                //.cookie("cookieName", "cookieValue")
                .statusCode(404)
                .statusLine("HTTP/1.1 404 NOT FOUND")
                .statusLine(containsString("NOT FOUND"))
                .body("error", Matchers.is("Book with id 199 not found"));
    }



}


