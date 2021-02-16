package com.upgrade.island3.rest.it;

import akka.stream.Materializer;
import com.google.inject.Inject;
import com.upgrade.island3.converter.ReservationModel;
import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.service.ReservationService;
import com.upgrade.island3.utils.AbstractIntegrationTestITCase;
import com.upgrade.island3.utils.TestUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.shaded.okio.ByteString;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.oneOf;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(
        properties = {
                "island.test.spots.number=35",
                "island.test.threads.number=1024"
        }
)
@Slf4j
@EnableAutoConfiguration
@AutoConfigureWebTestClient(timeout = "10000")
public class ReservationControllerConcurrentTestITCase extends AbstractIntegrationTestITCase {

    private static final String TEST_URL_RESERVATION = "/reservation/";
    private static final String LOG_LINE_START = ">>>>>-----------CONCURRENT-----------TEST-----------START---------------------------------------------------------->>>>>";
    private static final String LOG_LINE_END = ">>>>>-----------CONCURRENT-----------TEST-----------END------------------------------------------------------------>>>>>";
    private static final String TEST_BOOKING_UUID = "96a1ce01-2542-4d1d-b1dc-dbaa96604e73";

    @Value("${island.test.spots.number}")
    int spotsNumber;

    @Value("${island.test.threads.number}")
    int threadsNumber;

    @Autowired
    private ReservationService reservationService;

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Inject
    Materializer materializer;

    @BeforeEach
    public void cleanDB() {
        this.reservationService.cancelAllReservations();
    }

    @Test
    public void whenConcurrebtReservationOccursThenSystemCanGracefullyHandleConcurrentRequestsToReserveTheCampsite() {
        log.info(LOG_LINE_START);
        log.info("Running whenConcurrebtReservationOccursThenSystemCanGracefullyHandleConcurrentRequestsToReserveTheCampsite");
        Vector<CompletableFuture<Void>> threads = new Vector<>();

        IntStream.range(1, threadsNumber).forEach(i -> {
            CompletableFuture<Void> thread =
                    CompletableFuture.runAsync(() -> {
                        webTestClient
                                .post()
                                .uri("http://localhost:" + port + TEST_URL_RESERVATION)
                                .body(Mono.just(
                                        TestUtils.generateRandomReservationRequestDto(4, 3)),
                                        ReservationRequestDto.class)
                                .exchange()
                                .expectStatus().value(oneOf(
                                HttpStatus.OK.value(),
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.NOT_FOUND.value()
                        ))
                                .expectBody(String.class)
                                .consumeWith(t -> {
                                    Optional<Map<String, String>> map = Optional.ofNullable(TestUtils.jsonToMap(t.getResponseBody()));
                                    if(map.isPresent()) {
                                        assertTrue(
                                                ((map.get().containsKey("bookingUuid") && map.get().get("bookingUuid").length() == 36) ||
                                                        (map.get().containsKey("errors"))))
                                        ;
                                    }
                                })
                                .returnResult();
                    });

            threads.add(thread);
        });

        threads.forEach(CompletableFuture::join);

        List<ReservationModel> reservations = this.reservationService.fetchAllReservations();

        assertNotEquals(spotsNumber, reservations.size());

        log.info(LOG_LINE_END);
    }
}
