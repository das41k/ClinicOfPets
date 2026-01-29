package ru.petrosyan.clinicOfPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petrosyan.clinicOfPet.dao.AppointmentDAO;
import ru.petrosyan.clinicOfPet.dao.PetDAO;
import ru.petrosyan.clinicOfPet.dao.VeterinarianDAO;
import ru.petrosyan.clinicOfPet.model.Appointment;
import ru.petrosyan.clinicOfPet.model.Pet;
import ru.petrosyan.clinicOfPet.model.Veterinarian;
import ru.petrosyan.clinicOfPet.utils.AppointmentValidator;

import javax.validation.Valid;
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

    @PostMapping
    public String insertAppointment(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult bindingResult, Model model) {
        appointmentValidator.validate(appointment, bindingResult);
        if (bindingResult.hasErrors()) {
            getDetailsForm(model);
            return "appointment/addAppointment";
        }
        appointmentDAO.insertAppointment(appointment);
        return "redirect:/appointment";
    }

    @GetMapping("/{id}/edit")
    public String getFormUpdate(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Appointment appointment = appointmentDAO.getByIdAppointment(id);
        if (appointment != null) {
            model.addAttribute("appointment", appointment);
            getDetailsForm(model);
            return "appointment/editAppointment";
        }
        redirectAttributes.addFlashAttribute("error", "Данный прием не найден! Возможно он был удален!");
        return "redirect:/appointment";
    }

    @PatchMapping("/{id}")
    public String updateAppointment(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult bindingResult,
                                    @PathVariable("id") Integer id, Model model) {
        appointment.setAppointment_id(id);
        appointmentValidator.validate(appointment, bindingResult);
        if (bindingResult.hasErrors()) {
            getDetailsForm(model);
            return "appointment/editAppointment";
        }
        appointmentDAO.updateAppointment(appointment);
        return "redirect:/appointment";
    }

    @DeleteMapping("/{id}")
    public String deleteAppointmentById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        int row = appointmentDAO.deleteAppointmentById(id);
        if (row > 0) {
            redirectAttributes.addFlashAttribute("info", "Прием был успешно удален!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Данный прием не найден! Возможно он был удален!");
        }
        return "redirect:/appointment";
    }
}
