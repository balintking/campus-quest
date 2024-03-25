package utility;

import java.util.*;

/**
 * Implementation of a simple Logger class capable of writing to the console output.
 *
 * @author Szakos Máté Antal
 */
public class Logger {
    /**
     * Indicating the depth of the call stack.
     */
    private static int depth = 0;
    /**
     * Storing the names of the object instances whose creation is logged.
     */
    private static final Map<Object, String> objectCatalog = new HashMap<>();
    /**
     * Storing the destroyed objects to make sure there are no inconsistencies.
     */
    private static final Set<Object> destroyedObjects = new HashSet<>();

    /**
     * Private static method for creating a loggable String from an iterable Collection.
     *
     * @param collectionObject The collection that must be logged.
     * @return The String of the containers content listed.
     */
    private static String logCollection(Object collectionObject) {
        Iterable<?> collection = (Iterable<?>) collectionObject;
        StringBuilder sb = new StringBuilder();
        for (Object element : collection) {
            if (element == null) {
                sb.append("null");
            } else if (objectCatalog.containsKey(element)) {
                sb.append(objectCatalog.get(element));
            } else {
                sb.append(element.toString());
            }
            sb.append(',');
        }
        if(!sb.isEmpty()){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * A static method for logging a method call. It increases the depth by 1.
     *
     * @param methodName       The name of the called method.
     * @param methodParameters The parameters of the called method.
     * @param returnType       The return type of the called method.
     * @throws IllegalStateException If the call has a parameter that already has been destroyed.
     */
    public static void logCall(String methodName, Object[] methodParameters, String returnType) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append('\t');
        }
        sb.append("call: ");
        sb.append(methodName);
        sb.append('(');
        if (methodParameters != null) {
            for (int i = 0; i < methodParameters.length; i++) {
                if (methodParameters[i] == null) {
                    sb.append("null");
                } else if (methodParameters[i] instanceof Iterable<?>) {
                    sb.append(logCollection(methodParameters[i]));
                } else if (objectCatalog.containsKey(methodParameters[i])) {
                    sb.append(objectCatalog.get(methodParameters[i]));
                } else {
                    sb.append(methodParameters[i].toString());
                }
                if (destroyedObjects.contains(methodParameters[i])) {
                    throw new IllegalStateException("The call has a parameter that already has been destroyed.");
                }
                if (i != methodParameters.length - 1) {
                    sb.append(',');
                }
            }
        }
        sb.append("): ");
        sb.append(returnType);
        System.out.println(sb);
        depth++;
    }

    /**
     * Overloading of the static method logCall for the case if parameters are absent.
     *
     * @param methodName The name of the called method.
     * @param returnType The return type of the called method.
     * @throws IllegalStateException If the call has a parameter that already has been destroyed.
     */
    public static void logCall(String methodName, String returnType) {
        logCall(methodName, null, returnType);
    }

    /**
     * A static method for logging a return from a method. It decreases the depth by 1.
     *
     * @param returnValue The returning value of the method.
     * @throws IllegalStateException It is thrown if the depth becomes less than 0, indicating that it is being called by mistake.
     * @throws IllegalStateException If the return value has already been destroyed.
     */
    public static void logReturn(Object returnValue) throws IllegalStateException {
        if (destroyedObjects.contains(returnValue)) {
            throw new IllegalStateException("The return value has already been destroyed.");
        }
        StringBuilder sb = new StringBuilder();
        depth--;
        if (depth < 0) {
            throw new IllegalStateException("You cannot return from a method you didn't call!");
        }
        for (int i = 0; i < depth; i++) {
            sb.append('\t');
        }
        sb.append("return: ");
        if (returnValue == null) {
            sb.append("null");
        } else if (returnValue instanceof Iterable<?>) {
            sb.append(logCollection(returnValue));
        } else if (objectCatalog.containsKey(returnValue)) {
            sb.append(objectCatalog.get(returnValue));
        } else {
            sb.append(returnValue.toString());
        }
        System.out.println(sb);

    }

    /**
     * Overloading of the static method logReturn for the case if it is a void return.
     *
     * @throws IllegalStateException It is thrown if the depth becomes less than 0, indicating that it is being called by mistake.
     * @throws IllegalStateException If the return value has already been destroyed.
     */
    public static void logReturn() throws IllegalStateException {
        logReturn("void");
    }

    /**
     * A static method for logging the creation of an object instance.
     *
     * @param objectReference       The reference of the instance being created.
     * @param objectType            The type of the instance being created.
     * @param instanceName          The name of the instance being created.
     * @param constructorParameters The array of the constructor parameters.
     */
    public static void logCreate(Object objectReference, String objectType, String instanceName, Object[] constructorParameters) {
        if (objectCatalog.containsKey(objectReference)) {
            throw new IllegalStateException("Ambigious constructor logging!");
        }
        destroyedObjects.remove(objectReference);
        objectCatalog.put(objectReference, instanceName);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append('\t');
        }
        sb.append("create: ");
        sb.append(objectType);
        sb.append('(');
        if (constructorParameters != null) {
            for (int i = 0; i < constructorParameters.length; i++) {
                if (constructorParameters[i] == null) {
                    sb.append("null");
                } else if (constructorParameters[i] instanceof Iterable<?>) {
                    sb.append(logCollection(constructorParameters[i]));
                } else if (objectCatalog.containsKey(constructorParameters[i])) {
                    sb.append(objectCatalog.get(constructorParameters[i]));
                } else {
                    sb.append(constructorParameters[i].toString());
                }
                if (i != constructorParameters.length - 1) {
                    sb.append(',');
                }
            }
        }
        sb.append("):");
        sb.append(instanceName);
        System.out.println(sb);
    }

    /**
     * Overloading of the static method logCreate for the case if there are no constructor parameters.
     *
     * @param objectReference The reference of the instance being created.
     * @param objectType      The type of the instance being created.
     * @param instanceName    The name of the instance being created.
     */
    public static void logCreate(Object objectReference, String objectType, String instanceName) {
        logCreate(objectReference, objectType, instanceName, null);
    }

    /**
     * Resets the Logger to its default state.
     */
    public static void reset() {
        objectCatalog.clear();
        destroyedObjects.clear();
        depth = 0;
    }

    /**
     * Asks a question for the tester and they should answer it with either yes or no.
     *
     * @param question The question to be asked.
     * @return Wether the user input was yes or no.
     */
    public static boolean testerInput(String question) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Tester intervention needed: " + question + "\n Press i (for yes) or n (for no)!");
        while (!sc.hasNextLine()) {
            sc.next();
        }
        String input = sc.nextLine();
        input = input.trim().toLowerCase();
        while (!(input.equals("i") || input.equals("n"))) {
            System.out.println("Please enter a valid input! i for yes or n for no!");
            while (!sc.hasNextLine()) {
                System.out.println("Please enter a valid input! i for yes or n for no!");
                sc.next();
            }
            input = sc.nextLine();
            input = input.trim().toLowerCase();
        }
        return input.equals("i");

    }

    /**
     * Marks the destruction of an object which must have been a logged creation.
     *
     * @param objectInstance The object being destroyed.
     * @throws IllegalStateException If the object has not been created.
     * @throws IllegalStateException If the object has already been destroyed.
     */
    public static void logDestroy(Object objectInstance, String objectType) {
        if (!objectCatalog.containsKey(objectInstance)) {
            throw new IllegalStateException("Cannot destroy an object that has not been created!");
        }
        if (destroyedObjects.contains(objectInstance)) {
            throw new IllegalStateException("Cannot destroy an object that has already been destroyed!");
        }
        System.out.println("Destroyed " + objectCatalog.get(objectInstance) + " (" + objectType + ')');
        objectCatalog.remove(objectInstance);
        destroyedObjects.add(objectInstance);
    }


}
