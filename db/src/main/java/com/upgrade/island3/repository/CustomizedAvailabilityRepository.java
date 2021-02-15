package com.upgrade.island3.repository;

import com.upgrade.island3.model.Availability;
import com.upgrade.island3.model.Status;

import java.time.LocalDate;
import java.util.List;

public interface CustomizedAvailabilityRepository {

    List<Availability> getAvailableDatesByRange(LocalDate fromDate, LocalDate toDate, Status status);

    List<Availability> getAllAvailableDates();

    Availability findAvailabilityByDate(LocalDate localDate);
}
