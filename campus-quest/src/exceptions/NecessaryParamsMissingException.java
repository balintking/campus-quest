package exceptions;

public class NecessaryParamsMissingException extends Exception {
    public NecessaryParamsMissingException(){
        super("354 Necessary parameter(s) missing");
    }
}
