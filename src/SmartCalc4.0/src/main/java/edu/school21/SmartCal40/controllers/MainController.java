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

import java.util.List;

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

    // TODO обработка ошибок
    @PostMapping("/")
    public String getResult(
            @RequestParam("expression") final String expression,
            @RequestParam("checkbox") final boolean isGraph,
            @RequestParam("value") final String value,
            @RequestParam("min-x") final String minX,
            @RequestParam("max-x") final String maxX,
            final Model model
    ) {
        service.save(expression);
        final Iterable<HistoryEntity> histories = service.loadHistory();
        if (isGraph) {
            setBaseAttributeToModel(value, expression, histories, model);
            setResultForGraph(expression, minX, maxX, model);
        } else {
            final String result = calcModel.getResult(expression, value);
            setBaseAttributeToModel(value, result, histories, model);
        }

        model.addAttribute("isGraph", isGraph);
        model.addAttribute("maxX", maxX);
        model.addAttribute("minX", minX);
        return "index";
    }

    private void setResultForGraph(
            final String expression,
            final String minX,
            final String maxX,
            final Model model
    ) {
        calcModel.calculateForDraw(expression, minX, maxX);
        model.addAttribute("valuesX", calcModel.getXCoordinates());
        model.addAttribute("valuesY", calcModel.getYCoordinates());
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
        model.addAttribute("maxX", 10);
        model.addAttribute("minX", -10);
        model.addAttribute("isGraph", false);
        model.addAttribute("valuesX", List.of());
        model.addAttribute("valuesY", List.of());
    }
}