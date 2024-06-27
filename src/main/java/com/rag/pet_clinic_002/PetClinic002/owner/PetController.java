package com.rag.pet_clinic_002.PetClinic002.owner;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("owners/{ownerId}")
public class PetController {
    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final OwnerRepository owners;

    public PetController(OwnerRepository owners) {
        this.owners = owners;
    }

    @ModelAttribute("petTypes")
    public Collection<PetType> populatePetTypes() {
        return this.owners.getPetTypes();
    }

    // bind an owner instance
    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") int ownerId) {
        Owner owner = this.owners.findById(ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Owner Id not found " + ownerId);
        }
        return owner;
    }

    // get the owner id, get the pet, bind to a Pet instance
    @ModelAttribute("pet")
    public Pet findPet(@PathVariable("ownerId") int ownerId,
            @PathVariable(name = "petId", required = false) Integer petId) {
        if (petId == null) {
            return new Pet();
        }

        Owner owner = this.owners.findById(ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Owner ID not found " + ownerId);
        }
        return owner.getPetById(petId);
    }

    //
    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder("pet")
    public void initPetBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new PetValidator());
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, ModelMap model) {
        Pet pet = new Pet();
        owner.addpet(pet);
        model.put("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }
    

}
