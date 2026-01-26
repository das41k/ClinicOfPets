package ru.petrosyan.clinicOfPet.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Pet {

    private Integer pet_id;

    @NotBlank(message = "Имя питомца на может быть пустым!")
    @Size(message = "ФИО не может содержать меньше 2 символов и больше 30", max = 30, min = 2)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Дата рождения не может быть в будущем!")
    private LocalDate dateBirth;

    private Owner owner;
    @NotBlank(message = "Выберите тип питомца!")
    private String petType;

    public Pet() {}

    public Pet(Integer pet_id, String name, LocalDate dateBirth, Owner owner, String petType) {
        this.pet_id = pet_id;
        this.name = name;
        this.dateBirth = dateBirth;
        this.owner = owner;
        this.petType = petType;
    }

    public Integer getPet_id() {
        return pet_id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getPetType() {
        return petType;
    }

    public void setPet_id(Integer pet_id) {
        this.pet_id = pet_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }
}
