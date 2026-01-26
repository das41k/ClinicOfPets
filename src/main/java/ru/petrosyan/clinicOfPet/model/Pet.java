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

    @NotBlank(message = "Выберите владельца!")
    private Integer owner_id;
    @NotBlank(message = "Выберите тип питомца!")
    private Integer petType_id;

    public Pet() {}

    public Pet(Integer pet_id, String name, LocalDate dateBirth, Integer owner_id, Integer petType_id) {
        this.pet_id = pet_id;
        this.name = name;
        this.dateBirth = dateBirth;
        this.owner_id = owner_id;
        this.petType_id = petType_id;
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

    public Integer getOwner_id() {
        return owner_id;
    }

    public Integer getPetType_id() {
        return petType_id;
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

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public void setPetType_id(Integer petType_id) {
        this.petType_id = petType_id;
    }
}
