package com.upgrade.island3.converter;

import com.upgrade.island3.model.*;
import org.springframework.stereotype.Component;

/**
 * ModelToDtoConverter
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Component
public class ModelToDtoConverter {

    public AvailabilityModel availabilityEntityToAvailabilityModel(Availability availability) {
        return AvailabilityModel.builder().
                availableDate(availability.getAvailableDate()).
                build();
    }

    public ReservationModel reservationEntityToReservationModel(Reservation reservation) {
        return ReservationModel.builder().
                bookingUuid(reservation.getBookingUuid()).
                status(statusEntityToStatusModel(reservation.getStatus())).
                user(islandUserEntityToIslandUserModel(reservation.getUser())).
                spot(spotEntityToSpotModel(reservation.getSpot())).
                price(reservation.getPrice()).
                arrivalDate(reservation.getArrivalDate()).
                departureDate(reservation.getDepartureDate()).
                build();
    }

    public ReservedDateModel reservedDateEntityToReservedDateModel(ReservedDate reservedDate) {
        return ReservedDateModel.builder().
                availability(availabilityEntityToAvailabilityModel(reservedDate.getAvailability())).
                reservation(reservationEntityToReservationModel(reservedDate.getReservation())).
                build();
    }

    public IslandUserModel islandUserEntityToIslandUserModel(IslandUser islandUser) {
        return IslandUserModel.builder().
                firstName(islandUser.getFirstName()).
                lastName(islandUser.getLastName()).
                email(islandUser.getEmail()).
                build();
    }

    public SpotModel spotEntityToSpotModel(Spot spot) {
        return SpotModel.builder().
                name(spot.getName()).
                description(spot.getDescription()).
                build();
    }

    public StatusModel statusEntityToStatusModel(Status status) {
        return StatusModel.builder().
                code(status.getCode()).
                description(status.getDescription()).
                build();
    }

}
