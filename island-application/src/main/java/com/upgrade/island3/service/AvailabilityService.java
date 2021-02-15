package com.upgrade.island3.service;

import com.upgrade.island3.model.Availability;
import com.upgrade.island3.model.Status;

import java.time.LocalDate;
import java.util.List;

/**
 * AvailabilityService
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
public interface AvailabilityService {
    List<Availability> findAvailability(LocalDate fromDate, LocalDate toDate, Status status);

    List<Availability> findAvailability();

    List<LocalDate> findAvailabilityDates(LocalDate fromDate, LocalDate toDate);

    List<LocalDate> findAvailabilityDates();
}
