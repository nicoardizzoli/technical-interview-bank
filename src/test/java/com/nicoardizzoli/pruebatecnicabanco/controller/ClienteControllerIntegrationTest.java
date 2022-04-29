package com.nicoardizzoli.pruebatecnicabanco.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nicoardizzoli.pruebatecnicabanco.dto.ClienteDTO;
import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }


    @Test
    void itShouldSaveCliente() throws Exception {
        //Given
        String identificacion = "id35";
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .contrasena("1234")
                .apellido("Ardizzoli")
                .nombre("Nicolas")
                .direccion("direccion 123")
                .edad(21)
                .estado(true)
                .genero("MASCULINO")
                .identificacion(identificacion)
                .telefono("123123123")
                .build();

        //When
        ResultActions resultActionPostSaveCliente = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/clientes/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(clienteDTO))));

        //Then
        resultActionPostSaveCliente.andExpect(status().isCreated());

    }


    /**
     * Converting an object in json
     *
     * @param object
     * @return String (json formatted object)
     */
    private String objectToJson(Object object) {
        try {

            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("failed to convert object to json");
            return null;
        }
    }

    /**
     * CONVERT A JSON OBJECT IN A CLASS
     * ALTERNATIVA A ESTE METODO PARA LEER PROPIEDADES DE UN JSON:
     * String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id")
     */

    public <T> Object convertJSONStringToObject(String json, Class<T> objectClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        return mapper.readValue(json, objectClass);
    }
}