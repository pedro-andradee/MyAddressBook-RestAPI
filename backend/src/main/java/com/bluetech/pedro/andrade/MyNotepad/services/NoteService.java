package com.bluetech.pedro.andrade.MyNotepad.services;

import com.bluetech.pedro.andrade.MyNotepad.dtos.NoteDTO;
import com.bluetech.pedro.andrade.MyNotepad.models.Note;
import com.bluetech.pedro.andrade.MyNotepad.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void copyDtoToEntity(NoteDTO noteDTO, Note entity) {
        entity.setText(noteDTO.getText());
        entity.setDate(noteDTO.getDate());
    }
}
