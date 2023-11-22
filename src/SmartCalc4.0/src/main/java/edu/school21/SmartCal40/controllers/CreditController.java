package edu.school21.SmartCal40.controllers;

import edu.school21.SmartCal40.dto.CreditParametersDTO;
import edu.school21.SmartCal40.dto.CreditResultDTO;
import edu.school21.SmartCal40.enums.Status;
import edu.school21.SmartCal40.models.CreditCalcModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@AllArgsConstructor
public class CreditController {
    private final CreditCalcModel creditModel;

    @GetMapping("/credit")
    public String getCreditPage(final Model model) {
        model.addAttribute("result", new CreditResultDTO(false, Status.SUCCESS));
        model.addAttribute("params", new CreditParametersDTO());
        return "credit";
    }

    /**
     * Расчет кредита
     * @param summa         общая сумма кредита
     * @param percent       процентная ставка срок
     * @param period        срок
     * @param termType      тип срока (месяцев/лет)
     * @param creditType    тип кредита (аннуитетный/дифференцированный)
     * @param model         модель для фронта (присутствует по-умолчанию)
     * @return страница кредитного калькулятора
     */
    @PostMapping("/credit")
    public String getCreditResult(
            @RequestParam("summa") final String summa,
            @RequestParam("percent") final String percent,
            @RequestParam("period") final String period,
            @RequestParam("term-type") final String termType,
            @RequestParam("credit-type") final String creditType,
            final Model model
    ) {
        creditModel.validateInputParameters(creditType, summa, period, termType, percent);
        log.info("Расчет кредита с параметрами: {}", creditModel.getStartParameters());

        final Status status = creditModel.calculate();
        if (status == Status.SUCCESS) {
            model.addAttribute("result", creditModel.getResult());
            log.info("Результат расчета по кредиту: {}", creditModel.getResult());
        } else {
            model.addAttribute("result", new CreditResultDTO(true, status));
            log.warn("При расчете кредита произошла ошибка: {}", status.getName());
        }
        model.addAttribute("params", creditModel.getStartParameters());
        return "credit";
    }
}
