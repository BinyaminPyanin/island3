package com.upgrade.island3.rest;

import com.upgrade.island3.exception.IslandApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * ExceptionController
 *
 * @author Binyamin Pyanin
 * @since 20210212
 */

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public String rsExceptionHandler(Throwable ex, HttpServletRequest request, Model map) {
        log.debug("Exception: {}", ex.getLocalizedMessage());
        log.error("Unknown error", ex);
        return "error";
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IslandApplicationException.class)
    public String rsExceptionHandler(IslandApplicationException ex, HttpServletRequest request, Model map) {
        log.debug("Exception: {} ({})", ex.getErrorCode(), ex.getErrorCode().getCode(), ex);
        return "error";
    }

}