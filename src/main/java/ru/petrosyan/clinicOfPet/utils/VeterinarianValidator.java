package ru.petrosyan.clinicOfPet.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.petrosyan.clinicOfPet.dao.VeterinarianDAO;
import ru.petrosyan.clinicOfPet.model.Veterinarian;

import java.util.Objects;

@Component
public class VeterinarianValidator implements Validator {

    private final VeterinarianDAO veterinarianDAO;

    @Autowired
    public VeterinarianValidator(VeterinarianDAO veterinarianDAO) {
        this.veterinarianDAO = veterinarianDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Validator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Veterinarian veterinarian = (Veterinarian) target;
        // Получаем ветеринара по телефону из БД
        Veterinarian existingVet = veterinarianDAO.getVeterinarianByPhone(veterinarian.getPhone());
        // Если нашли ветеринара с таким телефоном И это не тот же самый ветеринар (при обновлении)
        if (existingVet != null && !Objects.equals(existingVet.getVeterinarian_id(), veterinarian.getVeterinarian_id())) {
            errors.rejectValue("phone", "", "Ветеринар с данным номером уже есть в системе!");
        }
    }
}
