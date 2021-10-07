package hu.futureofmedia.task.contactsapi;

import hu.futureofmedia.task.contactsapi.common.ResourceCreationException;
import hu.futureofmedia.task.contactsapi.common.Status;
import hu.futureofmedia.task.contactsapi.dtos.ContactPersonRequest;
import hu.futureofmedia.task.contactsapi.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.mappers.ContactPersonMapper;
import hu.futureofmedia.task.contactsapi.repositories.ContactPersonRepository;
import hu.futureofmedia.task.contactsapi.services.ContactPersonService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ServiceTests {

    @Autowired
    private ContactPersonService contactPersonService;

    @MockBean
    private ContactPersonRepository contactPersonRepository;

    ContactPerson person1, person2, person3;

    @BeforeEach
    public void setUp() {
        person1 = new ContactPerson();
        person1.setId(321L);
        person1.setLastName("Bela");
        person1.setStatus(Status.ACTIVE);

        person2 = new ContactPerson();
        person2.setId(423L);
        person2.setLastName("Attila");
        person2.setStatus(Status.ACTIVE);

        person3 = new ContactPerson();
        person3.setId(540L);
        person3.setLastName("Denes");
        person3.setStatus(Status.ACTIVE);
    }

    @Test
    public void getContactPersonsTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("lastName"));
        when(contactPersonRepository.findAllByStatus(Status.ACTIVE, pageable)).thenReturn(Stream
            .of(person1, person2, person3).collect(Collectors.toList()));
        Assertions.assertEquals(3, contactPersonService.getContactPersons(0).size());
    }

    @Test
    public void getContactPersonById() {
        when(contactPersonRepository.findById(540L)).thenReturn(java.util.Optional.ofNullable(person3));
        Assertions.assertEquals("Denes", contactPersonService.getContactPerson(540L).getLastName());
    }

    @Test
    public void deleteContactPerson() {
        when(contactPersonRepository.findById(540L)).thenReturn(java.util.Optional.ofNullable(person3));
        contactPersonService.deleteContactPerson(540L);
        Assertions.assertEquals(Status.DELETED, person3.getStatus());
    }

    @Test
    public void createContactPerson() {
        ContactPersonRequest contactPersonRequest = new ContactPersonRequest();
        Assert.assertThrows(ResourceCreationException.class, () -> contactPersonService.createContactPerson(contactPersonRequest));
    }
}
