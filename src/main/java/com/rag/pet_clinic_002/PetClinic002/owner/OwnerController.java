package com.rag.pet_clinic_002.PetClinic002.owner;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class OwnerController {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private static final String OWNER_CREATE_SUCCEESS_PAGE = "owners/ownerCreateSuccessPage";
    private final OwnerRepository owners;

    public OwnerController(OwnerRepository owners) {
        this.owners = owners;
    }

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

    @GetMapping("/owners/new")
    public String initCreationForm(Map<String, Object> model) {
        Owner owner = new Owner();
        model.put("owner", owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }
    @PostMapping("/owners/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result, RedirectAttributes redirectAttributes) {
        //TODO: process POST request
          
        if(result.hasErrors()){
        	System.out.println("result error ");
            redirectAttributes.addFlashAttribute("error","There was an errr in creating the owner");
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }
        this.owners.save(owner);
        Integer id = owner.getId();
        redirectAttributes.addFlashAttribute("message", "New Owner Created");
        // return "owners/"+id;
        return "redirect:/owners/" + owner.getId();
    }

    @GetMapping("/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails") ;
        Owner owner = this.owners.findById(ownerId);
        mav.addObject(owner);
        return  mav;
    }
    

}
