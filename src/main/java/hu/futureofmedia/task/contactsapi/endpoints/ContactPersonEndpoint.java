package hu.futureofmedia.task.contactsapi.endpoints;

import hu.futureofmedia.task.contactsapi.dtos.ContactPersonRequest;
import hu.futureofmedia.task.contactsapi.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.services.ContactPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
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

    @GetMapping("/{id}")
    public ResponseEntity<ContactPerson> getContactPerson(@PathVariable Long id) {
        return ResponseEntity.ok().body(contactPersonService.getContactPerson(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactPerson(@PathVariable Long id) {
        contactPersonService.deleteContactPerson(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Long> createContactPerson(@RequestBody ContactPersonRequest contactPersonRequest) {
        Long id = contactPersonService.createContactPerson(contactPersonRequest);

        return ResponseEntity.ok().body(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateContactPerson(
            @PathVariable Long id, @RequestBody ContactPersonRequest contactPersonRequest) {
        Long contactPersonId = contactPersonService.updateContactPerson(id, contactPersonRequest);
        URI location = URI.create(String.format("/contact-person/%s", contactPersonId));

        return ResponseEntity.created(location).build();
    }
}
