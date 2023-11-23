package edu.school21.SmartCal40;

import edu.school21.SmartCal40.dto.DepositResultDTO;
import edu.school21.SmartCal40.enums.PeriodType;
import edu.school21.SmartCal40.enums.TermType;
import edu.school21.SmartCal40.models.DepositCalcModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;

@SpringBootTest
class ApplicationDepositTests {

	private static final String DEPOSIT_PERCENT = "5";
	private static final String SUMMA = "1000000";
	private static final String AMOUNT_OF_MONTH = "12";
	private static final String TERM_TYPE = TermType.MONTH.getName();
	private static final String CAPITAL = PeriodType.NONE.getName();
	private static final String PERIOD_PAY = PeriodType.ONCE.getName();
	private static final String START_DATE = LocalDate.now().toString();
	private static final String ADDITIONS = "2000";
	private static final String WITHDRAWAL = "1000";
	private static final String TAX_PERCENT = "13";
	private DepositResultDTO resultDTO;

	@Autowired
	private DepositCalcModel depositCalcModel;

	@BeforeEach
	public void calculate() {
		depositCalcModel.validateInputParameters(
				SUMMA,
				AMOUNT_OF_MONTH,
				TERM_TYPE,
				DEPOSIT_PERCENT,
				CAPITAL,
				PERIOD_PAY,
				START_DATE,
				ADDITIONS,
				WITHDRAWAL,
				TAX_PERCENT
		);
		resultDTO = depositCalcModel.getResult();
	}

	@Test
	public void resultPercent() {
		Assertions.assertEquals(50150.01, resultDTO.getResultPercent());
	}

	@Test
	public void SumAtTheEnd() {
		Assertions.assertEquals(1062150.01, resultDTO.getSumAtTheEnd());
	}

	@Test
	public void SumTax() {
		Assertions.assertEquals(6519.51, resultDTO.getSumTax());
	}

}
