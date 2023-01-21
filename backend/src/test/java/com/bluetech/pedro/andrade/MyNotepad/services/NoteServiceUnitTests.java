package com.bluetech.pedro.andrade.MyNotepad.services;

import com.bluetech.pedro.andrade.MyNotepad.dtos.NoteDTO;
import com.bluetech.pedro.andrade.MyNotepad.models.Note;
import com.bluetech.pedro.andrade.MyNotepad.repositories.NoteRepository;
import com.bluetech.pedro.andrade.MyNotepad.services.exceptions.ResourceNotFoundException;
import com.bluetech.pedro.andrade.MyNotepad.utils.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class NoteServiceUnitTests {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    private Note note;
    private NoteDTO noteDTO;
    private LocalDate dateWithDataPersisted;
    private LocalDate dateWithoutDataPersisted;
    private PageImpl<Note> nonEmptyPage;
    private PageImpl<Note> emptyPage;
    private Pageable pageable;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        note = Factory.createNote();
        noteDTO = Factory.createNoteDto();
        dateWithDataPersisted = LocalDate.of(2023, 01, 19);
        dateWithoutDataPersisted = LocalDate.of(2023, 01, 22);
        nonEmptyPage = new PageImpl<>(List.of(note));
        emptyPage = new PageImpl<>(List.of());
        pageable = PageRequest.of(0, 10);
        existingId = 1L;
        nonExistingId = 2L;

        when(noteRepository.save(ArgumentMatchers.any(Note.class))).thenReturn(note);
        when(noteRepository.findByDate(dateWithDataPersisted, pageable)).thenReturn(nonEmptyPage);
        when(noteRepository.findByDate(dateWithoutDataPersisted, pageable)).thenReturn(emptyPage);
        when(noteRepository.getReferenceById(existingId)).thenReturn(note);
        when(noteRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        doNothing().when(noteRepository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(noteRepository).deleteById(nonExistingId);
    }

    @Test
    public void insertNewNoteShouldReturnNoteDTO() {
        NoteDTO newNoteDTO = noteService.insertNewNote(noteDTO);
        assertNotNull(newNoteDTO);
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
    public void deleteNoteShouldDoNothingWhenIdExists() {
        assertDoesNotThrow(() -> {
            noteService.deleteNote(existingId);
        });
    }

    @Test
    public void deleteNoteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            noteService.deleteNote(nonExistingId);
        });
    }
}
