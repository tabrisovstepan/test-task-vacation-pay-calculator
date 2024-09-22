package ru.neoflex.vacationpaycalculator.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.vacationpaycalculator.DTO.VacationPayResponse;
import ru.neoflex.vacationpaycalculator.Services.VacationPayService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@RestController
public class VacationPayController {

    @Autowired
    private VacationPayService vacationPayService;

    @GetMapping("/calculate")
    public VacationPayResponse calculate(@RequestParam BigDecimal averageSalary,
                                         @RequestParam int totalVacationDays,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                             Optional<LocalDate> startDay) {

        return vacationPayService.calculateVacationPayDetail(averageSalary, totalVacationDays, startDay);
    }
}
