package ru.petrosyan.clinicOfPet.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.petrosyan.clinicOfPet.dao.OwnerDAO;
import ru.petrosyan.clinicOfPet.model.Owner;

import java.util.Objects;

@Component
public class OwnerValidator implements Validator {

    private final OwnerDAO ownerDAO;

    @Autowired
    public OwnerValidator(OwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Owner.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Owner owner = (Owner) target;
        Owner existByPhone = ownerDAO.getOwnerByPhone(owner.getPhone());

        if (existByPhone != null && !Objects.equals(existByPhone.getOwner_id(), owner.getOwner_id())) {
            errors.rejectValue("phone", "", "Владелец с данным номером уже есть в системе!");
        }
    }
}
