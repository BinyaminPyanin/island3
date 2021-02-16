package com.upgrade.island3.rest;

import com.upgrade.island3.utils.AbstractIntegrationTest;
import com.upgrade.island3.converter.ReservationModel;
import com.upgrade.island3.service.ReservationService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class ReservationControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    @SneakyThrows
    void whenFetchAllReservationsMethodIsCalledWthNoReservationsInDbThenEmptyListReturned() {
        log.info("Running whenFetchAllReservationsMethodIsCalledWthNoReservationsInDbThenEmptyListReturned");

        List<ReservationModel> reservationModelList = this.reservationService.fetchAllReservations();

        assertTrue(reservationModelList.isEmpty());

    }

}
