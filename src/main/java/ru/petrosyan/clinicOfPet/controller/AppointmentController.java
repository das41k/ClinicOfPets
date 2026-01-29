package ru.petrosyan.clinicOfPet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.petrosyan.clinicOfPet.dao.AppointmentDAO;
import ru.petrosyan.clinicOfPet.model.Appointment;
import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentDAO appointmentDAO;

    public AppointmentController(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @GetMapping
    public String getAllAppointments(Model model) {
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointment/allAppointments";
    }
}
