package com.example.lab02MPR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {

    @GetMapping(value = "/welcome")
    public String getWelcomeView() {return "welcome";}

    private final MyRestService service;

    @Autowired
    public  WebController(MyRestService dogService) {this.service = dogService;}


    @GetMapping(value = "/index")
    public String getIndexView(Model model) {
        model.addAttribute("dog", service.findAll());
        return "index";
    }

    @GetMapping(value = "/addDog")
    public String getAddView(Model model){
        model.addAttribute("dog", new Dog("",""));
        return "addDog";
    }

    @PostMapping(value = "/addDog")
    public String addDog(Dog dog, Model model, RedirectAttributes redirectAttributes){
        if(dog.getBreed().isEmpty() || dog.getName().isEmpty()){
            model.addAttribute("errorMessage", "Breed and Name can't be empty");
            return "addDog";
        }
        service.addDog(dog);
        redirectAttributes.addFlashAttribute("SuccessMessage", "Dog created");
        return "redirect:/index";
    }

    @GetMapping(value = "/editDog/{id}")
    public String getEditView(@PathVariable("id") Long id, Model model){
        Dog dog = service.findDogById(id);
        if(service.findDogById(id) != null){
            model.addAttribute("dog",dog);
            return "editDog";
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping(value = "/editDog")
    public String editDog(Dog dog, Model model,RedirectAttributes redirectAttributes){
        if (dog.getBreed().isEmpty() || dog.getName().isEmpty()) {
            model.addAttribute("errorMessage", "Breed and Name can't be empty");
            return "editDog";
        }
        service.replaceDog(dog, dog.getId());
        redirectAttributes.addFlashAttribute("SuccessMessage", "Dog edited successfully");
        return "redirect:/index";
    }
    @GetMapping(value = "/deleteDog/{id}")
    public String deleteDog(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if(service.findDogById(id) != null){
            service.deleteDog(id);
            redirectAttributes.addFlashAttribute("successMessage", "Dog deleted successfully");
        }
        else redirectAttributes.addFlashAttribute("errorMessage", "Dog not found");
        return "redirect:/index";
    }
}
