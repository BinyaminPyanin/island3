package com.upgrade.island3.converter;

import lombok.*;

import java.time.LocalDate;

/**
 * AvailabilityModel
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AvailabilityModel {
    private LocalDate availableDate;
    private StatusModel status;
}
