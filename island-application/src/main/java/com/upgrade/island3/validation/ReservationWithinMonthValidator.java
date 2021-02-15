package com.upgrade.island3.validation;

import com.upgrade.island3.dto.request.RequestDatesDto;
import com.upgrade.island3.utils.LocalDateRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * ReservationMaxOneMonthValidator
 *
 * @author Binyamin Pyanin
 * @since 20210215
 */
public class ReservationWithinMonthValidator implements ConstraintValidator<ReservationWithinOneMonth, RequestDatesDto> {

    public void initialize(ReservationLengthInDays constraint) {
    }

    public boolean isValid(RequestDatesDto reservationDates, ConstraintValidatorContext context) {

        LocalDate fromDate = reservationDates.getArrivalDate();

        return LocalDate.now(LocalDateRange.UTC).isAfter(fromDate.minusMonths(1)) &&
                LocalDate.now(LocalDateRange.UTC).isBefore(fromDate.minusDays(1));

    }
}