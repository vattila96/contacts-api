package hu.futureofmedia.task.contactsapi.endpoints;

import hu.futureofmedia.task.contactsapi.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.services.ContactPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contact-person")
@RequiredArgsConstructor
public class ContactPersonEndpoint {

    private final ContactPersonService contactPersonService;

    @GetMapping
    public ResponseEntity<List<ContactPerson>> getContactPersons(@RequestParam("page") int page) {
        return ResponseEntity.ok().body(contactPersonService.getContactPersons(page));
    }
}
