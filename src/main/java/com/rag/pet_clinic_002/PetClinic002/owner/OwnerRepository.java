package com.rag.pet_clinic_002.PetClinic002.owner;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OwnerRepository extends Repository<Owner, Integer> {
  void save(Owner owner);

  @Query("SELECT DISTINCT owner FROM Owner owner left JOIN owner.pets WHERE owner.lastName LIKE:lastName%")
  @Transactional(readOnly = false)
  Page<Owner> findByLastName(@Param("lastName") String lastName, Pageable pageable);

  @Query("SELECT owner FROM Owner owner left join  fetch owner.pets WHERE owner.id =:id")
  @Transactional(readOnly = true)
  Owner findById(@Param("id") Integer id);

  @Query("SELECT owner FROM Owner owner")
  @Transactional(readOnly = true)
  Page<Owner> fildAll(Pageable pageable);

  // retrieve pet type

  @Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
  @Transactional(readOnly = true)
  List<PetType> getPetTypes();

}
