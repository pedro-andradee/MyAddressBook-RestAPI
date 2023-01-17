package com.bluetech.pedro.andrade.MyNotepad.services;

import com.bluetech.pedro.andrade.MyNotepad.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
}
