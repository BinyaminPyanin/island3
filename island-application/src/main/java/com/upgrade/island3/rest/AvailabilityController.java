package com.upgrade.island3.rest;

import com.upgrade.island3.service.AvailabilityService;
import com.upgrade.island3.utils.LocalDateRange;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "availability")
@ConditionalOnProperty(value = "island.availabilities.endpoint.enabled", havingValue = "true")
@Tag(name = "Availabilities Operations", description = "Operations related to availabilities fetching.")
public class AvailabilityController {

    private AvailabilityService availabilityService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @Operation(summary = "Returns the all availabilities or availabilities for the specified `fromDate`,`toDate`.",
            description = "Returns the all availabilities or availabilities for the specified date range `fromDate`,`toDate`.")
    @ApiResponse(responseCode = "200",
            description = "Returns all the availabilities for the specified date range `fromDate`,`toDate`.",
            content = @Content(array = @ArraySchema(schema = @Schema(type = "string", format = "uri"))))
    @ApiResponse(responseCode = "400",
            description = "Invalid parameters.")
    @ApiResponse(responseCode = "404",
            description = "No availabilities for the specified date range `fromDate`,`toDate`.")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity findReports(
            @Parameter(in = ParameterIn.QUERY, description = "Start of the date range.")
            @RequestParam(required = false, name = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @Parameter(in = ParameterIn.QUERY, description = "End of the date range.")
            @RequestParam(required = false, name = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        final LocalDateRange localDateRange = new LocalDateRange(fromDate, toDate);
        if (localDateRange.getError().isPresent()) {
            return localDateRange.getError().get();
        }

        List<LocalDate> listOfAvailabilities;
        if (null == fromDate && null == toDate) {
            listOfAvailabilities =
                    availabilityService.findAvailability();
        } else {
            listOfAvailabilities =
                    availabilityService.findAvailability(localDateRange.getFromDate(), localDateRange.getToDate());
        }

        if (listOfAvailabilities.isEmpty()) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    body("Empty list of availabilities.");
        }

        return ResponseEntity.
                status(HttpStatus.OK).
                body(listOfAvailabilities);
    }

}
