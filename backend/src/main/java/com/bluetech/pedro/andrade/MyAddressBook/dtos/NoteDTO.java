package com.bluetech.pedro.andrade.MyAddressBook.dtos;

import com.bluetech.pedro.andrade.MyAddressBook.models.Note;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

public class NoteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "The text field cannot be empty")
    private String text;
    private LocalDate date;

    public NoteDTO() {
    }

    public NoteDTO(Long id, String text, LocalDate date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public NoteDTO(Note model) {
        this.id = model.getId();
        this.text = model.getText();
        this.date = model.getDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
