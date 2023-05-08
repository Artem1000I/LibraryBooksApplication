import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class GetBookById extends AbstractTest{

    @BeforeAll
    static void setUp(){

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
    @Test
    void GetBookIdValid(){
        given()

                .when()
                .contentType(ContentType.JSON)
                .get("http://localhost:5000/api/books/2")

                .then()
                .log().all()
                .assertThat()
                //.cookie("cookieName", "cookieValue")
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .body("book.id", Matchers.is(2))
                .body("book.name", Matchers.is("Алгоритмы: построение и анализ"));


    }


    @Test
    void GetBookWithWrongId(){
        given()

                .when()
                .contentType(ContentType.JSON)
                .get("http://localhost:5000/api/books/9999999")

                .then()
                .log().all()
                .assertThat()
                //.cookie("cookieName", "cookieValue")
                .statusCode(404)
                .statusLine("HTTP/1.1 404 NOT FOUND")
                .statusLine(containsString("NOT FOUND"))
                .body("error", Matchers.is("Book with id 9999999 not found"));

    }

}
