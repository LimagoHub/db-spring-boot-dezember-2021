package de.db.webapp.services.impl;

import de.db.webapp.repositories.PersonenRepository;
import de.db.webapp.services.PersonenServiceException;
import de.db.webapp.services.mappers.PersonMapper;
import de.db.webapp.services.models.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {

    @InjectMocks
    private PersonenServiceImpl objectUnderTest;
    @Mock
    private PersonenRepository repoMock;
    @Mock
    private PersonMapper mapperMock;
    @Mock
    private List<String> antipathMock;


    private final Person validPerson = Person.builder().id("012345678901234567890123456789012345").vorname("John").nachname("Doe").build();


    @Test
    void speichernOderAendern_ParameterNull_throwsPersonenService() throws Exception{
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichernOderAendern(null));
        assertEquals("Person darf nicht null sein", ex.getMessage());
    }

    @Test
    void speichernOderAendern_vornameNull_throwsPersonenService() throws Exception{
        final Person p = Person.builder().id("012345678901234567890123456789012345").vorname(null).nachname("Doe").build();

        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichernOderAendern(p));
        assertEquals("Vorname zu kurz", ex.getMessage());
    }

    @Test
    void speichernOderAendern_vornameZUKurzl_throwsPersonenService() throws Exception{
        final Person p = Person.builder().id("012345678901234567890123456789012345").vorname("j").nachname("Doe").build();

        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichernOderAendern(p));
        assertEquals("Vorname zu kurz", ex.getMessage());
    }

    @Test
    void speichernOderAendern_Antipath_throwsPersonenService() throws Exception{
        final Person p = Person.builder().id("012345678901234567890123456789012345").vorname("John").nachname("Doe").build();

        Mockito.when(antipathMock.contains(Mockito.anyString())).thenReturn(true);
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichernOderAendern(p));
        assertEquals("Antipath", ex.getMessage());
    }

}