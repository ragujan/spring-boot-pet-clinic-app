package com.rag.pet_clinic_002.PetClinic002.vet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "vets")
public class Vet {

    // Many Vets and Many Specialitis relationship
    // Vet A: Spe X, Spe Y, Spe L
    // Vet B: Spe X, Spe L

    // Spe X: Vet A, Vet B
    // Spe Y: Vet A
    // Spe L: Vet B, Vet A
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_specialities", joinColumns = @JoinColumn(name = "vet_id"), inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialities;

    protected Set<Speciality> getSpecialitiesInternal() {
        if (this.specialities == null) {
            return this.specialities = new HashSet<>();
        }
        return this.specialities;
    }

    protected void setSpecialitiesInternal(Set<Speciality> specialities) {
        this.specialities = specialities;
    }

    @XmlElement
    public List<Speciality> getSpecialities() {
        List<Speciality> sortedSpecs = new ArrayList<>(getSpecialitiesInternal());
        PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedSpecs);
    }
    public int getNoOfSpecialities(){
        return getSpecialitiesInternal().size();
    }

    public void addSpeciality(Speciality speciality){
        getSpecialitiesInternal().add(speciality);
    }


}
