package edu.school21.SmartCal40.controllers;

import edu.school21.SmartCal40.models.BasicCalcModel;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController // потом поправить
@AllArgsConstructor
public class MainController {
    final BasicCalcModel model;

    @GetMapping("/{request}") // потом поправить
    public String getMainPage(@PathVariable final String request) {
        return "result = " + model.getResult(request, 0) + "\n";
    }
}