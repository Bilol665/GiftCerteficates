package uz.pdp.giftcerteficates.controller.exceptions;

public class NoGiftWithThisNameFoundException extends RuntimeException {
    public NoGiftWithThisNameFoundException(String message) {
        super(message);
    }
}
