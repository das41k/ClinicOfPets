package ru.petrosyan.clinicOfPet.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.petrosyan.clinicOfPet.dao.AppointmentDAO;
import ru.petrosyan.clinicOfPet.model.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class AppointmentValidator implements Validator {
    private final AppointmentDAO appointmentDAO;

    public AppointmentValidator(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Appointment.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Appointment appointment = (Appointment) target;
        checkValidDate(appointment.getDateAdmission(), errors);
        checkValidTime(appointment.getTimeAdmission(), errors);
        checkUniqueForAppointment(appointment, errors);
    }

    public void checkValidDate(LocalDate dateAdmission, Errors errors) {
        if (dateAdmission.isAfter(LocalDate.now().plusDays(7))) {
            errors.rejectValue("dateAdmission", "",
                    "Максимальный срок записи - 7 дней вперед. Выберите дату до "
                            + LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
    }

    public void checkValidTime(LocalTime timeAdmission, Errors errors) {
        if (timeAdmission.isBefore(LocalTime.of(9, 0))
                || timeAdmission.isAfter(LocalTime.of(20,0))) {
            errors.rejectValue("timeAdmission","","Время приема должно быть с 9:00 по 20:00");
        }
    }

    public void checkUniqueForAppointment(Appointment appointment, Errors errors) {
        LocalDate dateAdmission = appointment.getDateAdmission();
        LocalTime timeAdmission = appointment.getTimeAdmission();
        Integer veterinarianId = appointment.getVeterinarian().getVeterinarian_id();
        Appointment existAppointment = appointmentDAO.getAppointmentByDateTimeAndVeterinarian(dateAdmission, timeAdmission, veterinarianId);
        if (existAppointment != null && !Objects.equals(appointment.getAppointment_id(), existAppointment.getAppointment_id())) {
            errors.rejectValue("veterinarian", "", "У ветеринара уже есть прием на это время и дату");
        }
    }
}
