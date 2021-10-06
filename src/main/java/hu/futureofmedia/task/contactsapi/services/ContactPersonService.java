package hu.futureofmedia.task.contactsapi.services;

import hu.futureofmedia.task.contactsapi.common.Status;
import hu.futureofmedia.task.contactsapi.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.repositories.ContactPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactPersonService {

    private final ContactPersonRepository contactPersonRepository;

    public List<ContactPerson> getContactPersons(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("lastName"));

        return contactPersonRepository.findAllByStatus(Status.ACTIVE, pageable);
    }
}
