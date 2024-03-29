package edu.school21.SmartCal40.controllers;

import edu.school21.SmartCal40.entities.HistoryEntity;
import edu.school21.SmartCal40.models.BasicCalcModel;
import edu.school21.SmartCal40.services.HistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
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
        setBaseAttributeToModel("0", "",  service.loadLastTenRecordsOfHistory(), model);
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
            @RequestParam(name = "checkbox", required = false) final boolean isGraph,
            @RequestParam("value") final String value,
            @RequestParam("min-x") final String minX,
            @RequestParam("max-x") final String maxX,
            final Model model
    ) {
        log.info("Вычисление выражения {}", expression);
        service.save(expression);
        final List<String> histories = service.loadLastTenRecordsOfHistory();

        try {
            if (isGraph) {
                setBaseAttributeToModel(value, expression, histories, model);
                setResultForGraph(expression, minX, maxX, model);
                log.info("Результатом является график, где min-x = {}, max-x = {}", minX, maxX);
            } else {
                final String result = calcModel.getResult(expression, value);
                setBaseAttributeToModel(value, result, histories, model);
                log.info("Результат вычислений {}", result);
            }
        } catch (final Exception e) {
            setBaseAttributeToModel(value, e.getMessage(), histories, model);
            log.warn("Ошибка во время вычисления: {}", e.getMessage());
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
            final List<String> histories,
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