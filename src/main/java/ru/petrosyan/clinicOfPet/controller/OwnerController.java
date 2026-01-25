package ru.petrosyan.clinicOfPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petrosyan.clinicOfPet.dao.OwnerDAO;
import ru.petrosyan.clinicOfPet.model.Owner;

import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerDAO ownerDAO;

    @Autowired
    public OwnerController(OwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }

    @GetMapping
    public String getAllOwners(Model model) {
        List<Owner> owners = ownerDAO.getAllOwners();
        model.addAttribute("owners", owners);
        return "owner/allOwners";
    }

    @GetMapping("/{id}")
    public String getOwnerById(@PathVariable("id") Integer ownerId, Model model, RedirectAttributes redirectAttributes) {
        Owner owner = ownerDAO.getOwnerById(ownerId);
        if (owner != null) {
            model.addAttribute("owner", owner);
            return "owner/owner";
        }
        redirectAttributes.addFlashAttribute("error", "Владелец не был найден в системе. Возможно он был удален!");
        return "redirect:/owner";
    }
}
