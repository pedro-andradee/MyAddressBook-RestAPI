package com.bluetech.pedro.andrade.MyNotepad.controllers;

import com.bluetech.pedro.andrade.MyNotepad.dtos.NoteDTO;
import com.bluetech.pedro.andrade.MyNotepad.services.NoteService;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
public class NoteControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;
    
    private NoteDTO noteDTO;
    private String todayDateWhenNoRequestParam;
    private String dateRequestParam;
    private PageImpl<NoteDTO> nonEmptyPage;
    private Pageable pageable;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        noteDTO = Factory.createNoteDto();
        todayDateWhenNoRequestParam = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).toString();
        dateRequestParam = "2023-01-22";
        nonEmptyPage = new PageImpl<>(List.of(noteDTO));
        pageable = PageRequest.of(0, 10);
        existingId = 1L;
        nonExistingId = 2L;

        when(noteService.insertNewNote(any(NoteDTO.class))).thenReturn(noteDTO);
        when(noteService.getAllNotesPageByDate(todayDateWhenNoRequestParam, pageable)).thenReturn(nonEmptyPage);
        when(noteService.getAllNotesPageByDate(dateRequestParam, pageable)).thenReturn(nonEmptyPage);
        when(noteService.updateNote(eq(existingId), any(NoteDTO.class))).thenReturn(noteDTO);
        when(noteService.updateNote(eq(nonExistingId), any(NoteDTO.class))).thenThrow(ResourceNotFoundException.class);
        doNothing().when(noteService).deleteNote(existingId);
        doThrow(ResourceNotFoundException.class).when(noteService).deleteNote(nonExistingId);
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
