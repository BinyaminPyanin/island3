package com.upgrade.island3.service;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {

    List<LocalDate> findAvailability(LocalDate startDate, LocalDate endDate);

}
