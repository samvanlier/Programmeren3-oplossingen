package be.kdg.blog.controller;

import be.kdg.blog.dto.BlogEntryDto;
import be.kdg.blog.dto.BlogFormDto;
import be.kdg.blog.model.Entry;
import be.kdg.blog.service.BlogService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

//DEEL 1
@RestController
public class BlogRestController {
    private final BlogService blogService;
    private final ModelMapper modelMapper;

    @Autowired
    public BlogRestController(BlogService blogService, ModelMapper modelMapper) {
        this.blogService = blogService;
        this.modelMapper = modelMapper;
    }

    /*
    Een GET op /users/{userId}/entries
    moet een JSON-resultaat teruggeven met een lijst van blog entries van de gebruiker in kwestie.
     */
    @GetMapping("/users/{userId}/entries")
    public Collection<BlogEntryDto> getEntriesByUserId(@PathVariable int userId, @AuthenticationPrincipal UserDetails userDetails) {
        Collection<BlogEntryDto> dtos = new ArrayList<>();
        try {
            Collection<Entry> entries = this.blogService.getEntriesByUserId(userId);
            for (Entry entry :
                    entries) {
                BlogEntryDto dto = modelMapper.map(entry, BlogEntryDto.class);

                dtos.add(dto);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return dtos;
    }

    /*
    Een POST op /entries
    met volgende request body
    {
        "subject": "Dingen gedaan",
        "message": "Vandaag veel gedaan"
    }
    moet een blog entry aan hetsysteem toevoegen voor de ingelogde gebruiker
     */
    @PostMapping("/entries")
    public void postNewEntry(@AuthenticationPrincipal UserDetails userDetails, @RequestBody BlogFormDto entryDto) {
        if (entryDto != null) {
            Entry entry = new Entry(entryDto.getSubject(), entryDto.getMessage());
            blogService.addEntry(entry, userDetails.getUsername());
        }
    }
}
