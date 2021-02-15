package com.upgrade.island3.converter;

import lombok.*;

import java.time.LocalDate;

/**
 * ReservationModel
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReservationModel {
    private String bookingUuid;
    private StatusModel status;
    private IslandUserModel user;
    private SpotModel spot;
    private long price;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
}
