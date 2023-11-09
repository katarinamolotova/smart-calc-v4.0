package edu.school21.SmartCal40.controllers;

import edu.school21.SmartCal40.models.CreditCalcModel;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // потом поправить
@AllArgsConstructor
public class CreditController {
    final CreditCalcModel model;

    @PostMapping("/credit/{request}") // потом поправить
    public String getMainPage(@PathVariable final String request) {
        return null;
    }
}