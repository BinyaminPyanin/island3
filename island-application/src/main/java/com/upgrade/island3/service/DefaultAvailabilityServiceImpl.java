package com.upgrade.island3.service;

import com.upgrade.island3.exception.IslandApplicationException;
import com.upgrade.island3.model.Availability;
import com.upgrade.island3.model.Status;
import com.upgrade.island3.repository.AvailabilityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
    private StatusService statusService;
    private MessageSource messageSource;

    //TODO why?
    @Value("${availability.default.date.range.days:31}")
    private int defaultDateRangeDays;

    @Autowired
    public DefaultAvailabilityServiceImpl(AvailabilityRepository availabilityRepository,
                                          StatusService statusService,
                                          MessageSource messageSource) {
        this.availabilityRepository = availabilityRepository;
        this.statusService = statusService;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public List<Availability> findAvailability(LocalDate fromDate, LocalDate toDate, Status status) {
        return getAvailability(fromDate, toDate, status);
    }

    @Override
    @Transactional
    public List<Availability> findAvailability() {
        return getAllAvailabilities();
    }

    @Override
    @Transactional
    public List<LocalDate> findAvailabilityDates(LocalDate fromDate, LocalDate toDate) {
        log.info("Fetching available dates for requested date range from [{}] to [{}]", fromDate, toDate);

        List<Availability> listOfAvailabilities = getAvailability(fromDate, toDate, this.statusService.getAvailable());

        return listOfAvailabilities.
                stream().
                map(Availability::getAvailableDate).
                collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<LocalDate> findAvailabilityDates() {
        log.info("Fetching all available dates");

        List<Availability> listOfAvailabilities = getAllAvailabilities();
        return listOfAvailabilities.stream().map(Availability::getAvailableDate).collect(Collectors.toList());
    }

    @Override
    public void saveAll(List<Availability> availabilityList) {
        log.info("Saving availabilities : {}",
                availabilityList.stream().map(Object::toString).collect(Collectors.joining(",")));
        this.availabilityRepository.saveAll(availabilityList);
    }

    @Override
    public Availability findAvailabilityByDate(LocalDate localDate) {
        log.info("Fetching availability by date {}", localDate);

        Optional<Availability> availability =
                Optional.ofNullable(this.availabilityRepository.findAvailabilityByDate(localDate));
        if (!availability.isPresent()) {
            log.info("Availability with date {} not found", localDate);
            throw new IslandApplicationException(
                    messageSource.getMessage("island.exception.availability.no.date.available", null, null,
                            Locale.getDefault()));
        }
        return availability.get();
    }

    private List<Availability> getAvailability(LocalDate fromDate, LocalDate toDate, Status status) {
        log.info("Fetching availabilities for requested date range from [{}] to [{}]", fromDate, toDate);

        List<Availability> listOfAvailabilities = availabilityRepository.getAvailableDatesByRange(fromDate, toDate, status);
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
