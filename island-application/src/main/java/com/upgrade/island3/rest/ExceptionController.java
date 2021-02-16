package com.upgrade.island3.rest;

import com.upgrade.island3.exception.IslandApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ExceptionController
 *
 * @author Binyamin Pyanin
 * @since 20210212
 */
@Slf4j
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    private static final String EXCEPTION_PREFIX = "Exception: {}";

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> islnadExceptionHandler(RuntimeException ex, WebRequest request) {
        log.debug(EXCEPTION_PREFIX, ex.getLocalizedMessage());
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(IslandApplicationException.class)
    public ResponseEntity<Object> islnadExceptionHandler(IslandApplicationException ex, WebRequest request, Model map) {
        log.debug(EXCEPTION_PREFIX, ex.getLocalizedMessage());
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {JpaSystemException.class})
    protected ResponseEntity<Object> islnadExceptionHandler(JpaSystemException ex, WebRequest request) {
        log.debug(EXCEPTION_PREFIX, ex.getLocalizedMessage());
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {ObjectOptimisticLockingFailureException.class})
    protected ResponseEntity<Object> islnadExceptionHandler(ObjectOptimisticLockingFailureException ex, WebRequest request) {
        log.debug(EXCEPTION_PREFIX, ex.getLocalizedMessage());
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}