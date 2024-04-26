package exceptions;

public class OperationProhibitedException extends RuntimeException {
    OperationProhibitedException(){
        super("350 Operation prohibited");
    }
}
