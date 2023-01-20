package com.bluetech.pedro.andrade.MyNotepad.utils;

import com.bluetech.pedro.andrade.MyNotepad.dtos.ContactDTO;
import com.bluetech.pedro.andrade.MyNotepad.models.Contact;

public class Factory {

    public static Contact createContact() {
        return new Contact(9L, "Paulo Roberto", "pauloroberto@gmail.com", "81992356471");
    }

    public static ContactDTO createContactDto() {
        return new ContactDTO(createContact());
    }
}
