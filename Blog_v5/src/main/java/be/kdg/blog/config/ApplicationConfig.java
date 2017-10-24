package be.kdg.blog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    /**
     * maakt een bean voor modelmapper zodat deze via DI kan geïnjecteerd worden via de constructor in de @link(BlogController)
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
