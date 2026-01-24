package ru.petrosyan.clinicOfPet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petrosyan.clinicOfPet.dao.VeterinarianDAO;
import ru.petrosyan.clinicOfPet.model.Veterinarian;
import ru.petrosyan.clinicOfPet.utils.VeterinarianValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/veterinarian")
public class VeterinarianController {

    private final VeterinarianDAO veterinarianDAO;
    private final VeterinarianValidator veterinarianValidator;

    @Autowired
    public VeterinarianController(VeterinarianDAO veterinarianDAO, VeterinarianValidator veterinarianValidator) {
        this.veterinarianDAO = veterinarianDAO;
        this.veterinarianValidator = veterinarianValidator;
    }

    @GetMapping
    public String getAllVeterinarians(Model model) {
        List<Veterinarian> veterinarians = veterinarianDAO.getAllVeterinarians();
        model.addAttribute("veterinarians", veterinarians);
        return "veterinarian/allVeterinarians";
    }

    @GetMapping("/{id}")
    public String getVeterinarianById(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Veterinarian veterinarian = veterinarianDAO.getVeterinarianById(id);
        if (veterinarian != null) {
            model.addAttribute("veterinarian", veterinarian);
            return "veterinarian/veterinarian";
        }
        redirectAttributes.addFlashAttribute("error", "Ветеринар не был найден в системе. Возможно он был удален!");
        return "redirect:/veterinarian";
    }

    @GetMapping("/new")
    public String getFormInsert(Model model) {
        model.addAttribute("veterinarian", new Veterinarian());
        return "veterinarian/newVeterinarian";
    }

    @PostMapping
    public String insertVeterinarian(@ModelAttribute("veterinarian") @Valid Veterinarian veterinarian, BindingResult bindingResult) {
        veterinarianValidator.validate(veterinarian, bindingResult);
        if (bindingResult.hasErrors()) {
            return "veterinarian/newVeterinarian";
        }
        veterinarianDAO.insertVeterinarian(veterinarian);
        return "redirect:/veterinarian";
    }

    @GetMapping("/{id}/edit")
    public String getFormUpdate(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Veterinarian veterinarian = veterinarianDAO.getVeterinarianById(id);
        if (veterinarian != null) {
            model.addAttribute("veterinarian", veterinarian);
            return "veterinarian/editVeterinarian";
        }
        redirectAttributes.addFlashAttribute("error", "Данный ветеринар на найден. Возможно он был удален!");
        return "redirect:/veterinarian";
    }

    @PatchMapping("/{id}")
    public String updateVeterinarian(@ModelAttribute @Valid Veterinarian veterinarian, BindingResult bindingResult,
                                     @PathVariable("id") Integer id) {
        veterinarian.setVeterinarian_id(id);
        veterinarianValidator.validate(veterinarian, bindingResult);
        if (bindingResult.hasErrors()) {
            return "veterinarian/editVeterinarian";
        }
        veterinarianDAO.updateVeterinarian(veterinarian);
        return "redirect:/veterinarian";
    }

    @DeleteMapping("/{id}")
    public String deleteVeterinarianById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        int row = veterinarianDAO.deleteVeterinarianById(id);
        if (row > 0) {
            redirectAttributes.addFlashAttribute("info", "Ветеринар был успешно удален!");
        }  else {
            redirectAttributes.addFlashAttribute("error", "Ветеринар не был найден в системе. Возможно он был удален!");
        }
        return "redirect:/veterinarian";
    }
}
