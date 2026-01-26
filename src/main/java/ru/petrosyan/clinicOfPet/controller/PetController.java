package ru.petrosyan.clinicOfPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
