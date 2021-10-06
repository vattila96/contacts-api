package hu.futureofmedia.task.contactsapi.services;

import hu.futureofmedia.task.contactsapi.common.ResourceNotFoundException;
import hu.futureofmedia.task.contactsapi.common.Status;
import hu.futureofmedia.task.contactsapi.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.repositories.ContactPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactPersonService {

    private final ContactPersonRepository contactPersonRepository;

    public List<ContactPerson> getContactPersons(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("lastName"));

        return contactPersonRepository.findAllByStatus(Status.ACTIVE, pageable);
    }

    public ContactPerson getContactPerson(long id) {
        Optional<ContactPerson> contactPerson = contactPersonRepository.findById(id);

        return contactPerson.orElseThrow(
                () -> new ResourceNotFoundException(ContactPerson.class.getName(), id));
    }

    public void deleteContactPerson(long id){
        Optional<ContactPerson> contactPerson = contactPersonRepository.findById(id);

        if (contactPerson.isPresent()) {
            contactPersonRepository.delete(contactPerson.get());
        } else {
            throw new ResourceNotFoundException(ContactPerson.class.getName(), id);
        }
    }
}
