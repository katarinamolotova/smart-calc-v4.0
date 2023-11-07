package edu.school21.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MainController {

    @PostMapping("/")
    public String getMainPage(final Model model) {
        model.addAttribute("tab", "main"); // переправить
        return "index";
    }
}