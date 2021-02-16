package com.upgrade.island3.service;

import com.upgrade.island3.converter.DtoToModelConverter;
import com.upgrade.island3.converter.ModelToDtoConverter;
import com.upgrade.island3.dto.response.ReservationResponseDto;
import com.upgrade.island3.exception.IslandApplicationException;
import com.upgrade.island3.model.Reservation;
import com.upgrade.island3.repository.ReservationRepository;
import com.upgrade.island3.utils.TestUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DefaultReservationServiceImplTest {

    @InjectMocks
    private DefaultReservationServiceImpl classUnderTest;
    @Mock
    private AvailabilityService availabilityService;
    @Mock
    private StatusService statusService;
    @Mock
    private IslandUserService islandUserService;
    @Mock
    private SpotService spotService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private MessageSource messageSource;
    @Mock
    private ModelToDtoConverter modelDtoConverter;
    @Mock
    private DtoToModelConverter dtoToModelConverter;

    @Test
    @SneakyThrows
    void whenMakeReservationIsCalledThenReservationCompleted() {
        log.info("Running whenMakeReservationIsCalledThenReservationCompleted");

        when(availabilityService.findAvailability(
                TestUtils.getArrivalDate(),
                TestUtils.getDepartureDate(),
                this.statusService.getAvailable())).
                thenReturn(TestUtils.getAvailabilityList());

        when(statusService.getReserved()).thenReturn(TestUtils.getReserved());

        when(dtoToModelConverter.convertReservationRequestDtoToIslandUserEntity(
                TestUtils.getReservationRequestDto())).
                thenReturn(TestUtils.getIslandUser());

        Reservation reservation = TestUtils.getReservation();
        when(dtoToModelConverter.convertReservationRequestDtoToReservationEntity(
                any(),
                any(),
                any(),
                any())).
                thenReturn(reservation);

        ReservationResponseDto reservationResponseDto =
                classUnderTest.makeReservation(TestUtils.getReservationRequestDto());

        verify(reservationRepository, times(1)).save(any());

        assertEquals(reservationResponseDto.getBookingUuid(), reservation.getBookingUuid());
    }

    @Test
    void whenNoAvailableDatesForReservationThenExceptionIsThrown() {
        log.info("Running whenNoAvailableDatesForReservationThenExceptionIsThrown");

        Assertions.assertThrows(IslandApplicationException.class, () ->
                classUnderTest.makeReservation(TestUtils.getReservationRequestDto()));
    }

}
