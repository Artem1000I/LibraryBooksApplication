import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class UpdateBookInformation extends AbstractTest{

    @BeforeAll
    static void setUp(){

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void UpdateBookInfo(){
        given()
                .body( "{\n"
                        +"\"name\": \"Острые козырьки\",\n"
                        +"\"author\": \"Томас Шелби\",\n"
                        +"\"isElectronicBook\": true,\n"
                        +"\"year\": 1022"
                        + "}")
                .when()
                .contentType(ContentType.JSON)
                .put("http://localhost:5000/api/books/1")

                .then()
                .log().all()
                .assertThat()
                //.cookie("cookieName", "cookieValue")
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .body("book.name", Matchers.is("Острые козырьки"))
                .body("book.id", Matchers.is(1)) //ПРОВЕРИТЬ И ПЕРЕДЕЛАТЬ
                .body("book.author", Matchers.is("Томас Шелби"))
                .body("book.isElectronicBook", Matchers.is(true))
                .body("book.year", Matchers.is(1022));
    }

    @Test
    void UpdateBookInvalidId(){
        given()
                .body( "{\n"
                        +"\"name\": \"Король лев\",\n"
                        +"\"author\": \"Тимон и пумба\",\n"
                        +"\"isElectronicBook\": true,\n"
                        +"\"year\": 1995"
                        + "}")
                .when()
                .contentType(ContentType.JSON)
                .put("http://localhost:5000/api/books/9999999")

                .then()
                .log().all()
                .assertThat()
                //.cookie("cookieName", "cookieValue")
                .statusCode(404)
                .statusLine("HTTP/1.1 404 NOT FOUND")
                .statusLine(containsString("NOT FOUND"))
                .body("error", Matchers.is("Book with id 9999999 not found"));
    }


    @Test
    void UpdateBookNameFieldWithSpaces(){
        given()
                .body( "{\n"
                        +"\"name\": \"   \",\n"
                        +"\"author\": \"Тимон и пумба\",\n"
                        +"\"isElectronicBook\": true,\n"
                        +"\"year\": 1947"
                        + "}")
                .when()
                .contentType(ContentType.JSON)
                .put("http://localhost:5000/api/books/1")

                .then()
                .log().all()
                .assertThat()
                //.cookie("cookieName", "cookieValue")
                .statusCode(400)
                .statusLine("HTTP/1.1 400 BAD REQUEST")
                .statusLine(containsString("BAD REQUEST"))
                .body("error", Matchers.is("Required name field is not filled"));
        //Поле нейм не должно быть пустым проверка
    }



    @Test
    void AddBookNameNotFilled(){
        given()
                .body( "{\n"
                        +"\"name\": \"   \",\n"
                        +"\"author\": \"Симба\",\n"
                        +"\"isElectronicBook\": true,\n"
                        +"\"year\": 1986"
                        + "}")
                .when()
                .contentType(ContentType.JSON)
                .put("http://localhost:5000/api/books/1")

                .then()
                .log().all()
                .assertThat()
                //.cookie("cookieName", "cookieValue")
                .statusCode(400)
                .statusLine("HTTP/1.1 400 BAD REQUEST")
                .statusLine(containsString("BAD REQUEST"))
                .body("error", Matchers.is("Required name field is not filled"));
        //Поле нейм не должно быть пустым проверка
    }

}
