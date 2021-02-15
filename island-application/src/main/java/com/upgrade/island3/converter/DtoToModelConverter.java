package com.upgrade.island3.converter;

import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.model.IslandUser;
import com.upgrade.island3.model.Reservation;
import com.upgrade.island3.model.Spot;
import com.upgrade.island3.model.Status;
import com.upgrade.island3.utils.LocalDateRange;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DtoToModelConverter
 *
 * @author Binyamin Pyanin
 * @since 20210215
 */
@Component
public class DtoToModelConverter {

    public Reservation convertReservationRequestDtoToReservationEntity(ReservationRequestDto reservationRequest,
                                                                       IslandUser islandUser,
                                                                       Status status,
                                                                       Spot spot) {
        LocalDate fromDate = reservationRequest.getRequestDates().getArrivalDate();
        LocalDate toDate = reservationRequest.getRequestDates().getDepartureDate();

        return Reservation.builder().
                bookingUuid(UUID.randomUUID().toString()).
                status(status).
                user(islandUser).
                spot(spot).
                price(0).
                arrivalDate(fromDate).
                departureDate(toDate).
                creationDate(LocalDate.now(LocalDateRange.UTC)).
                updateDate(LocalDate.now(LocalDateRange.UTC)).
                build();
    }

    public IslandUser convertReservationRequestDtoToIslandUserEntity(ReservationRequestDto reservationRequest) {
        return IslandUser.builder().
                firstName(reservationRequest.getFirstName()).
                lastName(reservationRequest.getLastName()).
                email(reservationRequest.getEmail()).
                build();
    }

}
