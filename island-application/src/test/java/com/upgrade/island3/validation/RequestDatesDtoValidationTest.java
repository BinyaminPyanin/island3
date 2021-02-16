package com.upgrade.island3.validation;

import com.upgrade.island3.dto.request.RequestDatesDto;
import com.upgrade.island3.utils.LocalDateRange;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class RequestDatesDtoValidationTest {

    private Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @SneakyThrows
    void whenReservationNotInFutureThenErrorMessageShowed() {
        log.info("Running whenReservationNotInFutureThenErrorMessageShowed");

        RequestDatesDto requestDatesDto = RequestDatesDto.
                builder().
                arrivalDate(LocalDate.now(LocalDateRange.UTC)).
                departureDate(LocalDate.now(LocalDateRange.UTC).plusDays(1)).
                build();

        Set<ConstraintViolation<RequestDatesDto>> setOfConstraints = validator.validate(requestDatesDto);

        assertTrue(setOfConstraints.size() > 0);

    }

    @Test
    @SneakyThrows
    void whenReservationLengthInDaysLessThenThreeDaysThenNoErrorMessageShowed() {
        log.info("Running whenReservationLengthInDaysLessThenThreeDaysThenNoErrorMessageShowed");

        RequestDatesDto requestDatesDto = RequestDatesDto.
                builder().
                arrivalDate(LocalDate.now(LocalDateRange.UTC).plusDays(2)).
                departureDate(LocalDate.now(LocalDateRange.UTC).plusDays(5)).
                build();

        Set<ConstraintViolation<RequestDatesDto>> setOfConstraints = validator.validate(requestDatesDto);

        assertTrue(setOfConstraints.isEmpty());

    }

    @Test
    @SneakyThrows
    void whenReservationLengthInDaysMoreThenThreeDaysThenErrorMessagePresent() {
        log.info("Running whenReservationLengthInDaysMoreThenThreeDaysThenErrorMessagePresent");

        RequestDatesDto requestDatesDto = RequestDatesDto.
                builder().
                arrivalDate(LocalDate.now(LocalDateRange.UTC).plusDays(2)).
                departureDate(LocalDate.now(LocalDateRange.UTC).plusDays(6)).
                build();

        Set<ConstraintViolation<RequestDatesDto>> setOfConstraints = validator.validate(requestDatesDto);

        assertTrue(setOfConstraints.size() == 1);
        assertTrue(setOfConstraints
                .stream()
                .findFirst()
                .get().getMessage()
                .equals("{island.validation.reservation.dates.length.exceeded}"));

    }

}
