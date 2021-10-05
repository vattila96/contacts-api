package hu.futureofmedia.task.contactsapi.repositories;

import hu.futureofmedia.task.contactsapi.entities.Company;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CompanyRepository extends Repository<Company, Long> {
    List<Company> findAll();
}
