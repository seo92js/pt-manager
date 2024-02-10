package com.seojs.ptmanager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExRestControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public String runtime(RuntimeException e) {
        log.error("[exceptionHandler] ex", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberNotFoundEx.class)
    public String memberNotFound(MemberNotFoundEx e) {
        log.error("[exceptionHandler] ex", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TrainerNotFoundEx.class)
    public String trainerNotFound(TrainerNotFoundEx e) {
        log.error("[exceptionHandler] ex", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TicketNotFoundEx.class)
    public String ticketNotFound(TicketNotFoundEx e) {
        log.error("[exceptionHandler] ex", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AdminNotFoundEx.class)
    public String adminNotFound(AdminNotFoundEx e) {
        log.error("[exceptionHandler] ex", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberDuplicateEx.class)
    public String memberDuplicate(MemberDuplicateEx e) {
        log.error("[exceptionHandler] ex", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TicketDuplicateEx.class)
    public String ticketDuplicate(TicketDuplicateEx e) {
        log.error("[exceptionHandler] ex", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TrainerDuplicateEx.class)
    public String trainerDuplicate(TrainerDuplicateEx e) {
        log.error("[exceptionHandler] ex", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReserveDuplicateEx.class)
    public String runtime(ReserveDuplicateEx e) {
        log.error("[exceptionHandler] ex", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String valid(MethodArgumentNotValidException e) {

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String errorMessage = errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.error("[exceptionHandler] ex", e.getMessage());

        return errorMessage;
    }
}
