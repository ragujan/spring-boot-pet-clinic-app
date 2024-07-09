package com.rag.pet_clinic_002.PetClinic002.vet;

import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;
import static org.assertj.core.api.Assertions.assertThat;

public class VetTests {
    @Test
    void testSerialization() {
        Vet vet = new Vet();
        vet.setFirstName("Zaphod");
        vet.setFirstName("Beeblebrox");
        vet.setId(123);

        @SuppressWarnings("deprecation")
        Vet other = (Vet) SerializationUtils.deserialize(SerializationUtils.serialize(vet));
        assertThat(other.getFirstName()).isEqualTo(vet.getFirstName());
        assertThat(other.getLastName()).isEqualTo(vet.getLastName());
        assertThat(other.getId()).isEqualTo(vet.getId());

    }
}
