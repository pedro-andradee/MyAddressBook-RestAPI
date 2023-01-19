package com.bluetech.pedro.andrade.MyNotepad.repositories;

import com.bluetech.pedro.andrade.MyNotepad.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> findByDate(LocalDate date, Pageable pageable);
}
