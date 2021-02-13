package com.upgrade.island3.repository;

import com.upgrade.island3.model.Availability;

import java.time.LocalDate;
import java.util.List;

public interface CustomizedAvailabilityRepository {

    List<Availability> getAvailableDatesByRange(LocalDate fromDate, LocalDate toDate);

}
