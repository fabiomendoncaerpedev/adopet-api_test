package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private TutorService service;

    @Test
    @DisplayName("should return code 200 indicating that tutor was registered successfully")
    void positiveScenarioPostCadastrar() throws Exception {
        String json = """
                {
                    "nome": "Fabiao da Massa",
                    "telefone": "79991935136",
                    "email": "fabiao.massa@gmail.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("should return code 400 warning that there is information missing")
    void negativeScenarioPostCadastrar() throws Exception {
        String json = """
                {
                    "telefone": "79991935136",
                    "email": "fabiao.massa@gmail.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("should return code 200 indicating update was performed successfully")
    void positiveScenarioPutAtualizar() throws Exception {
        String json = """
                {
                    "id": 1,
                    "nome": "Fabiao da Massa",
                    "telefone": "79991935136",
                    "email": "fabiao.massa@gmail.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("should return code 400 warning that update could not be performed")
    void negativeScenarioPutAtualizar() throws Exception {
        String json = """
                {
                    "id": 1,
                    "nome": "Fabiao da Massa",
                    "telefone": "0",
                    "email": "fabiao.massa@gmail.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }
}