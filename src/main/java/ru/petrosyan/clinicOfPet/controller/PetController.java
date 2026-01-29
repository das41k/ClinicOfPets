package ru.petrosyan.clinicOfPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petrosyan.clinicOfPet.dao.OwnerDAO;
import ru.petrosyan.clinicOfPet.dao.PetDAO;
import ru.petrosyan.clinicOfPet.model.Pet;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/pet")
public class PetController {

    private final PetDAO petDAO;
    private final OwnerDAO ownerDAO;

    @Autowired
    public PetController(PetDAO petDAO, OwnerDAO ownerDAO) {
        this.petDAO = petDAO;
        this.ownerDAO = ownerDAO;
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

    @GetMapping("/new")
    public String getFormInsert(Model model) {
        model.addAttribute("pet", new Pet());
        getFormDetails(model);
        return "pet/addPet";
    }

    public void getFormDetails(Model model) {
        model.addAttribute("petTypes", petDAO.getAllPetTypes());
        model.addAttribute("owners", ownerDAO.getAllOwners());
    }

    @PostMapping
    public String insertPet(@ModelAttribute("pet") @Valid Pet pet, BindingResult bindingResult,
                            Model model, @RequestParam(value = "ownerId", required = false) Integer ownerId) {
        if (bindingResult.hasErrors()) {
            getFormDetails(model);
            return "pet/addPet";
        }
        pet.setOwner(ownerDAO.getOwnerById(ownerId));
        petDAO.insertPet(pet);
        return "redirect:/pet";
    }

    @GetMapping("/{id}/edit")
    public String getUpdateForm(@PathVariable("id") Integer petId, Model model, RedirectAttributes redirectAttributes) {
        Pet pet = petDAO.getPetById(petId);
        if (pet != null) {
            model.addAttribute("pet", pet);
            getFormDetails(model);
            return "pet/editPet";
        }
        redirectAttributes.addFlashAttribute("error", "Питомец не был найден в системе! Возможно он удален.");
        return "redirect:/pet";
    }

    @PatchMapping("/{id}")
    public String updatePet(@PathVariable("id") Integer petId, @ModelAttribute("pet") @Valid Pet pet,
                            BindingResult bindingResult, Model model,
                            @RequestParam(value = "ownerId", required = false) Integer ownerId) {
        pet.setPet_id(petId);
        if (bindingResult.hasErrors()) {
            getFormDetails(model);
            return "pet/editPet";
        }
        pet.setOwner(ownerDAO.getOwnerById(ownerId));
        petDAO.updatePet(pet);
        return "redirect:/pet";
    }

    @DeleteMapping("/{id}")
    public String deletePetById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        int row = petDAO.deletePetById(id);
        if (row > 0) {
            redirectAttributes.addFlashAttribute("info", "Питомец был успешно удален!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Питомец не был найден в системе! Возможно он удален.");
        }
        return "redirect:/pet";
    }
}
