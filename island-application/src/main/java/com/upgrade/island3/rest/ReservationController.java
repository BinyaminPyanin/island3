package com.upgrade.island3.rest;

import com.upgrade.island3.converter.ReservationModel;
import com.upgrade.island3.dto.request.ReservationRequestDto;
import com.upgrade.island3.dto.response.ReservationResponseDto;
import com.upgrade.island3.service.ReservationService;
import com.upgrade.island3.validation.InputParametersValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    private InputParametersValidator inputParametersValidator;

    @Autowired
    public ReservationController(ReservationService reservationService,
                                 InputParametersValidator inputParametersValidator) {
        this.reservationService = reservationService;
        this.inputParametersValidator = inputParametersValidator;
    }

    @Operation(summary = "Provides an end point for reserving the campsite.",
            description = "Returns a unique booking identifier back to the caller if the reservation is successful.")
    @ApiResponse(responseCode = "200",
            description = "Returns a unique booking identifier back to the caller if the reservation is successful.",
            content = @Content(schema = @Schema(implementation = ReservationResponseDto.class)))
    @ApiResponse(responseCode = "400",
            description = "Invalid parameters.")
    @ApiResponse(responseCode = "404",
            description = "Unable to make the reservation.")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ReservationResponseDto makeReservation(@RequestBody ReservationRequestDto reservationRequest
    ) {
        this.inputParametersValidator.validateRequestDates(reservationRequest.getRequestDates());

        return this.reservationService.makeReservation(reservationRequest);
    }

    @Operation(summary = "Provides an end point for fetching all existing campsite reservations.",
            description = "Returns a list of all existing campsite reservations.")
    @ApiResponse(responseCode = "200",
            description = "Returns a list of all existing campsite reservations.",
            content = @Content(schema = @Schema(implementation = ReservationModel.class)))
    @ApiResponse(responseCode = "400",
            description = "Invalid parameters.")
    @ApiResponse(responseCode = "404",
            description = "Empty list of reservations.")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity fetchAllExistingReservations() {
        List<ReservationModel> reservationModelList = this.reservationService.fetchAllReservations();

        if (reservationModelList.isEmpty()) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    body("Empty list of reservations.");
        }

        return ResponseEntity.
                status(HttpStatus.OK).
                body(reservationModelList);
    }

    @Operation(summary = "Provides an end point for fetching existing campsite reservation.",
            description = "Returns existing campsite reservation by booking identifier.")
    @ApiResponse(responseCode = "200",
            description = "Returns existing campsite reservation by booking identifier.",
            content = @Content(schema = @Schema(implementation = ReservationModel.class)))
    @ApiResponse(responseCode = "400",
            description = "Invalid parameters.")
    @ApiResponse(responseCode = "404",
            description = "Reservation not found.")
    @GetMapping(path = "/{bookingUuid}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getReservationsByBookingIdentifier(@Valid @PathVariable String bookingUuid) {
        ReservationModel reservationModel = this.reservationService.fetchReservationByBookingUuid(bookingUuid);

        if (null == reservationModel) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    body("Reservation with booking identifier " + bookingUuid + " not found");
        }

        return ResponseEntity.
                status(HttpStatus.OK).
                body(reservationModel);
    }

    @Operation(summary = "Provides an end point for canceling existing campsite reservation.",
            description = "Cancels existing campsite reservation by booking identifier.")
    @ApiResponse(responseCode = "200",
            description = "Cancels existing campsite reservation by booking identifier.",
            content = @Content(schema = @Schema(implementation = ReservationModel.class)))
    @ApiResponse(responseCode = "400",
            description = "Invalid parameters.")
    @ApiResponse(responseCode = "204",
            description = "No content.")
    @DeleteMapping(path = "/{bookingUuid}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity cancelReservationsByBookingIdentifier(@Valid @PathVariable String bookingUuid) {
        this.reservationService.cancelReservation(bookingUuid);

        return ResponseEntity.
                status(HttpStatus.NO_CONTENT).
                body(bookingUuid);
    }

    @Operation(summary = "Provides an end point for modifying the campsite.",
            description = "Returns a unique booking identifier back to the caller if the reservation modified ok.")
    @ApiResponse(responseCode = "200",
            description = "Returns a unique booking identifier back to the caller if the reservation modified ok.",
            content = @Content(schema = @Schema(implementation = ReservationResponseDto.class)))
    @ApiResponse(responseCode = "400",
            description = "Invalid parameters.")
    @ApiResponse(responseCode = "404",
            description = "Unable to modify the reservation.")
    @PutMapping(path = "/{bookingUuid}", produces = APPLICATION_JSON_VALUE)
    public ReservationResponseDto modifyReservation(
            @Valid @PathVariable String bookingUuid,
            @RequestBody ReservationRequestDto reservationRequest) {

        this.inputParametersValidator.validateRequestDates(reservationRequest.getRequestDates());

        return this.reservationService.modifyReservation(reservationRequest, bookingUuid);
    }

}
