package com.kaiyee0.reflection;

import com.kaiyee0.reflection.entity.Person;
import com.kaiyee0.reflection.exception.PersonException;
import com.kaiyee0.reflection.service.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ReflectionApplicationTests {
    @InjectMocks
    private PersonService personService;

    @Test
    void test_Success() {
        Person person = new Person("kay", 25);
        String jsonString = personService.getValidatedPersonAsJson(person);
        // --> {"name":"kay","age":"25"}
        Assertions.assertEquals("{\"name\":\"kay\",\"age\":\"25\"}", jsonString);
    }

    @Test
    void test_Fail_NumberNegativeThenThrow() {
        Person person = new Person("kay", -10);
        // com.kaiyee0.reflection.exception.PersonException: Field age value -10 is not positive
        Assertions.assertThrows(PersonException.class, () -> personService.getValidatedPersonAsJson(person));
    }
}
