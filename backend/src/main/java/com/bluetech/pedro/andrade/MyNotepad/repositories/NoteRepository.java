package com.bluetech.pedro.andrade.MyNotepad.repositories;

import com.bluetech.pedro.andrade.MyNotepad.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}
