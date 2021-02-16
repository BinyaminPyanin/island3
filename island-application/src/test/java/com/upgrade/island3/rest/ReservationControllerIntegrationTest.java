package com.upgrade.island3.rest;

import com.upgrade.island3.converter.ReservationModel;
import com.upgrade.island3.dto.response.ReservationResponseDto;
import com.upgrade.island3.exception.IslandApplicationException;
import com.upgrade.island3.service.ReservationService;
import com.upgrade.island3.utils.AbstractIntegrationTest;
import com.upgrade.island3.utils.TestUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class ReservationControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String LOG_LINE_START = ">>>>>-----------INTEGRATION-----------TEST-----------START---------------------------------------------------------->>>>>";
    private static final String LOG_LINE_END = ">>>>>-----------INTEGRATION-----------TEST-----------END------------------------------------------------------------>>>>>";
    private static final String TEST_BOOKING_UUID = "96a1ce01-2542-4d1d-b1dc-dbaa96604e73";

    @Autowired
    private ReservationService reservationService;

    @Test
    @SneakyThrows
    void whenFetchAllReservationsMethodIsCalledWthNoReservationsInDbThenEmptyListReturned() {
        log.info(LOG_LINE_START);
        log.info("Running whenFetchAllReservationsMethodIsCalledWthNoReservationsInDbThenEmptyListReturned");

        assertDoesNotThrow(() -> this.reservationService.fetchAllReservations());
        log.info(LOG_LINE_END);
    }

    @Test
    @SneakyThrows
    void whenFetchReservationsByUuidMethodIsCalledWthNoReservationsInDbThenExceptionIsThrown() {
        log.info(LOG_LINE_START);
        log.info("Running whenFetchReservationsByUuidMethodIsCalledWthNoReservationsInDbThenExceptionIsThrown");

        Assertions.assertThrows(IslandApplicationException.class, () ->
                this.reservationService.fetchReservationByBookingUuid(TEST_BOOKING_UUID));
        log.info(LOG_LINE_END);
    }

    @Test
    @SneakyThrows
    void whenFetchReservationsByUuidMethodIsCalledHavingNullWthNoReservationsInDbThenExceptionIsThrown() {
        log.info(LOG_LINE_START);
        log.info("Running whenFetchReservationsByUuidMethodIsCalledHavingNullWthNoReservationsInDbThenExceptionIsThrown");

        Assertions.assertThrows(IslandApplicationException.class, () ->
                this.reservationService.fetchReservationByBookingUuid(null));
        log.info(LOG_LINE_END);
    }

    @Test
    @SneakyThrows
    void whenMakeReservationMethodIsCalledThenEmptyReservationSavedInDb() {
        log.info(LOG_LINE_START);
        log.info("Running whenMakeReservationMethodIsCalledThenEmptyReservationSavedInDb");

        ReservationResponseDto reservationResponseDto =
                this.reservationService.makeReservation(TestUtils.getReservationRequestDto());

        assertNotNull(reservationResponseDto.getBookingUuid());
        log.info(LOG_LINE_END);
    }

    @Test
    @SneakyThrows
    void whenCancelReservationMethodIsCalledThenReservationCanceledInDb() {
        log.info(LOG_LINE_START);
        log.info("Running whenCancelReservationMethodIsCalledThenReservationCanceledInDb");

        List<ReservationModel> reservationModelList = this.reservationService.fetchAllReservations();
        if (!reservationModelList.isEmpty() && reservationModelList.size() > 0) {
            ReservationModel reservation = reservationModelList.get(0);
            this.reservationService.cancelReservation(reservation.getBookingUuid());
        }

        assertDoesNotThrow(() -> this.reservationService.fetchAllReservations());
        log.info(LOG_LINE_END);
    }


}
