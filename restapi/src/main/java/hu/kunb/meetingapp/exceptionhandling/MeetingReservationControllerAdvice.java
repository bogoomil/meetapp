package hu.kunb.meetingapp.exceptionhandling;

import hu.kunb.meetingapp.apidefinition.spring.model.ErrorDto;
import hu.kunb.meetingapp.apidefinition.spring.model.ErrorDtoErrorListInner;
import hu.kunb.meetingapp.reservation.exception.ReservationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class MeetingReservationControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto dto = new ErrorDto();
        BindingResult bindingResult = ex.getBindingResult();
        dto.setErrorList(new ArrayList<>());
        bindingResult.getFieldErrors().forEach(fieldError -> {
            dto.getErrorList().add(new ErrorDtoErrorListInner().message(fieldError.getField() + " " + fieldError.getDefaultMessage()));
        });
        dto.setMessage("Invalid input data");
        ResponseEntity resp = ResponseEntity.status(400).body(dto);

        return resp;
    }

    @ExceptionHandler(value = {ReservationException.class})
    public ResponseEntity<?> handleNvenExceptions(ReservationException ex) {
        ErrorDto dto = new ErrorDto()
                .errorList(ex.getErrors().stream().map(error -> new ErrorDtoErrorListInner().message(error)).collect(Collectors.toList()))
                .message("Invalid input data");
        ResponseEntity resp = ResponseEntity.status(400).body(dto);
        return resp;
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<?> handleExceptions(Throwable ex) {
        ErrorDto dto = new ErrorDto();
        dto.setMessage("Internal server error: " + Arrays.asList(ex.getClass().getCanonicalName()));
        ResponseEntity resp = ResponseEntity.status(500).body(dto);
        return resp;
    }
}
