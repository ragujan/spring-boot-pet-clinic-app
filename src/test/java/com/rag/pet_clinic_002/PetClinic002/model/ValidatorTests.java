package com.rag.pet_clinic_002.PetClinic002.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.rag.pet_clinic_002.PetClinic002.base_model.Person;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

public class ValidatorTests {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    void shouldNotValidateWhenFirstNameEmpty() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);

        Person person = new Person();
        person.setFirstName("");
        person.setLastName("ragbag");
        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolation = validator.validate(person);

        assertThat(constraintViolation).hasSize(1);
        ConstraintViolation<Person> violation = constraintViolation.iterator().next();
        assertThat(violation.getPropertyPath()).hasToString("firstName");
        assertThat(violation.getMessage()).isEqualTo("must not be blank");

    }

    @Test
    void shouldNotValidateWhenFirstNameEmptyAndLastName() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);

        Person person = new Person();
        person.setFirstName("");
        person.setLastName("");
        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolation = validator.validate(person);

        Iterator<ConstraintViolation<Person>> iterator = constraintViolation.iterator();

        ConstraintViolation<Person> violation1 = iterator.next();
        assertThat(violation1.getPropertyPath().toString()).isEqualTo("lastName");
        assertThat(violation1.getMessage()).isEqualTo("must not be blank");

        ConstraintViolation<Person> violation2 = iterator.next();
        assertThat(violation2.getPropertyPath().toString()).isEqualTo("firstName");
        assertThat(violation2.getMessage()).isEqualTo("must not be blank");

    }
}
