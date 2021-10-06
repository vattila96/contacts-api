package hu.futureofmedia.task.contactsapi.repositories;

import hu.futureofmedia.task.contactsapi.common.Status;
import hu.futureofmedia.task.contactsapi.entities.ContactPerson;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface ContactPersonRepository extends PagingAndSortingRepository<ContactPerson, Long> {
    List<ContactPerson> findAllByStatus(Status status, Pageable pageable);
}
