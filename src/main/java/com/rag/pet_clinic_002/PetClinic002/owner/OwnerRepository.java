package com.rag.pet_clinic_002.PetClinic002.owner;
import org.springframework.data.repository.Repository;
public interface OwnerRepository  extends Repository<Owner, Integer>{
  void save(Owner owner);
}
