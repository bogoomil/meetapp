package hu.kunb.meetingapp.reservation.exception;

import java.util.ArrayList;
import java.util.List;

public class ReservationException extends RuntimeException{

    private List<String> errors = new ArrayList<>();

    public void addError(String error){
        this.errors.add(error);
    }

    public boolean isEmpty(){
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
}
