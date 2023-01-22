package com.bluetech.pedro.andrade.MyAddressBook.services;

import com.bluetech.pedro.andrade.MyAddressBook.dtos.ContactDTO;
import com.bluetech.pedro.andrade.MyAddressBook.models.Contact;
import com.bluetech.pedro.andrade.MyAddressBook.repositories.ContactRepository;
import com.bluetech.pedro.andrade.MyAddressBook.services.exceptions.ResourceNotFoundException;
import com.bluetech.pedro.andrade.MyAddressBook.utils.Factory;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ContactServiceUnitTests {

    @InjectMocks
    private ContactService contactService;

    @Mock
    private ContactRepository contactRepository;

    private Contact contact;
    private ContactDTO contactDTO;
    private PageImpl<Contact> nonEmptyPage;
    private PageImpl<Contact> emptyPage;
    private Pageable pageable;
    private String namePersistedDB;
    private String nameNotPersistedDB;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        contact = Factory.createContact();
        contactDTO = Factory.createContactDto();
        nonEmptyPage = new PageImpl<>(List.of(contact));
        emptyPage = new PageImpl<>(List.of());
        pageable = PageRequest.of(0, 10);
        namePersistedDB = "Amanda";
        nameNotPersistedDB = "Juscelino";
        existingId = 1L;
        nonExistingId = 2L;

        when(contactRepository.save(ArgumentMatchers.any(Contact.class))).thenReturn(contact);
        when(contactRepository.findByOrderByName(pageable)).thenReturn(nonEmptyPage);
        when(contactRepository.findByNameContainingOrderByName(namePersistedDB, pageable)).thenReturn(nonEmptyPage);
        when(contactRepository.findByNameContainingOrderByName(nameNotPersistedDB, pageable)).thenReturn(emptyPage);
        when(contactRepository.getReferenceById(existingId)).thenReturn(contact);
        when(contactRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        doNothing().when(contactRepository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(contactRepository).deleteById(nonExistingId);
    }

    @Test
    public void insertNewContactShouldReturnContactDTO() {
        ContactDTO newContactDTO = contactService.insertNewContact(contactDTO);
        assertNotNull(newContactDTO);
    }

    @Test
    public void getAllContactsPageOrderByNameShouldReturnNotNullPage() {
        Page<ContactDTO> page = contactService.getAllContactsPageOrderByName(pageable);
        assertNotNull(page);
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
    public void deleteContactShouldDoNothingWhenIdExists() {
        assertDoesNotThrow(() -> {
            contactService.deleteContact(existingId);
        });
    }

    @Test
    public void deleteContactShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            contactService.deleteContact(nonExistingId);
        });
    }
}
