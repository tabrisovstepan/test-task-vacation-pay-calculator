package ru.neoflex.vacationpaycalculator.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Getter
public class VacationPayResponse {
    private String responseMessage;
    private BigDecimal vacationPay;
}
