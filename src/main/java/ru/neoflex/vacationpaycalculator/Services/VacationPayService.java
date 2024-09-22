package ru.neoflex.vacationpaycalculator.Services;

import org.apache.el.stream.Stream;
import org.springframework.stereotype.Service;
import ru.neoflex.vacationpaycalculator.DTO.VacationPayResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class VacationPayService {

    private final static double AVG_DAY_PER_MONTH = 29.3;
    private final static int ACTUAL_YEAR = LocalDate.now().getYear();
    private final static List<LocalDate> LIST_HOLIDAYS = holidays();

    public VacationPayResponse calculateVacationPayDetail(BigDecimal averageSalary,
                                                    int totalVacationDays,
                                                    Optional<LocalDate> startDay) {
        int totalDays = totalVacationDays;
        if (startDay.isPresent()) {
            totalDays = excludeHolidays(startDay.get(), totalVacationDays);
        }

        BigDecimal vacationPay = calculateVacationPay(averageSalary, totalDays);
        return new VacationPayResponse("Сумма отпускных:", vacationPay);
    }

    private int excludeHolidays(LocalDate startDay, int totalDays) {
        return (int) startDay.datesUntil(startDay.plusDays(totalDays))
                .filter(tempDate -> !LIST_HOLIDAYS.contains(tempDate))
                .count();
    }

    private BigDecimal calculateVacationPay(BigDecimal averageSalary, int totalVacationDays) {
        BigDecimal avgSalaryPerDay = averageSalary.divide(BigDecimal.valueOf(AVG_DAY_PER_MONTH), 2, RoundingMode.HALF_EVEN);
        return  avgSalaryPerDay.multiply(BigDecimal.valueOf(totalVacationDays));
    }

    private static List<LocalDate> holidays() {
        return List.of(
                LocalDate.of(ACTUAL_YEAR, Month.JANUARY, 1),
                LocalDate.of(ACTUAL_YEAR, Month.JANUARY, 2),
                LocalDate.of(ACTUAL_YEAR, Month.JANUARY, 3),
                LocalDate.of(ACTUAL_YEAR, Month.JANUARY, 4),
                LocalDate.of(ACTUAL_YEAR, Month.JANUARY, 5),
                LocalDate.of(ACTUAL_YEAR, Month.JANUARY, 6),
                LocalDate.of(ACTUAL_YEAR, Month.JANUARY, 7),
                LocalDate.of(ACTUAL_YEAR, Month.JANUARY, 8),
                LocalDate.of(ACTUAL_YEAR, Month.FEBRUARY, 23),
                LocalDate.of(ACTUAL_YEAR, Month.MARCH, 8),
                LocalDate.of(ACTUAL_YEAR, Month.APRIL, 29),
                LocalDate.of(ACTUAL_YEAR, Month.APRIL, 30),
                LocalDate.of(ACTUAL_YEAR, Month.MAY, 1),
                LocalDate.of(ACTUAL_YEAR, Month.MAY, 9),
                LocalDate.of(ACTUAL_YEAR, Month.MAY, 10),
                LocalDate.of(ACTUAL_YEAR, Month.JUNE, 12),
                LocalDate.of(ACTUAL_YEAR, Month.NOVEMBER, 4),
                LocalDate.of(ACTUAL_YEAR, Month.DECEMBER, 30),
                LocalDate.of(ACTUAL_YEAR, Month.JANUARY, 31)
        );
    }
}
