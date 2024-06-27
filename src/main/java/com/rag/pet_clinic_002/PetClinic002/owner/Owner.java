package com.rag.pet_clinic_002.PetClinic002.owner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;

import com.rag.pet_clinic_002.PetClinic002.base_model.Person;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "owners")
public class Owner extends Person {
	@Column(name = "address")
	@NotBlank
	private String address;

	@Column(name = "city")
	@NotBlank
	private String city;

	@Column(name = "telephone")
	@NotBlank
	@Pattern(regexp = "\\d{10}", message = "Telephone must be a 10 digit number")
	private String telephone;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	@OrderBy("name")
	private List<Pet> pets = new ArrayList<>();

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this).append("id", this.getId())
			.append("new", this.isNew())
			.append("lastName", this.getLastName())
			.append("firstName", this.getFirstName())
			.append("address", this.address)
			.append("city", this.city)
			.append("telephone", this.telephone)
			.toString();
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
	public Pet getPetById(Integer id){
        for (Pet pet : pets) {
			 if(!pet.isNew()){
				Integer compId = pet.getId();
				if(compId.equals(id)){
					return pet;
				}
			 }
		}
		return null;
	}

	public Pet getPetByName(String name, boolean ignoreNew){
		name = name.toLowerCase();
		for (Pet pet : pets) {
			 String compName = pet.getName();
             if(compName != null && compName.equalsIgnoreCase(name)){
				if(!ignoreNew || !pet.isNew()){
					return pet;
				}
			 }
		}
		return null;
	}

	public void addpet(Pet pet){
		if(pet.isNew()){
			getPets().add(pet);
		}
	}

}
