package ru.petrosyan.clinicOfPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.petrosyan.clinicOfPet.dao.VeterinarianDAO;
import ru.petrosyan.clinicOfPet.model.Veterinarian;

import java.util.List;

@Controller
@RequestMapping("/veterinarian")
public class VeterinarianController {

    private final VeterinarianDAO veterinarianDAO;

    @Autowired
    public VeterinarianController(VeterinarianDAO veterinarianDAO) {
        this.veterinarianDAO = veterinarianDAO;
    }

    @GetMapping
    public String getAllVeterinarians(Model model) {
        List<Veterinarian> veterinarians = veterinarianDAO.getAllVeterinarians();
        model.addAttribute("veterinarians", veterinarians);
        return "veterinarian/allVeterinarians";
    }
}
