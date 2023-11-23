package edu.school21.SmartCal40;

import edu.school21.SmartCal40.enums.CreditType;
import edu.school21.SmartCal40.enums.TermType;
import edu.school21.SmartCal40.models.CreditCalcModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationCreditTests {

	private static final String TYPE = CreditType.ANNUITY.getName();
	private static final String SUM = "1000000";
	private static final String AMOUNT_OF_MONTH = "12";
	private static final String TERM_TYPE = TermType.MONTH.getName();
	private static final String PERCENT = "5";

	@Autowired
	private CreditCalcModel creditModel;

	@BeforeEach
	public void calculate() {
		creditModel.validateInputParameters(TYPE, SUM, AMOUNT_OF_MONTH, TERM_TYPE, PERCENT);
		creditModel.calculate();
	}

	@Test
	public void overpay() {
		Assertions.assertEquals(27289.9, creditModel.getResult().getOverPay());
	}

	@Test
	public void totalPayment() {
		Assertions.assertEquals(1027289.89, creditModel.getResult().getTotalPayment());
	}

	@Test
	public void everyMonthPay() {
		List<Double> payment = creditModel.getResult().getEveryMonthPayAsList();
		Assertions.assertEquals(AMOUNT_OF_MONTH, String.valueOf(payment.size()));
		Assertions.assertEquals(85607.49, payment.get(0));
	}

}
