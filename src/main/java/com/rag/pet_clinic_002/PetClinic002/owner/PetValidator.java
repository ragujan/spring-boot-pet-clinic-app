package com.rag.pet_clinic_002.PetClinic002.owner;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PetValidator implements Validator {
    private static final String REQUIRED = "required";

    @Override
    public boolean supports(Class<?> clazz) {
        return Pet.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pet pet = (Pet) target;
        String name = pet.getName();

        if (!StringUtils.hasText(name)) {
            errors.rejectValue("name", REQUIRED, REQUIRED);
        }
        if (pet.isNew() && pet.getType() == null) {
            errors.rejectValue("type",REQUIRED,REQUIRED);
        }
        if(pet.getBirthDate()==null){
            errors.rejectValue("birthDate",REQUIRED,REQUIRED);
        }
    }

}
