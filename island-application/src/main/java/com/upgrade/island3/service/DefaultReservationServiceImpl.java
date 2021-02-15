package com.upgrade.island3.service;

import com.upgrade.island3.converter.ModelDtoConverter;
import com.upgrade.island3.converter.ReservationModel;
import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.dto.response.ReservationResponseDto;
import com.upgrade.island3.model.IslandUser;
import com.upgrade.island3.model.Reservation;
import com.upgrade.island3.repository.ReservationRepository;
import com.upgrade.island3.utils.LocalDateRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * DefaultReservationServiceImpl
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Service
@Slf4j
public class DefaultReservationServiceImpl implements ReservationService {

    private AvailabilityService availabilityService;
    private StatusService statusService;
    private IslandUserService islandUserService;
    private SpotService spotService;
    private ReservationRepository reservationRepository;
    private MessageSource messageSource;
    private ModelDtoConverter modelDtoConverter;

    @Autowired
    public DefaultReservationServiceImpl(AvailabilityService availabilityService,
                                         StatusService statusService,
                                         IslandUserService islandUserService,
                                         SpotService spotService,
                                         ReservationRepository reservationRepository,
                                         MessageSource messageSource,
                                         ModelDtoConverter modelDtoConverter
    ) {
        this.availabilityService = availabilityService;
        this.statusService = statusService;
        this.islandUserService = islandUserService;
        this.spotService = spotService;
        this.reservationRepository = reservationRepository;
        this.messageSource = messageSource;
        this.modelDtoConverter = modelDtoConverter;
    }

    @Override
    @Transactional(propagation = REQUIRED, timeout = 5)
    public ReservationResponseDto makeReservation(ReservationRequestDto reservationRequest) {
        log.info("reservationRequest {}", reservationRequest);

        LocalDate fromDate = reservationRequest.getRequestDates().getArrivalDate();
        LocalDate toDate = reservationRequest.getRequestDates().getDepartureDate();

        //List<Availability> listOfAvailabilities = this.availabilityService.findAvailability(fromDate, toDate);

        //TODO validate reservation can be made
//        if (site is full) {
//
//            log.info("Unable to make the reservation.Camping is full");
//            throw new IslandApplicationException(
//                    messageSource.getMessage("island.exception.camping.full", null, null, Locale.getDefault())
//            );
//        }

        IslandUser islandUser = new IslandUser();
        islandUser.setFirstName(reservationRequest.getFirstName());
        islandUser.setLastName(reservationRequest.getLastName());
        islandUser.setEmail(reservationRequest.getEmail());
        this.islandUserService.save(islandUser);

        Reservation reservation = new Reservation();
        reservation.setBookingUuid(UUID.randomUUID().toString());
        reservation.setStatus(this.statusService.getReserved());
        reservation.setUser(islandUser);
        reservation.setSpot(this.spotService.randomSpot());
        reservation.setPrice(0);
        reservation.setArrivalDate(fromDate);
        reservation.setDepartureDate(toDate);
        reservation.setCreationDate(LocalDate.now(LocalDateRange.UTC));
        reservation.setUpdateDate(LocalDate.now(LocalDateRange.UTC));

        log.info("About to save reservation {}", reservation);
        this.reservationRepository.save(reservation);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setBookingUuid(reservation.getBookingUuid());
        return reservationResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationModel> fetchAllReservations() {
        log.info("About to fetch all reservations");

        List<Reservation> listOfReservations = this.reservationRepository.findAll();
        log.info("All Reservation : {}", listOfReservations.stream().map(Object::toString).collect(Collectors.joining(",")));

        return listOfReservations.
                stream().
                map(this.modelDtoConverter::reservationEntityToReservationModel).
                collect(Collectors.toList());
    }

}
