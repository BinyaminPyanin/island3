package com.upgrade.island3.dto.request;

import com.upgrade.island3.validation.ReservationLengthInDays;
import com.upgrade.island3.validation.ReservationWithinOneMonth;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * RequestDatesDto
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Data
@Builder
//@ReservationWithinOneMonth(message = "{island.validation.reservation.dates.within.one.month}")
@ReservationLengthInDays(message = "{island.validation.reservation.dates.length.exceeded}")
public class RequestDatesDto {
    @NotNull
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate arrivalDate;
    @NotNull
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureDate;
}
