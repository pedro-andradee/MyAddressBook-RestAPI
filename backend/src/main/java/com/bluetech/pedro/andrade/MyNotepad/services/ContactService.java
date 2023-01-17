package com.bluetech.pedro.andrade.MyNotepad.services;

import com.bluetech.pedro.andrade.MyNotepad.dtos.ContactDTO;
import com.bluetech.pedro.andrade.MyNotepad.models.Contact;
import com.bluetech.pedro.andrade.MyNotepad.repositories.ContactRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public ContactDTO insertNewContact(ContactDTO contactDTO) {
        Contact entity = new Contact();
        BeanUtils.copyProperties(contactDTO, entity);
        entity = contactRepository.save(entity);
        return new ContactDTO(entity);
    }
    
    public Page<ContactDTO> getAllContactsPage(Pageable pageable) {
        Page<Contact> page = contactRepository.findAll(pageable);
        return page.map(ContactDTO::new);
    }
}
