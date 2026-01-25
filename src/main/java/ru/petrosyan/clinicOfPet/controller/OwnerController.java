package ru.petrosyan.clinicOfPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petrosyan.clinicOfPet.dao.OwnerDAO;
import ru.petrosyan.clinicOfPet.model.Owner;
import ru.petrosyan.clinicOfPet.utils.OwnerValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerDAO ownerDAO;
    private final OwnerValidator ownerValidator;

    @Autowired
    public OwnerController(OwnerDAO ownerDAO, OwnerValidator ownerValidator) {
        this.ownerDAO = ownerDAO;
        this.ownerValidator = ownerValidator;
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

    @GetMapping("/new")
    public String getFormInsert(Model model) {
        model.addAttribute("owner", new Owner());
        return "owner/newOwner";
    }

    @PostMapping
    public String insertOwner(@ModelAttribute("owner") @Valid Owner owner, BindingResult bindingResult) {
        ownerValidator.validate(owner, bindingResult);
        if (bindingResult.hasErrors()) {
            return "owner/newOwner";
        }
        ownerDAO.insertOwner(owner);
        return "redirect:/owner";
    }

    @GetMapping("/{id}/edit")
    public String getFormUpdate(@PathVariable("id") Integer ownerId, Model model, RedirectAttributes redirectAttributes) {
        Owner owner = ownerDAO.getOwnerById(ownerId);
        if (owner != null) {
            model.addAttribute("owner", owner);
            return "/owner/editOwner";
        }
        redirectAttributes.addFlashAttribute("error", "Владелец не был найден в системе. Возможно он был удален!");
        return "redirect:/owner";
    }

    @PatchMapping("/{id}")
    public String updateOwner(@ModelAttribute("owner") @Valid Owner owner, @PathVariable("id") Integer ownerId ,BindingResult bindingResult) {
        owner.setOwner_id(ownerId);
        ownerValidator.validate(owner, bindingResult);
        if (bindingResult.hasErrors()) {
            return "owner/editOwner";
        }
        ownerDAO.updateOwner(owner);
        return "redirect:/owner";
    }

    @DeleteMapping("/{id}")
    public String deleteOwnerById(@PathVariable("id") Integer ownerId, RedirectAttributes redirectAttributes) {
        int row = ownerDAO.deleteOwnerById(ownerId);
        if (row > 0) {
            redirectAttributes.addFlashAttribute("info", "Владелец был успешно удален!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Владелец не был найден в системе. Возможно он был удален!");
        }
        return "redirect:/owner";
    }
}
