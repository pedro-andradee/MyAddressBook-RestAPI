package com.bluetech.pedro.andrade.MyNotepad.services;

import com.bluetech.pedro.andrade.MyNotepad.dtos.NoteDTO;
import com.bluetech.pedro.andrade.MyNotepad.repositories.NoteRepository;
import com.bluetech.pedro.andrade.MyNotepad.services.exceptions.ResourceNotFoundException;
import com.bluetech.pedro.andrade.MyNotepad.utils.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class NoteServiceIntegrationTests {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    private NoteDTO noteDTO;
    private Long countTotalNotes;
    private Pageable pageable;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        noteDTO = Factory.createNoteDto();
        countTotalNotes = 6L;
        pageable = PageRequest.of(0, 10);
        existingId = 1L;
        nonExistingId = 1000L;
    }

    @Test
    public void insertNewNoteShouldIncrementDBNotesCount() {
        noteService.insertNewNote(noteDTO);
        assertEquals(countTotalNotes + 1, noteRepository.count());
    }

    @Test
    public void getAllNotesPageByDateShouldReturnNonEmptyPageWhenDateWithDataPersisted() {
        Page<NoteDTO> page = noteService.getAllNotesPageByDate("2023-01-19", pageable);
        assertFalse(page.isEmpty());
    }

    @Test
    public void getAllNotesPageByDateShouldReturnEmptyPageWhenDateWithoutDataPersisted() {
        Page<NoteDTO> page = noteService.getAllNotesPageByDate("2023-01-22", pageable);
        assertTrue(page.isEmpty());
    }

    @Test
    public void updateNoteShouldReturnNoteDTOWhenIdExists() {
        NoteDTO newNoteDTO = noteService.updateNote(existingId, noteDTO);
        assertNotNull(newNoteDTO);
    }

    @Test
    public void updateNoteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            noteService.updateNote(nonExistingId, noteDTO);
        });
    }

    @Test
    public void deleteNoteShouldDeleteNoteWhenIdExists() {
        noteService.deleteNote(existingId);
        assertEquals(countTotalNotes - 1, noteRepository.count());
    }

    @Test
    public void deleteNoteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            noteService.deleteNote(nonExistingId);
        });
    }
}
