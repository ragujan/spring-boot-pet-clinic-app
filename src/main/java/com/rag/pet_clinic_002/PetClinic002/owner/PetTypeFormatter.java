package com.rag.pet_clinic_002.PetClinic002.owner;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.format.Formatter;

public class PetTypeFormatter implements Formatter<PetType>{

    private final OwnerRepository owners;

    public PetTypeFormatter(OwnerRepository owners){
        this.owners = owners;
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public PetType parse(String text , Locale locale) throws ParseException {
        Collection<PetType> findPetTypes = this.owners.getPetTypes();
        for (PetType petType : findPetTypes) {
            if(petType.getName().equals(text)){
                return petType;
            }
        }
        return null;
    }
    
}
