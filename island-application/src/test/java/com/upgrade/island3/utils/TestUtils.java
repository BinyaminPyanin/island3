package com.upgrade.island3.utils;

import com.upgrade.island3.converter.ReservationModel;
import com.upgrade.island3.dto.request.RequestDatesDto;
import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.model.*;
import org.assertj.core.util.Lists;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUtils {
    private static final Availability FEB_01_2021 = Availability.builder().
            id(1).
            availableDate(LocalDate.of(2021, 02, 1)).
            status(Status.builder().
                    id(1).
                    code(StatusReservation.AVAILABLE.name()).
                    description("available").
                    build()).
            reservedDates(Lists.emptyList()).
            build();
    private static final Availability FEB_02_2021 = Availability.builder().
            id(1).
            availableDate(LocalDate.of(2021, 02, 2)).
            status(Status.builder().
                    id(1).
                    code(StatusReservation.AVAILABLE.name()).
                    description("available").
                    build()).
            reservedDates(Lists.emptyList()).
            build();
    private static final Availability FEB_03_2021 = Availability.builder().
            id(1).
            availableDate(LocalDate.of(2021, 02, 2)).
            status(Status.builder().
                    id(1).
                    code(StatusReservation.AVAILABLE.name()).
                    description("available").
                    build()).
            reservedDates(Lists.emptyList()).
            build();

    private static final List<Availability> availabilitiesList = new ArrayList<>();
    private static final List<LocalDate> localDatesList = new ArrayList<>();

    private static final String firstName = "D1";
    private static final String lastName = "W1";
    private static final String email = "dim12345@yahoo.com";
    public static final String arrivalDate = "2021-02-16";
    public static final String departureDate = "2021-02-18";

    private static final RequestDatesDto REQUEST_DATES_DTO = RequestDatesDto.builder().
            arrivalDate(LocalDate.parse(arrivalDate)).
            departureDate(LocalDate.parse(departureDate)).
            build();

    private static final ReservationRequestDto RESERVATION_REQUEST_DTO = ReservationRequestDto.builder().
            requestDates(REQUEST_DATES_DTO).
            firstName(firstName).
            lastName(lastName).
            email(email).
            build();

    private static final IslandUser islandUser = IslandUser.builder().
            firstName(firstName).
            lastName(lastName).
            email(email).
            build();

    private static final Status reserved = Status.builder().
            id(2).
            code(StatusReservation.RESERVED.name()).
            description("reserved").
            build();

    private static final Spot spot1 = Spot.builder().
            id(1).
            name("spot1").
            description("spot1 description").
            build();

    private static final Spot spot2 = Spot.builder().
            id(2).
            name("spot2").
            description("spot2 description").
            build();

    private static final Reservation reservation = Reservation.builder().
            bookingUuid(UUID.randomUUID().toString()).
            status(reserved).
            user(islandUser).
            spot(spot1).
            price(0).
            arrivalDate(LocalDate.parse(arrivalDate)).
            departureDate(LocalDate.parse(departureDate)).
            creationDate(LocalDate.now(LocalDateRange.UTC)).
            updateDate(LocalDate.now(LocalDateRange.UTC)).
            build();

    public static List<Availability> getAvailabilityList(){
        availabilitiesList.add(FEB_01_2021);
        availabilitiesList.add(FEB_02_2021);
        availabilitiesList.add(FEB_03_2021);

        return availabilitiesList;
    }

    public static List<LocalDate> getLocalDatesList(){
        localDatesList.add(LocalDate.parse(arrivalDate));
        localDatesList.add(LocalDate.parse(departureDate));

        return localDatesList;
    }

    public static IslandUser getIslandUser(){return islandUser;};

    public static Reservation getReservation(){return reservation;};

    public static RequestDatesDto getRequestDatesDto(){return REQUEST_DATES_DTO;};

    public static ReservationRequestDto getReservationRequestDto(){return RESERVATION_REQUEST_DTO;};

    public static LocalDate getArrivalDate(){return LocalDate.parse(arrivalDate);};

    public static LocalDate getDepartureDate(){return LocalDate.parse(departureDate);};

    public static Status getReserved(){return reserved;};

    public static Spot getSpot(){return spot2;};

}
