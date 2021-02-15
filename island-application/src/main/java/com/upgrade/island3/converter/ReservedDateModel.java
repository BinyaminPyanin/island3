package com.upgrade.island3.converter;

import lombok.*;

/**
 * ReservedDateModel
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReservedDateModel {
    private AvailabilityModel availability;
    private ReservationModel reservation;
}
