package ru.neoflex.vacationpaycalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.neoflex.vacationpaycalculator.Services.VacationPayService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest(classes = VacationPayCalculatorApplication.class)
class VacationPayCalculatorApplicationTests {

	@Autowired
	private VacationPayService vacationPayService;

	@DisplayName("Проверка расчета суммы отпускных")
	@ParameterizedTest(name = "Средний заработок: {0}. Кол-во дней: {1}. Ожидаемые отпускные: {2}")
	@CsvSource({"100000,28,95563.16", "150000,14,71672.30"})
	public void calculateVacationPayRaw(int averageSalary, int totalVacationDays, double resultVacationPay) {
		assertCalculation(BigDecimal.valueOf(averageSalary), totalVacationDays, Optional.empty(), BigDecimal.valueOf(resultVacationPay));
	}

	@DisplayName("Проверка расчета суммы отпускных с учетом праздничных дней")
	@ParameterizedTest(name = "Средний заработок: {0}. Кол-во дней: {1}. Начало отпуска: {2}. Ожидаемые отпускные: {3}")
	@CsvSource({"100000,28,2024-01-01,68259.40", "150000,14,2024-05-01,56313.95"})
	public void calculateVacationPayExcludeHolidays(int averageSalary, int totalVacationDays, String startDay, double resultVacationPay) {

		LocalDate day = LocalDate.parse(startDay);
		assertCalculation(BigDecimal.valueOf(averageSalary), totalVacationDays, Optional.of(day), BigDecimal.valueOf(resultVacationPay));
	}

	private void assertCalculation(BigDecimal averageSalary, int totalVacationDays,
								   Optional<LocalDate> startDay, BigDecimal resultVacationPay) {

		BigDecimal vacationPay = vacationPayService
				.calculateVacationPayDetail(averageSalary, totalVacationDays, startDay)
				.getVacationPay();
		Assertions.assertEquals(vacationPay.stripTrailingZeros(),
				resultVacationPay.stripTrailingZeros(),
				"Неверный расчет: " + vacationPay);
	}
}
