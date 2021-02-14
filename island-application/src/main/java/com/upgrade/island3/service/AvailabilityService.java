package com.upgrade.island3.service;

import java.time.LocalDate;
import java.util.List;

/**
 * AvailabilityService
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface AvailabilityService {
    List<LocalDate> findAvailability(LocalDate startDate, LocalDate endDate);

    List<LocalDate> findAvailability();
}
