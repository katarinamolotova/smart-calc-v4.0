package edu.school21.SmartCal40.controllers;

import edu.school21.SmartCal40.models.BasicCalcModel;
import edu.school21.SmartCal40.repositories.HistoryRepository;
import edu.school21.SmartCal40.services.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MainController {
    final BasicCalcModel calcModel;
    final HistoryService service;
//    final HistoryRepository repository; // какого то уя


    @GetMapping("/") // потом поправить
    public String getMainPage() {
        return "index";
    }

    @PostMapping("/")
    public String getResult(
            @RequestParam("expression") final String expression,
            @RequestParam("value") final String value,
            final Model model
    ) {
//        service.save
        String result = calcModel.getResult(expression, value);
        model.addAttribute("value", value);
        model.addAttribute("result", result);
        return "index";
    }
}