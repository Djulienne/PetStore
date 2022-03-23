// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


// 3 - Classe
public class Pet {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; //endere�o da entidade Pet


    // 3.2 - M�todos e Fun��es
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson))); //o paths vai ler o caminho, e o read vai ler esse arquivo paths do caminho
    }

    // Incluir - Create - Post
    @Test(priority=1)
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Ent�o
        // ex. DADO que eu acessei o site - QUANDO eu preencho usuario e senha - ENTAO eu entro no site
        // Given - When - Then
        // parte do rest assured

        given() //dado
                .contentType("application/json") // comum em API REST - apis antiga era "text/xml"
                .log().all()
                .body(jsonBody)
        .when() //quando
                .post(uri)
        .then() //ent�o
                .log().all()
                .statusCode(200)
                .body("name", is ("Biluvi"))
                .body("status" , is("available"))
                .body("category.name", is("AX2345LORT")) //quando os valores nao vem em colchetes
                .body("tags.name", contains("data"))  // quando os valores vem em colchetes
        ;

    }

    @Test(priority=2)
    public void consultarPet(){
        String petId = "1990102201";

        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Biluvi"))
                .body("category.name", is("AX2345LORT"))
                .body("status", is("available"))
        .extract()
                .path("category.name")
        ;
        System.out.println("O token � " + token);

    }

    @Test(priority=3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Biluvi"))
                .body("status", is("sold"))
        ;
    }

    @Test(priority=4)
    public void excluirPet(){
        String petId = "1990102201";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;

    }
}
