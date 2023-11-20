package edu.school21.SmartCal40.controllers;

import edu.school21.SmartCal40.dto.CreditResultDTO;
import edu.school21.SmartCal40.enums.ErrorMessage;
import edu.school21.SmartCal40.models.CreditCalcModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class CreditController {
    private final CreditCalcModel creditModel;

    @GetMapping("/credit")
    public String getCreditPage() {
        return "credit";
    }

    @PostMapping("/credit")
    public String getCreditResult(
            @RequestParam("summa") final String summa,
            @RequestParam("percent") final String percent,
            @RequestParam("period") final String period,
            @RequestParam("term-type") final String termType,
            @RequestParam("credit-type") final String creditType,
            final Model model
    ) {
        final ErrorMessage message = creditModel.calculate(creditType, summa, period, termType, percent);
        if (message == ErrorMessage.SUCCESS) {
            model.addAttribute("result", creditModel.getResult());
        } else {
            model.addAttribute("result", new CreditResultDTO(true, message.getName()));
        }
        return "credit";
    }
}
