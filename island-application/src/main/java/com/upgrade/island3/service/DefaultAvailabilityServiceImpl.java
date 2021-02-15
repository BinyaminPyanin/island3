package com.upgrade.island3.service;

import com.upgrade.island3.model.Availability;
import com.upgrade.island3.repository.AvailabilityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DefaultAvailabilityServiceImpl
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Service
@Slf4j
public class DefaultAvailabilityServiceImpl implements AvailabilityService {

    private AvailabilityRepository availabilityRepository;

    //TODO why?
    @Value("${availability.default.date.range.days:31}")
    private int defaultDateRangeDays;

    @Autowired
    public DefaultAvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Availability> findAvailability(LocalDate fromDate, LocalDate toDate) {
        return getAvailability(fromDate, toDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Availability> findAvailability() {
        return getAllAvailabilities();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocalDate> findAvailabilityDates(LocalDate fromDate, LocalDate toDate) {
        log.info("Fetching available dates for requested date range from [{}] to [{}]", fromDate, toDate);

        List<Availability> listOfAvailabilities = getAvailability(fromDate, toDate);

        return listOfAvailabilities.
                stream().
                map(Availability::getAvailableDate).
                collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocalDate> findAvailabilityDates() {
        log.info("Fetching all available dates");

        List<Availability> listOfAvailabilities = getAllAvailabilities();
        return listOfAvailabilities.stream().map(Availability::getAvailableDate).collect(Collectors.toList());
    }

    private List<Availability> getAvailability(LocalDate fromDate, LocalDate toDate) {
        log.info("Fetching availabilities for requested date range from [{}] to [{}]", fromDate, toDate);

        List<Availability> listOfAvailabilities = availabilityRepository.getAvailableDatesByRange(fromDate, toDate);
        log.info("Availabilities for requested date range from [{}] to [{}] : {}",
                fromDate, toDate,
                listOfAvailabilities.
                        stream().
                        map(Object::toString).
                        collect(Collectors.joining(",")));

        if (listOfAvailabilities.isEmpty()) {
            return Collections.emptyList();
        }

        return listOfAvailabilities;
    }

    private List<Availability> getAllAvailabilities() {
        log.info("Fetching all availabilities");

        List<Availability> listOfAvailabilities = availabilityRepository.getAllAvailableDates();
        log.info("All availabilities : {}", listOfAvailabilities.stream().map(Object::toString).collect(Collectors.joining(",")));

        if (listOfAvailabilities.isEmpty()) {
            return Collections.emptyList();
        }

        return listOfAvailabilities;
    }

}
