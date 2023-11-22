package edu.school21.SmartCal40.controllers;

import edu.school21.SmartCal40.dto.DepositParametersDTO;
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
    private final DepositCalcModel depositModel;

    @GetMapping("/deposit")
    public String getDepositPage(
            final Model model
    ) {
        model.addAttribute("params", new DepositParametersDTO());
        return "deposit";
    }

    /**
     * Расчет доходности вклада
     *
     * @param summa             сумма вклада
     * @param period            срок размещения (в месяцах или годах)
     * @param termType          тип периода (месяцев/лет)
     * @param percent           процентная ставка
     * @param taxPercent        налоговая ставка
     * @param capitalization    капитализация процентов (Нет/Ежемесячно/Ежеквартально/Ежегодно)
     * @param periodPayment     периодичность выплат (Единовременно/Ежемесячно/Ежеквартально/Ежегодно)
     * @param openDate          дата открытия вклада
     * @param addition          ежемесячное пополнение (т.е. на какую сумму пополняет пользователь вклад раз в месяц)
     * @param withdrawal        ежемесячное снятие (т.е. какую сумму пользователь снимает раз в месяц)
     * @param model             модель для фронта (присутствует по-умолчанию)
     * @return имя страницы (всегда deposit)
     */
    @PostMapping("/deposit")
    public String getDepositResult(
            @RequestParam("summa") final String summa,
            @RequestParam("period") final String period,
            @RequestParam("term-type") final String termType,
            @RequestParam("percent") final String percent,
            @RequestParam("tax-percent") final String taxPercent,
            @RequestParam("capitalization") final String capitalization,
            @RequestParam("period-payment") final String periodPayment,
            @RequestParam("open-date") final String openDate,
            @RequestParam("addition") final String addition,
            @RequestParam("withdrawal") final String withdrawal,
            final Model model
    ) {
        depositModel.validateInputParameters(
                summa,
                period,
                termType,
                percent,
                capitalization,
                periodPayment,
                openDate,
                addition,
                withdrawal,
                taxPercent
        );

        DepositResultDTO resultDTO = depositModel.getResult();
        model.addAttribute("params", depositModel.getStartParameters());
        if (resultDTO.getErrorMassage().equals(ErrorMessage.SUCCESS)) {
            model.addAttribute("result", resultDTO);
        } else {
            model.addAttribute("result", new DepositResultDTO(true, resultDTO.getErrorMassage()));
        }
        return "deposit";
    }
}