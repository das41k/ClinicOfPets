package ru.petrosyan.clinicOfPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petrosyan.clinicOfPet.dao.PetDAO;
import ru.petrosyan.clinicOfPet.model.Pet;
import java.util.List;

@Controller
@RequestMapping("/pet")
public class PetController {

    private final PetDAO petDAO;

    @Autowired
    public PetController(PetDAO petDAO) {
        this.petDAO = petDAO;
    }

    @GetMapping
    public String getAllPets(Model model) {
        List<Pet> pets = petDAO.getAllPets();
        model.addAttribute("pets", pets);
        return "pet/allPets";
    }

    @GetMapping("/{id}")
    public String getPetById(@PathVariable("id") Integer petId, Model model, RedirectAttributes redirectAttributes) {
        Pet pet = petDAO.getPetById(petId);
        if (pet != null) {
            model.addAttribute("pet", pet);
            return "pet/pet";
        }
        redirectAttributes.addFlashAttribute("error", "Питомец не был найден в системе! Возможно он удален.");
        return "redirect:/pet";
    }
}
