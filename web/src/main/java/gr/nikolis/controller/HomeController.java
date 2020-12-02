package gr.nikolis.controller;

import gr.nikolis.mappings.home.HomeMappings;
import gr.nikolis.mappings.home.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // http://localhost:8888 or http://localhost:8888/home
    @GetMapping({HomeMappings.HOME, HomeMappings.HOME_DEFAULT})
    public String home() {
        return ViewNames.HOME;
    }
}
