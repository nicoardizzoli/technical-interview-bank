package com.nicoardizzoli.technicalinterviewbank.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nicoardizzoli.technicalinterviewbank.dto.CustomerDTO;
import com.nicoardizzoli.technicalinterviewbank.model.Gender;
import com.nicoardizzoli.technicalinterviewbank.utils.TestUtils;
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

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }


    @Test
    void itShouldSaveCustomer() throws Exception {
        //Given
        CustomerDTO customerDTO = TestUtils.getCustomerDto();

        //When
        ResultActions resultActionPostSaveCustomer = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/customers/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(customerDTO))));

        //Then
        resultActionPostSaveCustomer.andExpect(status().isCreated());

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