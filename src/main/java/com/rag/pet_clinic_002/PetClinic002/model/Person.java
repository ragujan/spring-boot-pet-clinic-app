package com.rag.pet_clinic_002.PetClinic002.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;

@MappedSuperclass
public class Person {

	@Column(name= "first_name")
	@NotBlank
	private String firstName;
	
	@Column(name= "last_name")
	@NotBlank
	private String lastName;
	
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
