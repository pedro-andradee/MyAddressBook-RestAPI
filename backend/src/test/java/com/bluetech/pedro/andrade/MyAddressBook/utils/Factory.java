package com.bluetech.pedro.andrade.MyAddressBook.utils;

import com.bluetech.pedro.andrade.MyAddressBook.dtos.ContactDTO;
import com.bluetech.pedro.andrade.MyAddressBook.dtos.NoteDTO;
import com.bluetech.pedro.andrade.MyAddressBook.models.Contact;
import com.bluetech.pedro.andrade.MyAddressBook.models.Note;

import java.time.LocalDate;

public class Factory {

    public static Contact createContact() {
        return new Contact(9L, "Paulo Roberto", "pauloroberto@gmail.com", "81992356471");
    }

    public static ContactDTO createContactDto() {
        return new ContactDTO(createContact());
    }

    public static Note createNote() {
        return new Note(7L, "Ir ao médico às 15h", LocalDate.of(2023, 01, 20));
    }

    public static NoteDTO createNoteDto() {
        return new NoteDTO(createNote());
    }
}
