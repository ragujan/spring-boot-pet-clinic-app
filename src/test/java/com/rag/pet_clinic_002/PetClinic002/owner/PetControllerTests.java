package com.rag.pet_clinic_002.PetClinic002.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.assertj.core.util.Lists;

@WebMvcTest(value = PetController.class, includeFilters = @ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE))
@DisabledInNativeImage
@DisabledInAotMode
public class PetControllerTests {
    private static final int TEST_OWNER_ID = 1;
    private static final int TEST_PED_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository owners;

    @BeforeEach
    void setup() {
        PetType cat = new PetType();
        cat.setId(3);
        cat.setName("hamster");
        given(this.owners.getPetTypes()).willReturn(Lists.newArrayList(cat));
        Owner owner = new Owner();
        Pet pet = new Pet();
        owner.addpet(pet);
        pet.setId(TEST_PED_ID);
        given(this.owners.findById(TEST_OWNER_ID)).willReturn(owner);
    }

    @Test
    void testInitFormCreation() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("pet"));

    }

    @Test
    void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
                .param("name", "Betty")
                .param("type", "hamster")
                .param("birthDate", "2015-02-12"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
                .param("name", "Betty")
                .param("birthDate", "2015-02-12"))
                .andExpect(model().attributeHasNoErrors("owner"))
                .andExpect(model().attributeHasErrors("pet"))
                .andExpect(model().attributeHasFieldErrors("pet", "type"))
                .andExpect(model().attributeHasFieldErrorCode("pet", "type", "required"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"));

        ;
    }
}
