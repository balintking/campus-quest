package exceptions;

public class UnexpectedErrorException extends Exception {
    public UnexpectedErrorException(){
        super("401 Unexpected error");
    }
}
