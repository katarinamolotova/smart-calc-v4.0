package edu.school21.SmartCal40.controllers;

import edu.school21.SmartCal40.entities.HistoryEntity;
import edu.school21.SmartCal40.models.BasicCalcModel;
import edu.school21.SmartCal40.services.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Locale;

@Controller
@AllArgsConstructor
public class MainController {
    private final BasicCalcModel calcModel;
    private final HistoryService service;

    @GetMapping("/about")
    public String getAboutPage() {
        return "about";
    }

    @GetMapping("/")
    public String getMainPage(final Model model) {
        setBaseAttributeToModel("0", "",  service.loadHistory(), model);
        return "index";
    }

    @GetMapping("/delete-history")
    public String deleteHistory() {
        service.deleteAll();
        return "redirect:/";
    }

    @PostMapping("/")
    public String getResult(
            @RequestParam("expression") final String expression,
            @RequestParam("value") final String value,
            final Model model
    ) {
        service.save(expression);
        setBaseAttributeToModel(
                value,
                calcModel.getResult(expression, value),
                service.loadHistory(),
                model
        );
        return "index";
    }

    private void setBaseAttributeToModel(
            final String xValue,
            final String result,
            final Iterable<HistoryEntity> histories,
            final Model model
    ) {
        model.addAttribute("value", xValue);
        model.addAttribute("result", result);
        model.addAttribute("histories", histories);
    }
}