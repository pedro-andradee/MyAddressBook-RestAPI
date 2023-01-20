package com.bluetech.pedro.andrade.MyNotepad.controllers;

import com.bluetech.pedro.andrade.MyNotepad.dtos.ContactDTO;
import com.bluetech.pedro.andrade.MyNotepad.services.ContactService;
import com.bluetech.pedro.andrade.MyNotepad.services.exceptions.ResourceNotFoundException;
import com.bluetech.pedro.andrade.MyNotepad.utils.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
public class ContactControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

    private ContactDTO contactDTO;
    private PageImpl<ContactDTO> nonEmptyPage;
    private PageImpl<ContactDTO> emptyPage;
    private Pageable pageable;
    private String namePersistedDB;
    private String nameNotPersistedDB;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        contactDTO = Factory.createContactDto();
        nonEmptyPage = new PageImpl<>(List.of(contactDTO));
        emptyPage = new PageImpl<>(List.of());
        pageable = PageRequest.of(0, 10);
        namePersistedDB = "Amanda";
        nameNotPersistedDB = "Juscelino";
        existingId = 1L;
        nonExistingId = 2L;

        when(contactService.insertNewContact(any())).thenReturn(contactDTO);
        when(contactService.getAllContactsPageOrderByName(pageable)).thenReturn(nonEmptyPage);
        when(contactService.getAllContactsNameContainingOrderByName(namePersistedDB, pageable))
                .thenReturn(nonEmptyPage);
        when(contactService.getAllContactsNameContainingOrderByName(nameNotPersistedDB, pageable))
                .thenReturn(emptyPage);
        when(contactService.updateContact(eq(existingId), any())).thenReturn(contactDTO);
        when(contactService.updateContact(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
        doNothing().when(contactService).deleteContact(existingId);
        doThrow(ResourceNotFoundException.class).when(contactService).deleteContact(nonExistingId);
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
    public void getAllContactsPage_ShouldReturnIsOk_WhenRequestParamMatchesPersistedData() throws Exception {
        ResultActions result = mockMvc.perform(get("/contacts")
                .queryParam("name", namePersistedDB)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void getAllContactsPage_ShouldReturnIsOk_WhenRequestParamDoesNotMatchPersistedData() throws Exception {
        ResultActions result = mockMvc.perform(get("/contacts")
                .queryParam("name", nameNotPersistedDB)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    void updateContact_ShouldReturnIsOk_WhenIdExists() throws Exception {
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
    void updateContact_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(contactDTO);

        ResultActions result = mockMvc.perform(put("/contacts/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    void deleteContact_ShouldReturnNoContent_WhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/contacts/{id}", existingId));
        result.andExpect(status().isNoContent());
    }

    @Test
    void deleteContact_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(delete("/contacts/{id}", nonExistingId));
        result.andExpect(status().isNotFound());
    }
}
