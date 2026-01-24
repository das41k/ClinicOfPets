package ru.petrosyan.clinicOfPet.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Veterinarian {

    Integer veterinarian_id;

    @NotBlank(message = "ФИО ветеринара на может быть пустым!")
    @Size(message = "ФИО не может содержать меньше 13 символов и больше 100", max = 100, min = 13)
    String name;

    @NotBlank(message = "Телефон не может быть пуст!")
    @Pattern(regexp = "^((\\+7|8)\\d{10})$")
    String phone;

    public Veterinarian(Integer veterinarian_id, String name, String phone) {
        this.veterinarian_id = veterinarian_id;
        this.name = name;
        this.phone = phone;
    }

    public Veterinarian() {}

    public Integer getVeterinarian_id() {
        return veterinarian_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setVeterinarian_id(Integer veterinarian_id) {
        this.veterinarian_id = veterinarian_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
