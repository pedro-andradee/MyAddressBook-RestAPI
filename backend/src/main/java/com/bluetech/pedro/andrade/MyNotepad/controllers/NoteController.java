package com.bluetech.pedro.andrade.MyNotepad.controllers;

import com.bluetech.pedro.andrade.MyNotepad.dtos.NoteDTO;
import com.bluetech.pedro.andrade.MyNotepad.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDTO> insertNewNote(@Valid @RequestBody NoteDTO noteDTO) {
        noteDTO = noteService.insertNewNote(noteDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(noteDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(noteDTO);
    }

    @GetMapping
    public ResponseEntity<Page<NoteDTO>> getAllNotesPageByDate(@RequestParam (value = "date",
            required = false) String date, Pageable pageable) {
        Page<NoteDTO> noteDTOPage = noteService.getAllNotesPageByDate(date, pageable);
        return ResponseEntity.ok().body(noteDTOPage);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable Long id, @Valid @RequestBody NoteDTO noteDTO) {
        noteDTO = noteService.updateNote(id, noteDTO);
        return ResponseEntity.ok().body(noteDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
