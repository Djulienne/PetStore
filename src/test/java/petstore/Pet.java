// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;


// 3 - Classe
public class Pet {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade Pet


    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson))); //o paths vai ler o caminho, e o read vai ler esse arquivo paths do caminho
    }

    // Incluir - Create - Post
    @Test
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // ex. DADO que eu acessei o site - QUANDO eu preencho usuario e senha - ENTAO eu entro no site
        // Given - When - Then

        given() //dado
                .contentType("application/json") // comum em API REST - apis antiga era "text/xml"
                .log().all()
                .body(jsonBody)
        .when() //quando
                .post(uri)
        .then() //então
                .log().all()
                .statusCode(200)
        ;

    }
}
