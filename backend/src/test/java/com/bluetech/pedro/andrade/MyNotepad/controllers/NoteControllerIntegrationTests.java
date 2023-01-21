package com.bluetech.pedro.andrade.MyNotepad.controllers;

import com.bluetech.pedro.andrade.MyNotepad.dtos.NoteDTO;
import com.bluetech.pedro.andrade.MyNotepad.utils.Factory;
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
public class NoteControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private NoteDTO noteDTO;
    private String dateRequestParam;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        noteDTO = Factory.createNoteDto();
        dateRequestParam = "2023-01-22";
        existingId = 1L;
        nonExistingId = 1000L;
    }

    @Test
    public void insertNewNote_ShouldReturnNoteDTOCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(noteDTO);

        ResultActions result = mockMvc.perform(post("/notes")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.text").exists());
        result.andExpect(jsonPath("$.date").exists());
    }

    @Test
    public void getAllNotesPageByDate_ShouldReturnIsOk_WhenNoRequestParam() throws Exception {
        ResultActions result = mockMvc.perform(get("/notes")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void getAllNotesPageByDate_ShouldReturnIsOk_WhenPageableRequestParam() throws Exception {
        ResultActions result = mockMvc.perform(get("/notes")
                .queryParam("page", "0")
                .queryParam("size", "12")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void getAllNotesPageByDate_ShouldReturnIsOk_WhenHasRequestParam() throws Exception {
        ResultActions result = mockMvc.perform(get("/notes")
                .queryParam("date", dateRequestParam)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void updateNote_ShouldReturnIsOk_WhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(noteDTO);

        ResultActions result = mockMvc.perform(put("/notes/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.text").exists());
        result.andExpect(jsonPath("$.date").exists());
    }

    @Test
    public void updateNote_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(noteDTO);

        ResultActions result = mockMvc.perform(put("/notes/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void delete_ShouldReturnNoContent_WhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/notes/{id}", existingId));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void delete_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(delete("/notes/{id}", nonExistingId));
        result.andExpect(status().isNotFound());
    }
}
