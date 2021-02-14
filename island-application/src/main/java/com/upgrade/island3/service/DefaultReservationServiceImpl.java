package com.upgrade.island3.service;

import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.dto.response.ReservationResponseDto;
import com.upgrade.island3.model.Availability;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * DefaultReservationServiceImpl
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Service
@Slf4j
public class DefaultReservationServiceImpl implements ReservationService {

    private AvailabilityService availabilityService;

    @Autowired
    public DefaultReservationServiceImpl(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @Override
    @Transactional(propagation = REQUIRED, timeout = 5)
    public ReservationResponseDto makeReservation(ReservationRequestDto reservationRequest) {
        log.info("reservationRequest {}", reservationRequest);

        //TODO rewrite call DB
        List<Availability> listOfAvailabilities =
                this.availabilityService.findAvailability(
                        reservationRequest.getRequestDates().getArrivalDate(),
                        reservationRequest.getRequestDates().getDepartureDate());


        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setBookingUuid(UUID.randomUUID().toString());
        return reservationResponseDto;
    }
}
