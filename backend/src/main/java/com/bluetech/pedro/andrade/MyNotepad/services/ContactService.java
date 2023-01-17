package com.bluetech.pedro.andrade.MyNotepad.services;

import com.bluetech.pedro.andrade.MyNotepad.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    
}
