package uz.pdp.giftcerteficates.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GiftNameNotTrueController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = GiftNameNotTrueException.class)
    public ResponseEntity<Object> exception(GiftNameNotTrueException exception) {
        return new ResponseEntity<>("Gift name is blank", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = GiftNotFoundException.class)
    public ResponseEntity<Object> ex(GiftNotFoundException e) {
        return new ResponseEntity<>("Tag not found",HttpStatus.NOT_FOUND);
    }

}
