package com.upgrade.island3.service;

import com.upgrade.island3.converter.DtoToModelConverter;
import com.upgrade.island3.converter.ModelToDtoConverter;
import com.upgrade.island3.converter.ReservationModel;
import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.dto.response.ReservationResponseDto;
import com.upgrade.island3.exception.IslandApplicationException;
import com.upgrade.island3.model.*;
import com.upgrade.island3.repository.ReservationRepository;
import com.upgrade.island3.utils.LocalDateRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
    private ModelToDtoConverter modelDtoConverter;
    private DtoToModelConverter dtoToModelConverter;

    @Autowired
    public DefaultReservationServiceImpl(AvailabilityService availabilityService,
                                         StatusService statusService,
                                         IslandUserService islandUserService,
                                         SpotService spotService,
                                         ReservationRepository reservationRepository,
                                         MessageSource messageSource,
                                         ModelToDtoConverter modelDtoConverter,
                                         DtoToModelConverter dtoToModelConverter
    ) {
        this.availabilityService = availabilityService;
        this.statusService = statusService;
        this.islandUserService = islandUserService;
        this.spotService = spotService;
        this.reservationRepository = reservationRepository;
        this.messageSource = messageSource;
        this.modelDtoConverter = modelDtoConverter;
        this.dtoToModelConverter = dtoToModelConverter;
    }

    @Override
    @Transactional(propagation = REQUIRED, timeout = 5)
    public ReservationResponseDto makeReservation(ReservationRequestDto reservationRequest) {
        log.info("reservationRequest {}", reservationRequest);

        LocalDate fromDate = reservationRequest.getRequestDates().getArrivalDate();
        LocalDate toDate = reservationRequest.getRequestDates().getDepartureDate();

        List<Availability> availableDates = this.availabilityService.findAvailability(fromDate, toDate, this.statusService.getAvailable());
        if (availableDates.isEmpty()) {
            log.info("Unable to complete the reservation.No available dates between {} and {}", fromDate, toDate);
            throw new IslandApplicationException(
                    messageSource.getMessage("island.exception.reservation.no.available.dates", null, null,
                            Locale.getDefault()));
        }

        IslandUser islandUser = this.dtoToModelConverter.convertReservationRequestDtoToIslandUserEntity(
                reservationRequest);
        this.islandUserService.save(islandUser);

        Status reserved = this.statusService.getReserved();
        Reservation reservation =
                this.dtoToModelConverter.convertReservationRequestDtoToReservationEntity(
                        reservationRequest, islandUser, reserved, this.spotService.randomAvailableSpot());
        reservation.getSpot().setStatus(reserved);

        log.info("About to save reservation {}", reservation);
        this.reservationRepository.save(reservation);

        return ReservationResponseDto.builder().
                bookingUuid(reservation.getBookingUuid()).
                build();
    }


    @Override
    @Transactional(readOnly = true)
    public List<ReservationModel> fetchAllReservations() {
        log.info("About to fetch all reservations");

        List<Reservation> listOfReservations = this.reservationRepository.findAll();
        log.info("All reservations : {}", listOfReservations.stream().map(Object::toString).collect(Collectors.joining(",")));

        return listOfReservations.
                stream().
                map(this.modelDtoConverter::reservationEntityToReservationModel).
                collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationModel fetchReservationByBookingUuid(String bookingUuid) {
        Reservation reservation = fetchReservation(bookingUuid);

        return this.modelDtoConverter.reservationEntityToReservationModel(reservation);
    }

    @Override
    @Transactional(propagation = REQUIRED, timeout = 5)
    public void cancelReservation(String bookingUuid) {
        log.info("About to cancel reservation with booking id {}", bookingUuid);

        Reservation reservation = fetchReservation(bookingUuid);

        if (reservation.getStatus().getCode().equals(StatusReservation.CANCELED.name())) {
            log.info("Reservation with booikng id {} already canceled", bookingUuid);
            throw new IslandApplicationException(
                    messageSource.getMessage("island.exception.reservation.canceled", null, null,
                            Locale.getDefault()));
        }

        reservation.getSpot().setStatus(this.statusService.getAvailable());
        reservation.setStatus(this.statusService.getCanceled());
        reservation.setUpdateDate(LocalDate.now(LocalDateRange.UTC));
        //TODO
        //Make dates available again
        //reservation.getReservedDates()

        log.info("About to save canceled reservation {}", reservation);
        this.reservationRepository.save(reservation);
    }

    @Override
    @Transactional(propagation = REQUIRED, timeout = 5)
    public ReservationResponseDto modifyReservation(ReservationRequestDto reservationUpdateRequestDto, String bookingUuid) {
        log.info("About to modify reservation with booking id {} with {}", bookingUuid, reservationUpdateRequestDto);
        Reservation reservation = fetchReservation(bookingUuid);

        if (reservation.getStatus().getCode().equals(StatusReservation.CANCELED.name())) {
            log.info("Cannot modify canceled reservation with booikng id {}", bookingUuid);
            throw new IslandApplicationException(
                    messageSource.getMessage("island.exception.reservation.canceled", null, null,
                            Locale.getDefault()));
        }

        IslandUser islandUser = reservation.getUser();
        IslandUser islandUserToUpdate = this.dtoToModelConverter.convertReservationRequestDtoToIslandUserEntity(
                reservationUpdateRequestDto);
        boolean isValidUserUpdate =
                !islandUser.getFirstName().equals(islandUserToUpdate.getFirstName()) ||
                        !islandUser.getLastName().equals(islandUserToUpdate.getLastName()) ||
                        !islandUser.getEmail().equals(islandUserToUpdate.getEmail());

        if (isValidUserUpdate) {
            log.info("User {} is different from user {}", islandUser, islandUserToUpdate);

            islandUser.setFirstName(islandUserToUpdate.getFirstName());
            islandUser.setLastName(islandUserToUpdate.getLastName());
            islandUser.setEmail(islandUserToUpdate.getEmail());
            this.islandUserService.save(islandUser);

            reservation.setUser(islandUser);
            reservation.setArrivalDate(reservationUpdateRequestDto.getRequestDates().getArrivalDate());
            reservation.setDepartureDate(reservationUpdateRequestDto.getRequestDates().getDepartureDate());
            reservation.setUpdateDate(LocalDate.now(LocalDateRange.UTC));
            this.reservationRepository.save(reservation);
        } else {
            log.info("User {} is identical to user {}", islandUser, islandUserToUpdate);

            boolean isValidReservationUpdate =
                    !reservation.getArrivalDate().equals(reservationUpdateRequestDto.getRequestDates().getArrivalDate()) ||
                            !reservation.getDepartureDate().equals(reservationUpdateRequestDto.getRequestDates().getDepartureDate());

            if (isValidReservationUpdate) {
                log.info("Reservation dates changed");

                reservation.setArrivalDate(reservationUpdateRequestDto.getRequestDates().getArrivalDate());
                reservation.setDepartureDate(reservationUpdateRequestDto.getRequestDates().getDepartureDate());
                reservation.setUpdateDate(LocalDate.now(LocalDateRange.UTC));
                this.reservationRepository.save(reservation);
            } else {
                log.info("Reservation dates identical");
                throw new IslandApplicationException(
                        messageSource.getMessage("island.exception.reservation.identical.update", null, null,
                                Locale.getDefault()));
            }
        }

        return ReservationResponseDto.builder().
                bookingUuid(reservation.getBookingUuid()).
                build();
    }

    private Reservation fetchReservation(String bookingUuid) {
        log.info("About to fetch reservation with booking id {}", bookingUuid);

        Optional<Reservation> reservation =
                Optional.ofNullable(this.reservationRepository.findByBookingUuid(bookingUuid));

        if (!reservation.isPresent()) {
            log.info("Reservation with booikng id {} not found", bookingUuid);
            throw new IslandApplicationException(
                    messageSource.getMessage("island.exception.reservation.not.found", null, null,
                            Locale.getDefault()));
        }

        log.info("Reservation with booikng id {} found [{}]", bookingUuid, reservation);
        return reservation.get();
    }

}
