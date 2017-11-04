package be.kdg.blog.controller;

import be.kdg.blog.dto.BlogEntryDto;
import be.kdg.blog.dto.BlogFormDto;
import be.kdg.blog.model.Blog;
import be.kdg.blog.model.Entry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class BlogController {

    private final Blog blog;
    private final ModelMapper modelMapper = new ModelMapper(); //gebruiken om objecten te mappen op DTO's (geimporteerd via gradle)

    @Autowired
    public BlogController(Blog blog) {
        this.blog = blog;
        blog.vulBlog();
    }

    @GetMapping("/blog")
    public ModelAndView getEntries() {
        ModelAndView  modelAndView = getBlogModelAndView();
        //modelAndView.getModel().put("entry", new Entry()); //zorgt voor een nieuwe entry voor de form
        modelAndView.getModel().put("blogFormDto", new BlogFormDto()); //deel 3 -> zorgt er voor dat er een leeg dto object is zodat deze in de form kan worden gevult en verstuurd kan worden naar de post
        return modelAndView;
    }

    /*
    DEEL 1:
        - entry komt binnen
        - eerst valideren
    DEEL 3:
        - bij een post komt er nu een DTO binne (deze bevat een subject en message)
        - valideer deze dto eerst!
     */
    @PostMapping("/blog")
    public ModelAndView postEntry(@Valid @ModelAttribute BlogFormDto formDto, BindingResult bindingResult) {
       ModelAndView modelAndView = getBlogModelAndView();
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        Entry entry = new Entry(formDto.getSubject(), formDto.getMessage(), formDto.getUserName());

        blog.addEntry(entry);
        return modelAndView;
    }

    /*
    extra hulp methode voor het maken van een ModelAndView
     */
    private ModelAndView getBlogModelAndView() {
        final ModelAndView modelAndView;
        modelAndView = new ModelAndView();
        modelAndView.setViewName("blog");

        final ArrayList<BlogEntryDto> entries = new ArrayList<>();

        //use DTO's
        for (Entry entry :
                blog.getEntries()) {
            BlogEntryDto dto = modelMapper.map(entry, BlogEntryDto.class);
            entries.add(dto);
        }


        modelAndView.getModel().put("entries", entries);

        return modelAndView;
    }
}
