package hu.futureofmedia.task.contactsapi.mappers;

import hu.futureofmedia.task.contactsapi.dtos.ContactPersonRequest;
import hu.futureofmedia.task.contactsapi.entities.ContactPerson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactPersonMapper {
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "status", ignore = true)
    ContactPerson contactPersonRequestToEntity(ContactPersonRequest contactPersonRequest);
}
