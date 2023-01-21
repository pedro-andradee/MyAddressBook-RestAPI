package com.bluetech.pedro.andrade.MyNotepad.services;

import com.bluetech.pedro.andrade.MyNotepad.dtos.ContactDTO;
import com.bluetech.pedro.andrade.MyNotepad.repositories.ContactRepository;
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
public class ContactServiceIntegrationTests {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    private ContactDTO contactDTO;
    private Pageable pageable;
    private String namePersistedDB;
    private String nameNotPersistedDB;
    private Long countTotalContacts;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        contactDTO = Factory.createContactDto();
        pageable = PageRequest.of(0, 10);
        namePersistedDB = "Amanda";
        nameNotPersistedDB = "Juscelino";
        countTotalContacts = 8L;
        existingId = 7L;
        nonExistingId = 1000L;
    }

    @Test
    public void insertNewContactShouldIncrementDBContactsCount() {
        contactService.insertNewContact(contactDTO);
        assertEquals(countTotalContacts + 1, contactRepository.count());
    }

    @Test
    public void getAllContactsPageOrderByNameShouldReturnNotEmptyPage() {
        Page<ContactDTO> page = contactService.getAllContactsPageOrderByName(pageable);
        assertEquals(countTotalContacts, page.getTotalElements());
    }

    @Test
    public void getAllContactsNameContainingOrderByNameShouldReturnNonEmptyPageWhenDataPersisted() {
        Page<ContactDTO> page = contactService.getAllContactsNameContainingOrderByName(namePersistedDB, pageable);
        assertFalse(page.isEmpty());
    }

    @Test
    public void getAllContactsNameContainingOrderByNameShouldReturnEmptyPageWhenNoDataPersisted() {
        Page<ContactDTO> page = contactService.getAllContactsNameContainingOrderByName(nameNotPersistedDB, pageable);
        assertTrue(page.isEmpty());
    }

    @Test
    public void updateContactShouldReturnContactDTOWhenIdExists() {
        ContactDTO newContactDTO = contactService.updateContact(existingId, contactDTO);
        assertNotNull(newContactDTO);
    }

    @Test
    public void updateContactShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            contactService.updateContact(nonExistingId, contactDTO);
        });
    }

    @Test
    public void deleteContactShouldDeleteContactWhenIdExists() {
        contactService.deleteContact(existingId);
        assertEquals(countTotalContacts - 1, contactRepository.count());
    }

    @Test
    public void deleteContactShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            contactService.deleteContact(nonExistingId);
        });
    }
}
