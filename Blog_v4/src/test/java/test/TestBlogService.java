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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestBlogService {

    @MockBean
    private EntryRepository entryRepo;

    @MockBean
    private UserRepository userRepo;

    BlogService service;

    @Before
    public void setup() {
        this.service = new BlogService(entryRepo, userRepo);
    }

    @Test
    public void testFindAllEntries() {
        Entry e1 = new Entry("hello", "wolrd", "samvanlier");
        Entry e2 = new Entry("hallo", "wereld", "samvanlier2");

        given(this.entryRepo.findAll()).willReturn(Arrays.asList(e1, e2));

        List<Entry> entries = this.service.getAllEntries().stream().collect(Collectors.toList());

        assertEquals(entries.get(0).getId(), e1.getId());
        assertEquals(entries.get(1).getId(), e2.getId());
    }

    @Test
    public void testAddNewUserIfUserDoesNotExist() {
        Entry entry = new Entry("hello", "world");
        User newUser = new User("newUserName");

        when(this.userRepo.findByName("newUserName")).thenReturn(newUser); //we moeten 1 methode mocken want de insert gaat werken en we moeten enkel dus de return mocken

        this.service.addEntry(entry, "newUserName");

        assertEquals(newUser.getName(), userRepo.findByName("newUserName").getName());

    }

    @Test
    public void testIfUserIsNotCreatedIfUserAlreadyExist() {
        User user = new User("newUser");
        Entry entry = new Entry("hello", "world");

        given(this.userRepo.findByName(user.getName())).willReturn(user);

        this.service.addEntry(entry, "newUser");

        ArgumentCaptor<Entry> captor = ArgumentCaptor.forClass(Entry.class);
        verify(this.entryRepo, times(1)).save(captor.capture());

        Entry addedEntry = captor.getValue();
        assertThat(addedEntry.getSubject(), is(entry.getSubject()));
        assertThat(addedEntry.getMessage(), is(entry.getMessage()));
        assertThat(addedEntry.getUser(), is(entry.getUser()));
        assertThat(addedEntry.getTijdVanToevoeging(), is(entry.getTijdVanToevoeging()));
    }

    @Test
    public void testAddNewEntry() {
        Entry entry = new Entry("hello", "world");

        given(this.entryRepo.findAll()).willReturn(Arrays.asList(entry));

        this.service.addEntry(entry);

        ArgumentCaptor<Entry> captor = ArgumentCaptor.forClass(Entry.class);
        verify(this.entryRepo, times(1)).save(captor.capture());
        Entry addedEntry = captor.getValue();

        assertThat(addedEntry.getId(), is(entry.getId()));
        assertThat(addedEntry.getSubject(), is(entry.getSubject()));
        assertThat(addedEntry.getMessage(), is(entry.getMessage()));
        assertThat(addedEntry.getTijdVanToevoeging(), is(entry.getTijdVanToevoeging()));
    }
}
