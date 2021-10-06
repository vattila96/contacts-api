package hu.futureofmedia.task.contactsapi.entities;

import hu.futureofmedia.task.contactsapi.common.Status;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Optional;

@Entity
@Getter
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;

    private String firstName;

    @Email
    private String email;

    private String phoneNumber;

    @OneToOne
    private Company company;

    private String comment;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    public Optional<String> getPhoneNumber() { return Optional.ofNullable(phoneNumber); }

    public Optional<String> getComment() { return Optional.ofNullable(comment); }
}

