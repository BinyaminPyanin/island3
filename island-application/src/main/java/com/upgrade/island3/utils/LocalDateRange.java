package com.upgrade.island3.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Getter
public class LocalDateRange {
    public static final ZoneId UTC = ZoneId.of("UTC");

    private LocalDate fromDate;
    private Optional<ResponseEntity> error;
    private LocalDate toDate;

    public LocalDateRange(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        checkDateRange();
    }

    private void checkDateRange() {
        if (fromDate == null && toDate == null) {
            error = Optional.empty();
            return;
        }
        toDate = toDate == null ? LocalDate.now(UTC) : toDate.atStartOfDay(UTC).toInstant().atZone(UTC).toLocalDate();
        fromDate = fromDate == null ? null : fromDate.atStartOfDay(UTC).toInstant().atZone(UTC).toLocalDate();
        if (fromDate != null && toDate.isBefore(fromDate)) {
            error = Optional.of(datesOverlapping());
        } else {
            error = Optional.empty();
        }
    }

    private ResponseEntity datesOverlapping() {
        return ResponseEntity.
                status(HttpStatus.BAD_REQUEST).
                body(toDate + " is before " + fromDate);
    }

}
