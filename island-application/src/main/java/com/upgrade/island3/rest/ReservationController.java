package com.upgrade.island3.rest;

import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.dto.response.ReservationResponseDto;
import com.upgrade.island3.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * ReservationController
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@RestController
@RequestMapping(path = "reservation")
@ConditionalOnProperty(value = "island.reservation.endpoint.enabled", havingValue = "true")
@Tag(name = "Reservations Operations", description = "Operations related to reservations.")
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Provide an end point for reserving the campsite..",
            description = "Return a unique booking identifier back to the caller if the reservation is successful.")
    @ApiResponse(responseCode = "200",
            description = "Return a unique booking identifier back to the caller if the reservation is successful.",
            content = @Content(schema = @Schema(implementation = ReservationResponseDto.class)))
    @ApiResponse(responseCode = "400",
            description = "Invalid parameters.")
    @ApiResponse(responseCode = "404",
            description = "Unable to make the reservation.")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ReservationResponseDto makeReservation(@Valid @RequestBody ReservationRequestDto reservationRequest
    ) {
        return this.reservationService.makeReservation(reservationRequest);
    }

}
