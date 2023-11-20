package edu.school21.SmartCal40.controllers;

import edu.school21.SmartCal40.models.DepositCalcModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//@Controller
@AllArgsConstructor
public class DepositController {
    final DepositCalcModel model;

    @GetMapping("/deposit")
    public String getDepositPage() {
        return "deposit";
    }

    @PostMapping("/deposit")
    public String getMainPage() {
        return "deposit";
    }
}