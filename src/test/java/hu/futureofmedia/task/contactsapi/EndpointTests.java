package hu.futureofmedia.task.contactsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.futureofmedia.task.contactsapi.common.Status;
import hu.futureofmedia.task.contactsapi.dtos.ContactPersonRequest;
import hu.futureofmedia.task.contactsapi.endpoints.ContactPersonEndpoint;
import hu.futureofmedia.task.contactsapi.entities.ContactPerson;
import hu.futureofmedia.task.contactsapi.services.ContactPersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class EndpointTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ContactPersonEndpoint contactPersonEndpoint;

    @MockBean
    private ContactPersonService contactPersonService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(contactPersonEndpoint).build();
    }

    @Test
    public void createContactPerson() throws Exception {
        ContactPersonRequest dto = new ContactPersonRequest();

        when(contactPersonService.createContactPerson(any())).thenReturn(4L);

        MvcResult result = mvc.perform(post("/contact-person")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(dto))
        ).andExpect(status().is(200)).andReturn();

        Assertions.assertEquals(4L, Long.parseLong(result.getResponse().getContentAsString()));
    }

    @Test
    public void getContactPerson() throws Exception {
        ContactPerson person = new ContactPerson();
        person.setId(321L);
        person.setLastName("Bela");

        when(contactPersonService.getContactPerson(321L)).thenReturn(person);

        mvc.perform(get("/contact-person/321")).andExpect(status().is(200))
                .andExpect(jsonPath("$.lastName").value("Bela"));
    }
}
