package com.bluetech.pedro.andrade.MyNotepad.services;

import com.bluetech.pedro.andrade.MyNotepad.dtos.NoteDTO;
import com.bluetech.pedro.andrade.MyNotepad.models.Note;
import com.bluetech.pedro.andrade.MyNotepad.repositories.NoteRepository;
import com.bluetech.pedro.andrade.MyNotepad.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Transactional
    public NoteDTO insertNewNote(NoteDTO noteDTO) {
        Note entity = new Note();
        copyDtoToEntity(noteDTO, entity);
        entity = noteRepository.save(entity);
        return new NoteDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<NoteDTO> getAllNotesPageByDate(String date, Pageable pageable) {
        LocalDate searchDate;
        if(date != null) {
            searchDate = LocalDate.parse(date);
        } else {
            searchDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        }
        Page<Note> page = noteRepository.findByDate(searchDate, pageable);
        return page.map(NoteDTO::new);
    }

    @Transactional
    public NoteDTO updateNote(Long id, NoteDTO noteDTO) {
        try {
            Note entity = noteRepository.getReferenceById(id);
            copyDtoToEntity(noteDTO, entity);
            entity = noteRepository.save(entity);
            return new NoteDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void deleteNote(Long id) {
        try {
            noteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    private void copyDtoToEntity(NoteDTO noteDTO, Note entity) {
        entity.setText(noteDTO.getText());
        entity.setDate(noteDTO.getDate());
    }
}
