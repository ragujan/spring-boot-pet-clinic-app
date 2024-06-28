package com.rag.pet_clinic_002.PetClinic002.owner;

import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.aot.DisabledInAotMode;

@WebMvcTest(value = PetController.class,includeFilters = @ComponentScan.Filter(value=PetTypeFormatter.class,type = FilterType.ASSIGNABLE_TYPE))
@DisabledInNativeImage
@DisabledInAotMode
public class PetControllerTests {
    
    
}
