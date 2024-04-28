package utility;

import exceptions.NecessaryParamsMissingException;
import exceptions.NonexistentObjectException;
import exceptions.NonexistentOperationException;
import exceptions.UnexpectedErrorException;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

/**
 * Function interface for the Commands.
 */
public interface Command {
    void execute(Object param) throws FileNotFoundException, NonexistentObjectException, UnexpectedErrorException, NecessaryParamsMissingException, NonexistentOperationException;
}
