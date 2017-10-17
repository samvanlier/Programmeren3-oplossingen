package be.kdg.blog.controller;

import be.kdg.blog.dto.BlogEntryDto;
import be.kdg.blog.dto.BlogFormDto;
import be.kdg.blog.service.BlogService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class BlogController {

    private final BlogService blogService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blog")
    public ModelAndView getEntries() {
        ModelAndView modelAndView = getBlogModelAndView();
        //modelAndView.getModel().put("entry", new Entry()); //zorgt voor een nieuwe entry voor de form
        modelAndView.getModel().put("blogFormDto", new BlogFormDto()); //deel 3
        return modelAndView;
    }

    @PostMapping("/blog")
    public ModelAndView postEntry(@Valid @ModelAttribute BlogFormDto formDto, BindingResult bindingResult) {
        ModelAndView modelAndView = getBlogModelAndView();
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        Entry entry = new Entry(formDto.getSubject(), formDto.getMessage());

        blogService.addEntry(entry, formDto.getUserName());
        return modelAndView;
    }

    private ModelAndView getBlogModelAndView() {
        final ModelAndView modelAndView;
        modelAndView = new ModelAndView();
        modelAndView.setViewName("blog");

        final ArrayList<BlogEntryDto> entries = new ArrayList<>();

        //use DTO's
        for (Entry entry :
                blogService.getAllEntries()) {
            BlogEntryDto dto = modelMapper.map(entry, BlogEntryDto.class);
            entries.add(dto);
        }


        modelAndView.getModel().put("entries", entries);

        return modelAndView;
    }
}
