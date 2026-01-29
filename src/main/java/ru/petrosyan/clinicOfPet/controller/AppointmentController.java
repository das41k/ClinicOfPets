package ru.petrosyan.clinicOfPet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @GetMapping("/{id}")
    public String getAppointmentById(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Appointment appointment = appointmentDAO.getByIdAppointment(id);
        if (appointment != null) {
            model.addAttribute("appointment", appointment);
            return "appointment/appointment";
        }
        redirectAttributes.addFlashAttribute("error", "Данный прием не найден! Возможно он был удален!");
        return "redirect:/appointment";
    }
}
