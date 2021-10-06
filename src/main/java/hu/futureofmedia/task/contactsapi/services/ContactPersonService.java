package hu.futureofmedia.task.contactsapi.services;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import hu.futureofmedia.task.contactsapi.common.ResourceCreationException;
import hu.futureofmedia.task.contactsapi.common.ResourceNotFoundException;
import hu.futureofmedia.task.contactsapi.common.Status;
import hu.futureofmedia.task.contactsapi.dtos.ContactPersonRequest;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.mappers.ContactPersonMapper;
import hu.futureofmedia.task.contactsapi.repositories.CompanyRepository;
import hu.futureofmedia.task.contactsapi.repositories.ContactPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ContactPersonService {

    private final ContactPersonRepository contactPersonRepository;
    private final ContactPersonMapper contactPersonMapper;
    private final CompanyRepository companyRepository;

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

    public Long createContactPerson(ContactPersonRequest contactPersonRequest) {
        ContactPerson contactPerson = contactPersonMapper.contactPersonRequestToEntity(contactPersonRequest);
        boolean isRequiredValid = contactPersonRequest.getLastName() != null
                && contactPersonRequest.getFirstName() != null
                && contactPersonRequest.getCompanyId() != null
                && contactPersonRequest.getEmail() != null;

        if (isRequiredValid
                && isEmailValid(contactPersonRequest.getEmail())
                && isPhoneNumberValid(contactPerson.getPhoneNumber())
                && isCompanyValid(contactPersonRequest.getCompanyId())) {
            Company company = companyRepository.findById(contactPersonRequest.getCompanyId());
            contactPerson.setCompany(company);
            contactPerson.setStatus(Status.ACTIVE);
            contactPersonRepository.save(contactPerson);

            return contactPerson.getId();
        } else {
            throw new ResourceCreationException(ContactPerson.class.getName());
        }
    }

    public boolean isEmailValid(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        return VALID_EMAIL_ADDRESS_REGEX.matcher(email).find();
    }

    public boolean isPhoneNumberValid(Optional<String> phoneNumber) {
        if (phoneNumber.isPresent()) {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            try {
                Phonenumber.PhoneNumber phoneNumberProto = phoneUtil.parse(phoneNumber.get(), "E164");
                return phoneUtil.isValidNumber(phoneNumberProto);
            } catch (NumberParseException e) {
                throw new ResourceCreationException(ContactPerson.class.getName());
            }
        }
        return true;
    }

    public boolean isCompanyValid(long companyId) {
        return companyRepository.findAll().stream().map(Company::getId).anyMatch(x -> x == companyId);
    }

    public Long updateContactPerson(long id, ContactPersonRequest contactPersonRequest) {
        Optional<ContactPerson> contactPerson = contactPersonRepository.findById(id);

        if (contactPerson.isPresent()) {
            ContactPerson updatedContactPerson = contactPerson.get();
            contactPersonMapper.updateContactPersonFromRequest(contactPersonRequest, updatedContactPerson);
            contactPersonRepository.save(updatedContactPerson);

            return updatedContactPerson.getId();
        } else {
            throw new ResourceNotFoundException(ContactPerson.class.getName(), id);
        }
    }
}
