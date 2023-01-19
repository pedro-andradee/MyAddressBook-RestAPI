package com.bluetech.pedro.andrade.MyNotepad.repositories;

import com.bluetech.pedro.andrade.MyNotepad.models.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findByOrderByName(Pageable pageable);
    Page<Contact> findByNameContainingOrderByName(String name, Pageable pageable);
}
