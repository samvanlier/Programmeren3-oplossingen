package test;

import be.kdg.blog.Application;
import be.kdg.blog.model.Entry;
import be.kdg.blog.model.User;
import be.kdg.blog.service.BlogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class, SpringSecurityWebTestConfig.class })
@WebAppConfiguration
@EnableWebSecurity
public class TestBlogControllerMetAuthenticatie {

    @MockBean
    private BlogService blogService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithUserDetails("sam")
    public void testPostEntry() throws Exception {
        this.mvc.perform(post("/blog")
            .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .param("body", "BODY")
                .param("parentId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostEntryNotLoggedIn() throws Exception {
        this.mvc.perform(post("/blog")
                .with(csrf())
                .accept(MediaType.TEXT_HTML)
                .param("body", "BODY")
                .param("parentId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }
}
