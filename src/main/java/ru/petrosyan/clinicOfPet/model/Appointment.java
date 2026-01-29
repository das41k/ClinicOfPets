package ru.petrosyan.clinicOfPet.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private Integer appointment_id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата приема не может быть пустой!")
    @FutureOrPresent(message = "Дата не может быть позже сегодняшней!")
    private LocalDate dateAdmission;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "Дата не может быть в прошлом!")
    private LocalTime timeAdmission;

    private Veterinarian veterinarian;
    private Pet pet;
    private AppointmentStatus status;

    public Appointment() {}

    public Appointment(Integer appointment_id, LocalDate dateAdmission, LocalTime timeAdmission, Veterinarian veterinarian, Pet pet, AppointmentStatus status) {
        this.appointment_id = appointment_id;
        this.dateAdmission = dateAdmission;
        this.timeAdmission = timeAdmission;
        this.veterinarian = veterinarian;
        this.pet = pet;
        this.status = status;
    }

    public Integer getAppointment_id() {
        return appointment_id;
    }

    public LocalDate getDateAdmission() {
        return dateAdmission;
    }

    public LocalTime getTimeAdmission() {
        return timeAdmission;
    }

    public Veterinarian getVeterinarian() {
        return veterinarian;
    }

    public Pet getPet() {
        return pet;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setAppointment_id(Integer appointment_id) {
        this.appointment_id = appointment_id;
    }

    public void setDateAdmission(LocalDate dateAdmission) {
        this.dateAdmission = dateAdmission;
    }

    public void setTimeAdmission(LocalTime timeAdmission) {
        this.timeAdmission = timeAdmission;
    }

    public void setVeterinarian(Veterinarian veterinarian) {
        this.veterinarian = veterinarian;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
