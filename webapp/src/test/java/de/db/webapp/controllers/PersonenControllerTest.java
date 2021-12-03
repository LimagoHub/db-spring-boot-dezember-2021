package de.db.webapp.controllers;

import de.db.webapp.controllers.dtos.PersonDTO;
import de.db.webapp.repositories.PersonenRepository;
import de.db.webapp.services.PersonenService;
import de.db.webapp.services.models.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql({"classpath:/create.sql", "classpath:/insert.sql"})
@ExtendWith(SpringExtension.class)
class PersonenControllerTest {
    
    @Autowired
    private TestRestTemplate resttemplate;

    @MockBean
    private PersonenService serviceMock;

//    @MockBean
//    private PersonenRepository repoMock;

    private final String validUUID = "b2e24e74-8686-43ea-baff-d9396b4202e0";
    private final PersonDTO validPersonDTO = PersonDTO.builder().id(validUUID).vorname("John").nachname("Doe").build();


    @Test
    void test1() throws Exception {
        when(serviceMock.findePersonNachId(anyString())).thenReturn(Optional.of(Person.builder().id("123").vorname("John").nachname("Doe").build()));

        PersonDTO dto = resttemplate.getForObject("/v1/personen/86dac2d5-7edc-483a-abc6-239e5b93eb13", PersonDTO.class);
        assertEquals("John", dto.getVorname());

    }
    @Test
    void test2() throws Exception {
        String dto = resttemplate.getForObject("/v1/personen/86dac2d5-7edc-483a-abc6-239e5b93eb13", String.class);
        System.out.println(dto);

    }

    @Test
    void test3() throws Exception {

        when(serviceMock.findePersonNachId(anyString())).thenReturn(Optional.of(Person.builder().id("123").vorname("John").nachname("Doe").build()));
        ResponseEntity<PersonDTO> entity = resttemplate.getForEntity("/v1/personen/86dac2d5-7edc-483a-abc6-239e5b93eb13", PersonDTO.class);
        PersonDTO dto = entity.getBody();

        assertEquals("John", dto.getVorname());
        assertEquals(HttpStatus.OK,entity.getStatusCode());

    }
    @Test
    void test4() throws Exception {

        when(serviceMock.findePersonNachId(anyString())).thenReturn(Optional.empty());

        ResponseEntity<PersonDTO> entity = resttemplate.getForEntity("/v1/personen/86dac2d5-7edc-483a-abc6-239e5b93eb20", PersonDTO.class);



        PersonDTO dto = entity.getBody();


        assertEquals(HttpStatus.NOT_FOUND,entity.getStatusCode());

    }

    @Test
    void test5() throws Exception {

        when(serviceMock.findePersonNachId(anyString())).thenReturn(Optional.empty());

        ResponseEntity<PersonDTO> entity = resttemplate.exchange("/v1/personen/86dac2d5-7edc-483a-abc6-239e5b93eb20", HttpMethod.GET,null, PersonDTO.class);



        PersonDTO dto = entity.getBody();


        assertEquals(HttpStatus.NOT_FOUND,entity.getStatusCode());

    }
    @Test
    void test6() throws Exception {

        // Arrange
        List<Person> personen = List.of(
                Person.builder().id(validUUID).vorname("John").nachname("Doe").build(),
                Person.builder().id(validUUID).vorname("John").nachname("Rambo").build(),
                Person.builder().id(validUUID).vorname("John").nachname("Wayne").build()
        );

        when(serviceMock.findeAlle()).thenReturn(personen);

        // Act
        ResponseEntity<Iterable<PersonDTO>> entity = resttemplate.exchange("/v1/personen", HttpMethod.GET,null,
                new ParameterizedTypeReference<Iterable<PersonDTO>>() {});


        // Assertion
        Iterable<PersonDTO> body = entity.getBody();

        List<PersonDTO> liste = StreamSupport
                .stream(body.spliterator(), false)
                .collect(Collectors.toList());

        assertEquals(3,liste.size());
        for (PersonDTO person: liste ) {
            assertEquals("John",person.getVorname());
        }

    }

    @Test
    void test7() throws Exception {

        Person p = Person.builder().id(validUUID).vorname("John").nachname("Doe").build();
        PersonDTO dto = PersonDTO.builder().id(validUUID).vorname("John").nachname("Doe").build();

        HttpEntity requestEntity = new HttpEntity(dto);

        when(serviceMock.speichernOderAendern(any())).thenReturn(false);

        // Act
        ResponseEntity<Void> entity = resttemplate.exchange("/v1/personen", HttpMethod.PUT,requestEntity, Void.class);


        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        verify(serviceMock, times(1)).speichernOderAendern(p);

    }

}