package ru.petrosyan.clinicOfPet.model;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private Integer appointment_id;

    @NotBlank(message = "Дата приема не может быть пустой!")
    private LocalDate dateAdmission;

    @NotBlank(message = "Время приема не может быть пустым!")
    private LocalTime timeAdmission;

    private Veterinarian veterinarian;
    private Pet pet;
    private AppointmentStatus status;
}
