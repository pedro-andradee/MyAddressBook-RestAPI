package com.bluetech.pedro.andrade.MyAddressBook.controllers;

import com.bluetech.pedro.andrade.MyAddressBook.dtos.ContactDTO;
import com.bluetech.pedro.andrade.MyAddressBook.utils.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ContactControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ContactDTO contactDTO;
    private String namePersistedDB;
    private String nameNotPersistedDB;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        contactDTO = Factory.createContactDto();
        namePersistedDB = "Amanda";
        nameNotPersistedDB = "Juscelino";
        existingId = 1L;
        nonExistingId = 1000L;

    }

    @Test
    public void insertNewContact_ShouldReturnContactDTOCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(contactDTO);

        ResultActions result = mockMvc.perform(post("/contacts")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.phoneNumber").exists());
    }

    @Test
    public void getAllContactsPage_ShouldReturnIsOk_WhenNoRequestParam() throws Exception {
        ResultActions result = mockMvc.perform(get("/contacts")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void getAllContactsPage_ShouldReturnIsOk_WhenPageableRequestParam() throws Exception {
        ResultActions result = mockMvc.perform(get("/contacts")
                .queryParam("page", "0")
                .queryParam("size", "12")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void getAllContactsPage_ShouldReturnIsOk_WhenRequestParamMatchesPersistedData() throws Exception {
        ResultActions result = mockMvc.perform(get("/contacts")
                .queryParam("name", namePersistedDB)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].name").exists());
    }

    @Test
    public void getAllContactsPage_ShouldReturnIsOk_WhenRequestParamDoesNotMatchPersistedData() throws Exception {
        ResultActions result = mockMvc.perform(get("/contacts")
                .queryParam("name", nameNotPersistedDB)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void updateContact_ShouldReturnIsOk_WhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(contactDTO);

        ResultActions result = mockMvc.perform(put("/contacts/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.phoneNumber").exists());
    }

    @Test
    public void updateContact_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(contactDTO);

        ResultActions result = mockMvc.perform(put("/contacts/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteContact_ShouldReturnNoContent_WhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/contacts/{id}", existingId));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteContact_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(delete("/contacts/{id}", nonExistingId));
        result.andExpect(status().isNotFound());
    }
}
