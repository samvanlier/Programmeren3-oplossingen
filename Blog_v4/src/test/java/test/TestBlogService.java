package test;

import be.kdg.blog.Application;
import be.kdg.blog.model.Entry;
import be.kdg.blog.model.User;
import be.kdg.blog.repository.EntryRepository;
import be.kdg.blog.repository.UserRepository;
import be.kdg.blog.service.BlogService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

//DEEL 3

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestBlogService {

    @MockBean
    private EntryRepository entryRepo;

    @MockBean
    private UserRepository userRepo;

    private BlogService service;

    @Before
    public void setup() {
        this.service = new BlogService(entryRepo, userRepo);
    }

    /*
    Kijk na of de findAll methode van je BlogEntry-service wel alle objecten teruggeeft
    die in je (gemockte) repository zitten
     */
    @Test
    public void testFindAllEntries() {
        Entry e1 = new Entry("hello", "wolrd", "samvanlier");
        Entry e2 = new Entry("hallo", "wereld", "samvanlier2");

        given(this.entryRepo.findAll()).willReturn(Arrays.asList(e1, e2)); //mock repo!

        List<Entry> entries = this.service.getAllEntries().stream().collect(Collectors.toList()); //get data from repo

        assertEquals(entries.get(0).getId(), e1.getId());
        assertEquals(entries.get(1).getId(), e2.getId());
    }

    /*
    Kijk na of de save methode van je BlogEntry-service een nieuwe User aanmaakt in de User-repository
    indien de gebruiker nog niet bestond
     */
    @Test
    public void testAddNewUserIfUserDoesNotExist() {
        Entry entry = new Entry("hello", "world");
        User newUser = new User("newUserName");

        when(this.userRepo.findByName("newUserName")).thenReturn(newUser); //we moeten 1 methode mocken want de insert gaat werken en we moeten enkel dus de return mocken

        this.service.addEntry(entry, "newUserName");

        assertEquals(newUser.getName(), userRepo.findByName("newUserName").getName());
    }

    /*
    Kijk na of de save methode van je BlogEntry-service geen nieuwe User aanmaakt in de User-repository
    indien de gebruiker al wel bestond
     */
    @Test
    public void testIfUserIsNotCreatedIfUserAlreadyExist() {
        User user = new User("sam");

        this.service.addEntry(new Entry("hello", "world"), user.getName());

        //maak gebruik van ArgumentCaptor<T>
        //Documentatie: https://static.javadoc.io/org.mockito/mockito-core/2.10.0/org/mockito/ArgumentCaptor.html
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class); //dit gaat objecten opvangen voor later te asserten!
        //.save()-methode gaat alles valideren en we willen weten of het door de validatie is geraakt
        verify(this.userRepo, times(1)).save(captor.capture());
        User addedUser = captor.getValue(); //get user-object dat opgevangen werd opvragen

        assertThat(addedUser.getName(), is(user.getName()));
    }

    @Test
    public void testAddNewEntry() {
        Entry entry = new Entry("hello", "world");

        given(this.entryRepo.findAll()).willReturn(Arrays.asList(entry));

        this.service.addEntry(entry);

        //maak gebruik van ArgumentCaptor<T>
        //Documentatie: https://static.javadoc.io/org.mockito/mockito-core/2.10.0/org/mockito/ArgumentCaptor.html
        //net zoals bij user willen we de entry gaan testen (zie extra uitleg bij testIfUserIsNotCreatedIfUserAlreadyExist() of de documentatie-link)
        ArgumentCaptor<Entry> captor = ArgumentCaptor.forClass(Entry.class);
        verify(this.entryRepo, times(1)).save(captor.capture());
        Entry addedEntry = captor.getValue();

        assertThat(addedEntry.getId(), is(entry.getId()));
        assertThat(addedEntry.getSubject(), is(entry.getSubject()));
        assertThat(addedEntry.getMessage(), is(entry.getMessage()));
        assertThat(addedEntry.getTijdVanToevoeging(), is(entry.getTijdVanToevoeging()));
    }
}
