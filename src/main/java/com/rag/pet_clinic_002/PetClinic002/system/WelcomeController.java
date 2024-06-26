package com.rag.pet_clinic_002.PetClinic002.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
class WelcomeController {

	@GetMapping("/")
	public String welcome() {
		return "welcome";
		// return "owners/createOrUpdateOwnerForm";
	}

}