package com.rag.pet_clinic_002.PetClinic002.owner;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("owners/{ownerId}")
public class PetController {
    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final OwnerRepository owners;

    public PetController(OwnerRepository owners) {
        this.owners = owners;
    }

    // Bind an PetType instance
    @ModelAttribute("petTypes")
    public Collection<PetType> populatePetTypes() {
        System.out.println("Pet type instance ");
        return this.owners.getPetTypes();
    }

    // bind an owner instance
    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") int ownerId) {
        Owner owner = this.owners.findById(ownerId);
        System.out.println("Binding Owner Instance ");
        System.out.println("owner id " + ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Owner Id not found " + ownerId);
        }
        return owner;
    }

    // get the owner id, get the pet, bind to a Pet instance
    @ModelAttribute("pet")
    public Pet findPet(@PathVariable("ownerId") int ownerId,
            @PathVariable(name = "petId", required = false) Integer petId) {
        System.out.println("Binding Pet Instance");
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
        System.out.println("Init binder for owner binding");
        System.out.println("----------");
        System.out.println("----------");
        System.out.println("----------");
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder("pet")
    public void initPetBinder(WebDataBinder dataBinder) {
        System.out.println("Init Binder for Pet");
        System.out.println("----------");
        System.out.println("----------");
        System.out.println("----------");
        dataBinder.setValidator(new PetValidator());
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, ModelMap model) {
        Pet pet = new Pet();
        owner.addpet(pet);
        model.put("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    // public String processPetCreationForm(@Valid Pet pet,@PathVariable Integer
    // ownerId) {
    public String processCreationForm(Owner owner, @Valid Pet pet,BindingResult bindingResult,
            RedirectAttributes redirectAttributes, ModelMap modelMap) {
        System.out.println("pet " + pet.getName());
        System.out.println("pet bdate" + pet.getBirthDate());
        System.out.println("owner " + owner.getLastName());
        owner.addpet(pet);
        modelMap.put("pet", pet);
        modelMap.put("owner", owner);
        // process to check duplicate name
        if(StringUtils.hasText(pet.getName()) 
        && pet.isNew() 
        && owner.getPetByName(pet.getName(), true) !=null ){
            bindingResult.rejectValue("name","duplicate","already exists");
        }
        // process to check if the birthdate is valid
        LocalDate currentDate = LocalDate.now();
        if(pet.getBirthDate()!=null && pet.getBirthDate().isAfter(currentDate)){
            bindingResult.rejectValue("birthDate","Type mis match.birthDate");
        }
        owner.addpet(pet);
        // if there is errors in binding results 
        if(bindingResult.hasErrors()){
            modelMap.put("pet",pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;

        }
        this.owners.save(owner);
        redirectAttributes.addFlashAttribute("message","New Pet has been added ");
        return "redirect:/owners/{ownerId}";

    }

}
