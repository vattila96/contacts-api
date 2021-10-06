package hu.futureofmedia.task.contactsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ContactsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContactsApiApplication.class, args);
    }
}
