package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.service.AbrigoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {
    @Autowired
    private MockMvc mvc;
    @Mock
    private AbrigoService abrigoService;

    @Test
    @DisplayName("should list all shelters")
    void cenarioGetListar() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/abrigos")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("should return code 200 indicating successful operation")
    void positiveScenarioPostCadastrar() throws Exception {
        String json = """
                {
                    "nome": "Abrigo do Seu Zé",
                    "telefone": "79991935136",
                    "email": "abrigo.ze@gmail.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("should return code 200 warning json is missing mandatory properties")
    void negativeScenarioPostCadastrar() throws Exception {
        String json = """
                {
                    "telefone": "79991935136",
                    "email": "abrigo.ze@gmail.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("should return code 200 indicating successful listing of pets")
    void positiveScenarioGetListarPets() throws Exception {
        String idOrName = "1";
        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/{idOuNome}/pets", idOrName)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("should return code 404 warning that no shelter was found")
    void negativeScenarioGetListarPets() throws Exception {
        String idOrName = "miauzin";

        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/{idOuNome}/pets", idOrName)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("should return code 200 indicating that pet was successfully registered")
    void positiveScenarioGetCadastrarPet() throws Exception {
        String idOrName = "1";
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Felix",
                    "raca": "gatao zyka",
                    "idade": 2,
                    "cor": "preto",
                    "peso": 2.5
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos/{idOuNome}/pets", idOrName)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("should return code 400 warning that no shelter was found")
    void negativeScenarioGetCadastrarPet() throws Exception {
        String idOrName = "miauzin casa de adocao";
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Felix",
                    "raca": "gatao zyka",
                    "idade": 2,
                    "cor": "preto",
                    "peso": 2.5
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos/{idOuNome}/pets", idOrName)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }
}