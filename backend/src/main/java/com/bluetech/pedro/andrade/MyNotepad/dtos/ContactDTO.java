package com.bluetech.pedro.andrade.MyNotepad.dtos;

import com.bluetech.pedro.andrade.MyNotepad.models.Contact;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ContactDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "The name field cannot be empty")
    private String name;
    private String email;
    private String phoneNumber;

    public ContactDTO() {
    }

    public ContactDTO(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public ContactDTO(Contact model) {
        this.id = model.getId();
        this.name = model.getName();
        this.email = model.getEmail();
        this.phoneNumber = model.getPhoneNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
