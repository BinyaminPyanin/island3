package com.upgrade.island3.service;

import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.dto.response.ReservationResponseDto;

/**
 * ReservationService
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface ReservationService {
    ReservationResponseDto makeReservation(ReservationRequestDto reservationUserDto);
}
