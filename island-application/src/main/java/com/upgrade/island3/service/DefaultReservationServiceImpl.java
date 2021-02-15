package com.upgrade.island3.service;

import com.upgrade.island3.converter.ModelDtoConverter;
import com.upgrade.island3.converter.ReservationModel;
import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.dto.response.ReservationResponseDto;
import com.upgrade.island3.exception.IslandApplicationException;
import com.upgrade.island3.model.IslandUser;
import com.upgrade.island3.model.Reservation;
import com.upgrade.island3.model.StatusReservation;
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

        IslandUser islandUser = convertReservationRequestDtoToIslandUserEntity(reservationRequest);
        this.islandUserService.save(islandUser);

        Reservation reservation =
                convertReservationRequestDtoToReservationEntity(reservationRequest,islandUser);

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

        reservation.setStatus(this.statusService.getCanceled());
        reservation.setUpdateDate(LocalDate.now(LocalDateRange.UTC));
        //Make dates available again
        //reservation.getReservedDates()

        log.info("About to save canceled reservation {}", reservation);
        this.reservationRepository.save(reservation);
    }

    @Override
    @Transactional(propagation = REQUIRED, timeout = 5)
    public ReservationResponseDto modifyReservation(ReservationRequestDto reservationRequestDto, String bookingUuid) {
        log.info("About to modify reservation with booking id {} with {}", bookingUuid, reservationRequestDto);
        Reservation reservation = fetchReservation(bookingUuid);

        //TODO modify logic here
        //update User

        //update Reservation

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

    private Reservation convertReservationRequestDtoToReservationEntity(ReservationRequestDto reservationRequest,IslandUser islandUser) {
        LocalDate fromDate = reservationRequest.getRequestDates().getArrivalDate();
        LocalDate toDate = reservationRequest.getRequestDates().getDepartureDate();

        return Reservation.builder().
                bookingUuid(UUID.randomUUID().toString()).
                status(this.statusService.getReserved()).
                user(islandUser).
                spot(this.spotService.randomSpot()).
                price(0).
                arrivalDate(fromDate).
                departureDate(toDate).
                creationDate(LocalDate.now(LocalDateRange.UTC)).
                updateDate(LocalDate.now(LocalDateRange.UTC)).
                build();
    }

    private IslandUser convertReservationRequestDtoToIslandUserEntity(ReservationRequestDto reservationRequest) {
        return IslandUser.builder().
                firstName(reservationRequest.getFirstName()).
                lastName(reservationRequest.getLastName()).
                email(reservationRequest.getEmail()).
                build();
    }

}
