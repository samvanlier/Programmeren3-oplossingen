package be.kdg.blog.controller;

import be.kdg.blog.dto.BlogEntryDto;
import be.kdg.blog.dto.BlogFormDto;
import be.kdg.blog.model.User;
import be.kdg.blog.security.CustomUserDetails;
import be.kdg.blog.service.BlogService;
import be.kdg.blog.model.Entry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final ModelMapper modelMapper;

    //versie voor gebruik te maken van een bean voor modelmapper
    /*@Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }*/

    @Autowired
    public BlogController(BlogService blogService, ModelMapper modelMapper) {
        this.blogService = blogService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/blog")
    public ModelAndView getEntries(@AuthenticationPrincipal CustomUserDetails userDetails) {
        ModelAndView modelAndView = getBlogModelAndView();
        //modelAndView.getModel().put("entry", new Entry()); //zorgt voor een nieuwe entry voor de form
        modelAndView.getModel().put("blogFormDto", new BlogFormDto()); //deel 3
        return modelAndView;
    }

    @PostMapping("/blog")
    public ModelAndView postEntry(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @ModelAttribute BlogFormDto formDto, BindingResult bindingResult) {
        ModelAndView modelAndView = getBlogModelAndView();
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        Entry entry = new Entry(formDto.getSubject(), formDto.getMessage());
        blogService.addEntry(entry, userDetails.getUsername());
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

    //WARNING
    //Deze methode heeft niks te maken met de oefeningen maar het is gemaakt om iets te testen (html late zien als je wel of niet ingelogt bent)
    @GetMapping("/test")
    public ModelAndView testLogin() {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loggedin");

        return modelAndView;
    }
}
