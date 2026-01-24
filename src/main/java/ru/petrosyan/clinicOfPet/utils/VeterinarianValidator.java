package ru.petrosyan.clinicOfPet.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.petrosyan.clinicOfPet.dao.VeterinarianDAO;
import ru.petrosyan.clinicOfPet.model.Veterinarian;

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
        Veterinarian veterinarian = (Veterinarian)  target;
        if (veterinarianDAO.getVeterinarianByPhone(veterinarian.getPhone()) != null) {
            errors.rejectValue("email", "Ветеринар с данным номером уже есть в системе!");
        }
    }
}
