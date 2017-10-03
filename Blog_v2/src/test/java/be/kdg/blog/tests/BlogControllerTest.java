package be.kdg.blog.tests;

import be.kdg.blog.Application;
import be.kdg.blog.model.Blog;
import be.kdg.blog.model.Entry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BlogControllerTest {

    @MockBean
    private Blog blog;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetBlogName() throws Exception {
        this.mvc.perform(get("/blog").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("blog"));
    }

    @Test
    public void testPostBlog() throws Exception {
        String subject = "hello";
        String message = "world";

        this.mvc.perform(
                post("/blog")
                        .requestAttr("subject", subject).requestAttr("message", message)
                        .accept(MediaType.TEXT_HTML)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("blog"));
    }


    @Test
    public void testGetBlogEntries() throws Exception {
        final LocalDateTime time1 = LocalDateTime.now();
        final LocalDateTime time2 = LocalDateTime.now();

        final Entry e1 = new Entry(1, "hello", "world", time1);
        final Entry e2 = new Entry(2, "hallo", "wereld", time2);

        final ArrayList<Entry> entries = new ArrayList<>();
        entries.addAll(Arrays.asList(
                e1, e2
        ));

        given(this.blog.getEntries()).willReturn(entries);

        this.mvc.perform(get("/blog").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(e1.getSubject())))
                .andExpect(content().string(containsString(e1.getMessage())))
                .andExpect(content().string(containsString(e1.getTijdVanToevoeging().toString())))
                .andExpect(content().string(containsString(e2.getSubject())))
                .andExpect(content().string(containsString(e2.getMessage())))
                .andExpect(content().string(containsString(e2.getTijdVanToevoeging().toString())))
        ;
    }

    //extra
    //TODO: extra oefening nog maken


}
