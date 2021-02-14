package com.upgrade.island3.service;

import com.upgrade.island3.model.Availability;

import java.time.LocalDate;
import java.util.List;

/**
 * AvailabilityService
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface AvailabilityService {
    List<Availability> findAvailability(LocalDate fromDate, LocalDate toDate);

    List<Availability> findAvailability();

    List<LocalDate> findAvailabilityDates(LocalDate fromDate, LocalDate toDate);

    List<LocalDate> findAvailabilityDates();
}
