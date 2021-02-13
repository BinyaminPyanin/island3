package com.upgrade.island3.service;

import com.upgrade.island3.model.Availability;
import com.upgrade.island3.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultAvailabilityServiceImpl implements AvailabilityService {


    private AvailabilityRepository availabilityRepository;

    @Value("${availability.default.date.range.days:31}")
    private int defaultDateRangeDays;

    @Autowired
    public DefaultAvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public List<LocalDate> findAvailability(LocalDate fromDate, LocalDate toDate) {
        List<Availability> listOfAvailabilities = availabilityRepository.getAvailableDatesByRange(fromDate, toDate);

        if (null == listOfAvailabilities || listOfAvailabilities.isEmpty()) {
            return Collections.emptyList();
        }

        return listOfAvailabilities.stream().map(Availability::getAvailableDate).collect(Collectors.toList());
    }


}
