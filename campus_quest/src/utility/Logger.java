package utility;

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



}
