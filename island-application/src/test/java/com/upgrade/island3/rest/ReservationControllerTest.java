package com.upgrade.island3.rest;

import com.upgrade.island3.converter.ModelToDtoConverter;
import com.upgrade.island3.converter.ReservationModel;
import com.upgrade.island3.service.ReservationService;
import com.upgrade.island3.utils.TestUtils;
import com.upgrade.island3.validation.InputParametersValidator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc
public class ReservationControllerTest {

    private static final String TEST_URL_RESERVATION = "/reservation/";
    private static final String EMPTY_LIST_OF_RESERVATIONS = "Empty list of reservations.";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private InputParametersValidator inputParametersValidator;

    @MockBean
    private ModelToDtoConverter modelToDtoConverter;

    @Test
    @SneakyThrows
    void whenFetchAllExistingReservationsEPReachedAndAvailabilitiesNotFoundThen404IsReturned() {
        log.info("Running whenFetchAllExistingReservationsEPReachedAndAvailabilitiesNotFoundThen404IsReturned");

        mockMvc.perform(get(TEST_URL_RESERVATION)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(EMPTY_LIST_OF_RESERVATIONS)));
    }

    @Test
    @SneakyThrows
    void whenFetchAllExistingReservationsEPReachedThenOk() {
        log.info("Running whenFetchAllExistingReservationsEPReachedThenOk");

        List<ReservationModel> reservationModelList = new ArrayList<>();
        reservationModelList.add(
                modelToDtoConverter.reservationEntityToReservationModel(TestUtils.getReservation()));

        when(reservationService.fetchAllReservations()).thenReturn(reservationModelList);

        mockMvc.perform(get(TEST_URL_RESERVATION)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
