package ru.petrosyan.clinicOfPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petrosyan.clinicOfPet.dao.AppointmentDAO;
import ru.petrosyan.clinicOfPet.dao.PetDAO;
import ru.petrosyan.clinicOfPet.dao.VeterinarianDAO;
import ru.petrosyan.clinicOfPet.model.Appointment;
import ru.petrosyan.clinicOfPet.model.Pet;
import ru.petrosyan.clinicOfPet.model.Veterinarian;
import ru.petrosyan.clinicOfPet.utils.AppointmentValidator;

import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentDAO appointmentDAO;
    private final AppointmentValidator appointmentValidator;
    private final VeterinarianDAO veterinarianDAO;
    private final PetDAO petDAO;

    @Autowired
    public AppointmentController(AppointmentDAO appointmentDAO, AppointmentValidator appointmentValidator, VeterinarianDAO veterinarianDAO, PetDAO petDAO) {
        this.appointmentDAO = appointmentDAO;
        this.appointmentValidator = appointmentValidator;
        this.veterinarianDAO = veterinarianDAO;
        this.petDAO = petDAO;
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

    @GetMapping("/new")
    public String getFormInsert(Model model) {
        model.addAttribute("appointment", new Appointment());
        getDetailsForm(model);
        return "appointment/addAppointment";
    }

    public void getDetailsForm(Model model) {
        List<Veterinarian> veterinarians = veterinarianDAO.getAllVeterinarians();
        List<Pet> pets = petDAO.getAllPets();
        model.addAttribute("veterinarians", veterinarians);
        model.addAttribute("pets", pets);
    }
}
