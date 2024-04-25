package utility;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

/**
 * Function interface for the Commands.
 */
public interface Command {
    void execute(Object param) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, FileNotFoundException;
}
