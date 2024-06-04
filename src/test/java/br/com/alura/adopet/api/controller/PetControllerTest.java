package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {
    @Autowired
    private MockMvc mvc;
    @Mock
    private PetService service;

    @Test
    @DisplayName("should return code 200 indicating successfull listing of available pets")
    void positiveScenarioGetListarTodosDisponiveis() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/pets")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }
}