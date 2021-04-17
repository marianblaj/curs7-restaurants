package ro.fasttrackit.restaurants.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String msg){
        super(msg);
    }
}
