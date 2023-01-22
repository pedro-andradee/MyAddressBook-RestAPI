package com.bluetech.pedro.andrade.MyAddressBook.repositories;

import com.bluetech.pedro.andrade.MyAddressBook.models.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class ContactRepositoryUnitTests {

    @Autowired
    private ContactRepository contactRepository;
    Pageable pageable;
    String firstNameOrderedPage;

    @BeforeEach
    void setUp() throws Exception {
        pageable = PageRequest.of(0, 10);
        firstNameOrderedPage = "Amanda Rego";
    }

    @Test
    public void findByOrderByNameShouldReturnAscOrderedPage() {
        Page<Contact> page = contactRepository.findByOrderByName(pageable);
        Contact contact = page.getContent().get(0);
        Assertions.assertEquals(firstNameOrderedPage, contact.getName());
    }

    @Test
    public void findByNameContainingOrderByNameShouldReturnOrderedPageWhenContainsName() {
        Page<Contact> page = contactRepository.findByNameContainingOrderByName("Amanda", pageable);
        Contact contact = page.getContent().get(0);
        Assertions.assertEquals(firstNameOrderedPage, contact.getName());
    }

    @Test
    public void findByNameContainingOrderByNameShouldReturnEmptyPageWhenDoesNotContainName() {
        Page<Contact> page = contactRepository.findByNameContainingOrderByName("Juscelino", pageable);
        Assertions.assertTrue(page.isEmpty());
    }
}
