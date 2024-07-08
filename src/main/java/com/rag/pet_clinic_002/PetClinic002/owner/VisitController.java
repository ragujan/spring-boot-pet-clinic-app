package com.rag.pet_clinic_002.PetClinic002.owner;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class VisitController {
    private final OwnerRepository ownerRepository;
    public VisitController(OwnerRepository owners){
        this.ownerRepository = owners;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("ownerId") int ownerId, @PathVariable("petId" )int petId, Map<String,Object> model){
        Owner owner = this.ownerRepository.findById(ownerId);
        Pet pet = owner.getPetById(petId);

        model.put("pet", pet);
        model.put("owner", owner);
        Visit visit = new Visit();
        pet.addVisit(visit);
        return visit;
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String initNewVisitForm() {
        return "pets/createOrUpdateVisitForm";
    }
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisit(@ModelAttribute Owner owner, @PathVariable int petId, @Valid Visit visit,BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            return "pets/createOrUpdateVisitForm";
        }
        System.out.println("received date is "+visit.getDate());
        owner.addVisit(petId, visit);
        this.ownerRepository.save(owner);
        redirectAttributes.addFlashAttribute("message", "Your visit has been booked");
        // return null;
        return "redirect:/owners/{ownerId}";
    }
    

}
