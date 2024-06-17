package com.rag.pet_clinic_002.PetClinic002.owner;

import org.springframework.core.style.ToStringCreator;

import com.rag.pet_clinic_002.PetClinic002.base_model.Person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

}
