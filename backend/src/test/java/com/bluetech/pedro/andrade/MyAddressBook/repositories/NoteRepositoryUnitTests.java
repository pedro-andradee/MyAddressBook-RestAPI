package com.bluetech.pedro.andrade.MyAddressBook.repositories;

import com.bluetech.pedro.andrade.MyAddressBook.models.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@DataJpaTest
public class NoteRepositoryUnitTests {

    @Autowired
    private NoteRepository noteRepository;
    Pageable pageable;
    LocalDate dateWithDataPersisted;
    LocalDate dateWithNoDataPersisted;

    @BeforeEach
    void setUp() throws Exception {
        pageable = PageRequest.of(0, 10);
        dateWithDataPersisted = LocalDate.of(2023, 01, 19);
        dateWithNoDataPersisted = LocalDate.of(2023, 01, 20);
    }

    @Test
    public void findByDateShouldReturnNonEmptyPageWhenDataPersistedOnGivenDate() {
        Page<Note> page = noteRepository.findByDate(dateWithDataPersisted, pageable);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByDateShouldReturnEmptyPageWhenNoDataPersistedOnGivenDate() {
        Page<Note> page = noteRepository.findByDate(dateWithNoDataPersisted, pageable);
        Assertions.assertTrue(page.isEmpty());
    }
}
