package edu.school21.SmartCal40.controllers;

import edu.school21.SmartCal40.dto.CreditResultDTO;
import edu.school21.SmartCal40.dto.DepositResultDTO;
import edu.school21.SmartCal40.enums.ErrorMessage;
import edu.school21.SmartCal40.models.DepositCalcModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class DepositController {
    final DepositCalcModel depositCalcModel;

    @GetMapping("/deposit")
    public String getDepositPage() {
        return "deposit";
    }

    @PostMapping("/deposit")
    public String getMainPage(
            @RequestParam("summa") final String summa,
            @RequestParam("amount-of-month") final String amountOfMonth,
            @RequestParam("term-type") final String termType,
            @RequestParam("percent") final String percent,
            @RequestParam("capitalization-period") final String capitalizationPeriod,
            @RequestParam("period-pay") final String periodPay,
            @RequestParam("month-start") final String monthStart,
            /* как сюда бля мапу засунуть */
            @RequestParam("sum-begin") final String sumBegin,
            @RequestParam("result-percent") final String resultPercent,
            @RequestParam("tax-percent") final String taxPercent,
            final Model model
    ) {
        depositCalcModel.validateInputParameters(
                summa,
                amountOfMonth,
                termType,
                percent,
                capitalizationPeriod,
                periodPay,
                monthStart,
                null, // тут должна быть маппа
                null, // тут должна быть маппа
                sumBegin,
                resultPercent,
                taxPercent
        );

        DepositResultDTO resultDTO = depositCalcModel.getResult();
        if (resultDTO.getErrorMassage().equals(ErrorMessage.SUCCESS)) {
            model.addAttribute("result", resultDTO);
        } else {
            model.addAttribute("result", new DepositResultDTO(true, resultDTO.getErrorMassage()));
        }

        return "deposit";
    }
}