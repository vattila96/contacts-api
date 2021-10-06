package hu.futureofmedia.task.contactsapi.dtos;

import lombok.Data;

@Data
public class ContactPersonRequest {
    private String lastName;
    private String firstName;
    private Integer companyId;
    private String email;
    private String phoneNumber;
    private String comment;
}
