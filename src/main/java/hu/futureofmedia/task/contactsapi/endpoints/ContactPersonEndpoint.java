package hu.futureofmedia.task.contactsapi.endpoints;

import hu.futureofmedia.task.contactsapi.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.services.ContactPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
