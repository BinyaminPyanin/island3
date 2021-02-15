package com.upgrade.island3.validation;

import com.upgrade.island3.dto.request.RequestDatesDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * InputParametersValidator
 *
 * @author Binyamin Pyanin
 * @since 20210215
 */
@Component
@Validated
public class InputParametersValidator {

    public RequestDatesDto validateRequestDates(@Valid RequestDatesDto requestDates) {
        return requestDates;
    }

}
