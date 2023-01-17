package com.bluetech.pedro.andrade.MyNotepad.controllers;

import com.bluetech.pedro.andrade.MyNotepad.dtos.ContactDTO;
import com.bluetech.pedro.andrade.MyNotepad.services.ContactService;
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
    public ResponseEntity<Page<ContactDTO>> getAllContactsPage(Pageable pageable) {
        Page<ContactDTO> contactDTOPage = contactService.getAllContactsPage(pageable);
        return ResponseEntity.ok().body(contactDTOPage);
    }
}
