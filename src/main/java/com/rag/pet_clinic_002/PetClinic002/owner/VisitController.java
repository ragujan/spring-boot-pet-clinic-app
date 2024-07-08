package com.rag.pet_clinic_002.PetClinic002.owner;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    

}
