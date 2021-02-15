package com.upgrade.island3.service;

import com.upgrade.island3.converter.ReservationModel;
import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.dto.response.ReservationResponseDto;

import java.util.List;

/**
 * ReservationService
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface ReservationService {
    ReservationResponseDto makeReservation(ReservationRequestDto reservationUserDto);

    List<ReservationModel> fetchAllReservations();

    ReservationModel fetchReservationByBookingUuid(String bookingUuid);

    void cancelReservation(String bookingUuid);

    ReservationResponseDto modifyReservation(ReservationRequestDto reservationRequestDto, String bookingUuid);
}
