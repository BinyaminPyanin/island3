package com.upgrade.island3.validation;

import com.upgrade.island3.dto.request.RequestDatesDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * ReservationLengthInDaysValidator
 *
 * @author Binyamin Pyanin
 * @since 20210215
 */
public class ReservationLengthInDaysValidator implements ConstraintValidator<ReservationLengthInDays, RequestDatesDto> {

    @Override
    public void initialize(ReservationLengthInDays constraint) {
    }

    public boolean isValid(RequestDatesDto reservationDates, ConstraintValidatorContext context) {

        LocalDate fromDate = reservationDates.getArrivalDate();
        LocalDate toDate = reservationDates.getDepartureDate();

        return DAYS.between(fromDate, toDate) <= 3;
    }
}