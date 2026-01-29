package ru.petrosyan.clinicOfPet.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Owner {
    Integer owner_id;

    @NotBlank(message = "ФИО владельца на может быть пустым!")
    @Size(message = "ФИО не может содержать меньше 13 символов и больше 100", max = 100, min = 13)
    String name;

    @NotBlank(message = "Телефон не может быть пуст!")
    @Pattern(regexp = "^((\\+7|8)\\d{10})$", message = "Телефон должен начинаться с +7 или с 8, а затем содержать 10 цифр")
    String phone;

    public Owner(Integer owner_id, String name, String phone, String address) {
        this.owner_id = owner_id;
        this.name = name;
        this.phone = phone;
    }

    public Owner() {}

    public Integer getOwner_id() {
        return owner_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }


    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
