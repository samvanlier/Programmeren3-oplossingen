package be.kdg.blog.controllers;

import be.kdg.blog.model.Blog;
import be.kdg.blog.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

//geen overerving meer nodig
@Controller
public class BlogController {

    private final Blog blog;

    /*
    DEEL 3:
        gebruikt DI om een nieuw blog object te injecteren
     */
    @Autowired
    public BlogController(Blog blog) {
        this.blog = blog;
        blog.vulBlog();
    }

    /*
    DEEL 1:
        GET op /blog​:
            Je servlet stuurt HTML terug naar de client. In de HTML pagina vinden we een lijstje terug van alle entries
            in de blog.
     */
    //get methode
    @GetMapping("/blog")
    public ModelAndView getEntries() {
        final ModelAndView modelAndView;
        modelAndView = new ModelAndView();
        modelAndView.setViewName("blog");

        final ArrayList<Entry> entries = blog.getEntries();
        modelAndView.getModel().put("entries", entries);

        return modelAndView;
    }

    /*
    DEEL 1:
        POST op /blog​:
            - Werk met twee request parameters: subject en message.
            - Voeg een entry toe aan de blog met de gegeven subject en message.
            - Na het toevoegen van de nieuwe entry moet er opnieuw een lijstje van alle entries getoond worden
     */
    //post methode
    @PostMapping("/blog")
    public ModelAndView postEntry(HttpServletRequest req){
        blog.addEntry(req.getParameter("subject"), req.getParameter("message"));

        ModelAndView modelAndView = new ModelAndView("blog");
        modelAndView.getModel().put("entries", blog.getEntries());

        return modelAndView;
    }
}
