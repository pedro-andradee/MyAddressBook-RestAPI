package com.bluetech.pedro.andrade.MyAddressBook.controllers;

import com.bluetech.pedro.andrade.MyAddressBook.dtos.ContactDTO;
import com.bluetech.pedro.andrade.MyAddressBook.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/contacts")
@CrossOrigin("*")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDTO> insertNewContact(@Valid @RequestBody ContactDTO contactDTO) {
        contactDTO = contactService.insertNewContact(contactDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(contactDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(contactDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ContactDTO>> getAllContactsPage(@RequestParam (value = "name",
            required = false) String name, Pageable pageable) {
        Page<ContactDTO> contactDTOPage;
        if(name != null) {
            contactDTOPage = contactService.getAllContactsNameContainingOrderByName(name, pageable);
        } else {
            contactDTOPage = contactService.getAllContactsPageOrderByName(pageable);
        }
        return ResponseEntity.ok().body(contactDTOPage);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @Valid @RequestBody ContactDTO contactDTO) {
        contactDTO = contactService.updateContact(id, contactDTO);
        return ResponseEntity.ok().body(contactDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
