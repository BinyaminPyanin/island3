package com.upgrade.island3.rest;

import com.upgrade.island3.service.AvailabilityService;
import com.upgrade.island3.utils.TestUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(AvailabilityController.class)
@AutoConfigureMockMvc
public class AvailabilityControllerTest {

    private static final String TEST_URL_AVAILABILITY = "/availability/";
    private static final String MSG_EMPTY_LIST_OF_AVAILABLE_DATES = "Empty list of available dates.";
    private static final String ILLEGAL_ARGUMENT = "illegal";
    private static final String FROM_DATE_FIELD = "fromDate";
    private static final String TEST_FROM_DATE_VALUE_NEGATIVE = "2020-09-30";
    private static final String TO_DATE_FIELD = "toDate";
    private static final String TEST_TO_DATE_VALUE_2 = "2020-09-29";
    private static final String MSG_IS_BEFORE = " is before ";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvailabilityService availabilityService;

    @Test
    @SneakyThrows
    void whenAvailabilityEPReachedAndAvailabilitiesNotFoundThen404IsReturned() {
        log.info("Running whenAvailabilityEPReachedAndAvailabilitiesNotFoundThen404IsReturned");

        mockMvc.perform(get(TEST_URL_AVAILABILITY)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(MSG_EMPTY_LIST_OF_AVAILABLE_DATES)));
    }

    @Test
    @SneakyThrows
    void whenAvailabilityEPReachedAndFromDateIsIllegalThenExceptionIsThrown() {
        log.info("Running whenAvailabilityEPReachedAndFromDateIsIllegalThenExceptionIsThrown");

        mockMvc.perform(get(TEST_URL_AVAILABILITY)
                .param(FROM_DATE_FIELD, ILLEGAL_ARGUMENT)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
    }

    @Test
    @SneakyThrows
    void whenAvailabilityEPReachedAndDatesOverlapThenStatusIsBadRequest() {
        log.info("Running whenAvailabilityEPReachedAndDatesOverlapThenStatusIsBadRequest");

        mockMvc.perform(get(TEST_URL_AVAILABILITY)
                .param(FROM_DATE_FIELD, TEST_FROM_DATE_VALUE_NEGATIVE)
                .param(TO_DATE_FIELD, TEST_TO_DATE_VALUE_2)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(TEST_TO_DATE_VALUE_2 + MSG_IS_BEFORE + TEST_FROM_DATE_VALUE_NEGATIVE)));
    }

    @Test
    @SneakyThrows
    void whenAvailabilityEPReachedThenStatusIsOk() {
        log.info("Running whenAvailabilityEPReachedThenStatusIsOk");

        when(availabilityService.findAvailabilityDates(
                TestUtils.getArrivalDate(),
                TestUtils.getDepartureDate())).
                thenReturn(TestUtils.getLocalDatesList());

        mockMvc.perform(get(TEST_URL_AVAILABILITY)
                .param(FROM_DATE_FIELD, TestUtils.arrivalDate)
                .param(TO_DATE_FIELD, TestUtils.departureDate)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void whenAvailabilityEPReachedNoArgsThenStatusIsOk() {
        log.info("Running whenAvailabilityEPReachedNoArgsThenStatusIsOk");

        when(availabilityService.findAvailabilityDates()).thenReturn(TestUtils.getLocalDatesList());

        mockMvc.perform(get(TEST_URL_AVAILABILITY)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
