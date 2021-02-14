package com.upgrade.island3.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * ReservationRequestDto
 *
 * @author Binyamin Pyanin
 * @since 20210214
 */
@Data
public class ReservationRequestDto {
    @NotEmpty(message = "{island.validation.firstname.empty}")
    @NotNull
    @Length(min = 1, max = 256)
    private String firstName;
    @NotEmpty
    @NotNull
    @Length(min = 1, max = 256)
    private String lastName;
    @NotEmpty
    @NotNull
    @Email
    @Length(min = 1, max = 256)
    private String email;
    @Valid
    @NotNull
    private RequestDatesDto requestDates;
}
