package test;

import be.kdg.blog.Application;
import be.kdg.blog.controller.BlogRestController;
import be.kdg.blog.dto.BlogEntryDto;
import be.kdg.blog.model.Entry;
import be.kdg.blog.security.CustomUserDetails;
import be.kdg.blog.service.BlogService;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, SpringSecurityWebTestConfig.class})
@WebAppConfiguration
@EnableWebSecurity
public class TestBlogRestController {
    @MockBean
    private BlogService blogService;

    @Autowired
    private BlogRestController controller;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    /*
    given : Indien je gemockte service voor een bepaalde gebruiker een tweetal blog entries zou afleveren,
    moet je nagaan ( expect ) of alle velden van de afgeleverde JSON wel een juiste inhoud bevatten.
     */
    @Test
    @WithUserDetails("sam")
    public void testMVCGetEntriesByUserId() throws Exception {
        int id = 1;
        Collection<Entry> lijst = new ArrayList<>();

        Entry dto = new Entry();

        dto.setSubject("hello");
        dto.setMessage("world");

        lijst.add(dto);

        final CustomUserDetails samUserDetails = (CustomUserDetails) this.userDetailsService.loadUserByUsername("sam");

        given(this.blogService.getEntriesByUserId(id)).willReturn(lijst);

        this.mvc.perform(get("/users/" + id + "/entries")
        .with(csrf())
        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].subject", is(dto.getSubject())));
    }

    /*
    Je roept de REST controller methode aan die instaat voor het toevoegen van een blog entry.
    Je verifieert dat er een methode van je service klasse opgeroepen is geweest die instaat voor het toevoegen
    van een blog entry. Je kijkt ook na of de parameter die deze laatste methode binnen gekregen heeft gelijk is aan de
    parameter die je zelf aan de controller methode doorgaf.
     */
    @Test
    public void testGetEntriesByUserId() throws NotFoundException {
        int id = 1;
        Collection<Entry> lijst = new ArrayList<>();

        Entry dto = new Entry();

        dto.setSubject("hello");
        dto.setMessage("world");

        lijst.add(dto);

        final CustomUserDetails samUserDetails = (CustomUserDetails) this.userDetailsService.loadUserByUsername("sam");

        given(this.blogService.getEntriesByUserId(id)).willReturn(lijst);

       final Collection<BlogEntryDto> listToTest = controller.getEntriesByUserId(id, samUserDetails);
       final BlogEntryDto dtoToTest = (BlogEntryDto) listToTest.toArray()[0];

       assertThat(dtoToTest.getSubject(), is(dto.getSubject()));
       assertThat(dtoToTest.getMessage(), is(dto.getMessage()));
    }
}
