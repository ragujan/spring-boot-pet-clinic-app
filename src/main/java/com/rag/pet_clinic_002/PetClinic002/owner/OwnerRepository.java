package com.rag.pet_clinic_002.PetClinic002.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


public interface OwnerRepository  extends Repository<Owner, Integer>{
  void save(Owner owner);
  
  
  @Query("SELECT DISTINCT owner FROM Owner owner WHERE owner.lastName LIKE:lastName%")
  @Transactional(readOnly = false)
  Page<Owner> findByLastName(@Param("lastName") String lastName,Pageable pageable);

  @Query("SELECT owner FROM Owner owner WHERE owner.id =:id")
  @Transactional(readOnly = true)
  Owner findById(@Param("id") Integer id);

}
