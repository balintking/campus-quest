package utility;

import java.util.Scanner;

/**
 * Implementation of a simple Logger class capable of writing to the console output.
 * @author Szakos Máté Antal
 */
public class Logger {
    /**
     * Indicating the depth of the call stack.
     */
    private static int depth = 0;

    /**
     * A static method for logging a method call. It increases the depth by 1.
     * @param methodName The name of the called method.
     * @param methodParameters The parameters of the called method.
     * @param returnType The return type of the called method.
     */
    public static void logCall(String methodName,String[] methodParameters,String returnType) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < depth; i++) {
            sb.append('\t');
        }
        sb.append("call: "); sb.append(methodName); sb.append('(');
        if(methodParameters != null) {
            for (int i = 0; i < methodParameters.length; i++) {
                sb.append(methodParameters[i]);
                if (i != methodParameters.length - 1) {
                    sb.append(',');
                }
            }
        }
        sb.append("): "); sb.append(returnType);
        System.out.println(sb);
        depth++;
    }

    /**
     * Overloading of the static method logCall for the case if parameters are absent.
     * @param methodName The name of the called method.
     * @param returnType The return type of the called method.
     */
    public static void logCall(String methodName,String returnType) {
        logCall(methodName,null,returnType);
    }

    /**
     * A static method for logging a return from a method. It decreases the depth by 1.
     * @param returnValue The returning value of the method.
     * @throws IllegalStateException It is thrown if the depth becomes less than 0, indicating that it is being called by mistake.
     */
    public static void logReturn(String returnValue) throws IllegalStateException {
        StringBuilder sb = new StringBuilder();
        depth--;
        if(depth < 0) {
            throw new IllegalStateException("You cannot return from a method you didn't call!");
        }
        for(int i = 0; i < depth; i++) {
            sb.append('\t');
        }
        sb.append("return: "); sb.append(returnValue);
        System.out.println(sb);

    }

    /**
     * Overloading of the static method logReturn for the case if it is a void return.
     * @throws IllegalStateException It is thrown if the depth becomes less than 0, indicating that it is being called by mistake.
     */
    public static void logReturn() throws IllegalStateException {
        logReturn("void");
    }

    /**
     * A static method for logging the creation of an object instance.
     * @param objectType The type of the object.
     * @param constructorParameters The parameters the constructor takes.
     */
    public static void logCreate(String objectType,String[] constructorParameters) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < depth; i++) {
            sb.append('\t');
        }
        sb.append("create: "); sb.append(objectType); sb.append('(');
        if(constructorParameters != null) {
            for (int i = 0; i < constructorParameters.length; i++) {
                sb.append(constructorParameters[i]);
                if (i != constructorParameters.length - 1) {
                    sb.append(',');
                }
            }
        }
        sb.append(')');
        System.out.println(sb);
    }

    /**
     * Asks a question for the tester and they should answer it with eiter yes or no.
     * @param question The question to be asked.
     * @return Wether the user input was yes or no.
     */
    public static boolean testerInput(String question) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Tester intervention needed: " + question + "\n Press i (for yes) or n (for no)!");
        while(!sc.hasNextLine()) {
            sc.next();
        }
        String input = sc.nextLine();
        input.trim();
        while (!(input.equals("i") || input.equals("n"))){
            System.out.println("Please enter a valid input! i for yes or n for no!");
            while (!sc.hasNextLine()) {
                System.out.println("Please enter a valid input! i for yes or n for no!");
                sc.next();
            }
            input = sc.nextLine();
            input.trim();
        }
        sc.close();
        return input.equals("i");
    }


}
