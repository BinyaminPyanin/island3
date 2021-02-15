package com.upgrade.island3.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * ReservationResponseDto
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Data
@Builder
public class ReservationResponseDto {
    private String bookingUuid;
}
