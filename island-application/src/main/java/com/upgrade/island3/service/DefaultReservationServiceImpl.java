package com.upgrade.island3.service;

import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.dto.response.ReservationResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * DefaultReservationServiceImpl
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Service
@Slf4j
public class DefaultReservationServiceImpl implements ReservationService {
    @Override
    public ReservationResponseDto makeReservation(ReservationRequestDto reservationUserDto) {

        //TODO rewrite call DB
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setBookingUuid(UUID.randomUUID().toString());
        return reservationResponseDto;
    }
}
