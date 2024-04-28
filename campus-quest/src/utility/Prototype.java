package utility;

import characters.Person;
import characters.Student;
import exceptions.NecessaryParamsMissingException;
import exceptions.NonexistentObjectException;
import exceptions.NonexistentOperationException;
import exceptions.UnexpectedErrorException;
import items.*;
import map.Door;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This class provides a user interface for the Prototype program.
 */
public class Prototype {
    private static boolean testMode;
    private static boolean readingExpected = false;
    private static boolean isReadingState = false;
    private static Map<String, Command> commands = new HashMap<>();
    private static GameState currentState = new GameState();
    private static GameState expectedState = new GameState();
    private static Person agent;
    private static List<String> parameters = new ArrayList<>();
    private static Map<String, String> options = new HashMap<>();
    private static StringBuilder inputBuffer = new StringBuilder();


    static {
        commands.put("pickup", pitem -> {
            if (!(pitem instanceof Item item)) throw new IllegalArgumentException();
            agent.pickup(item);
        });
        commands.put("move", pdoor -> {
            if (!(pdoor instanceof Door door)) throw new IllegalArgumentException();
            agent.move(door);
        });
        commands.put("activate", pitem -> {
            if (!(agent instanceof Student sagent)) throw new IllegalArgumentException();
            if (!(pitem instanceof Item item)) throw new IllegalArgumentException();
            sagent.initActivate(item);
        });
        commands.put("drop", pitem -> {
            if (!(agent instanceof Student sagent)) throw new IllegalArgumentException();
            if (!(pitem instanceof Item item)) throw new IllegalArgumentException();
            sagent.drop(item);
        });
        commands.put("tick", param -> {
            currentState.tick();
        });
        commands.put("act", param -> {
            if (parameters.size() < 3) throw new RuntimeException();
            Object oagent = (readingExpected) ? expectedState.getObject(parameters.get(0)) : currentState.getObject(parameters.get(0));
            if (!(oagent instanceof Person pagent)) throw new RuntimeException();
            agent = pagent;
            GameState stat = (readingExpected) ? expectedState : currentState;
            commands.get(parameters.get(1)).execute(stat.getObject(parameters.get(2)));
        });
        commands.put("0", param -> {
            System.exit(0);
        });
        commands.put("1", param -> {
            testMode = true;
            System.out.println("Please specifiy the test directory (working dir: " + System.getProperty("user.dir")+ "\\)!");
            Scanner scanner = new Scanner(System.in);
            while (!scanner.hasNextLine()) {
                scanner.next();
            }
            String path = System.getProperty("user.dir") + "\\" + scanner.next();
            path = path.replace("/", "\\");
            int i = 0;
            while (true) {
                File testf = new File(path + "\\test" + i + ".txt");
                File outputf = new File(path + "\\output" + i + ".txt");
                if (!testf.exists() || !outputf.exists()) {
                    if (i == 0) System.out.println("Cannot find any tests in the directory \"" + path + "\"");
                    break;
                }
                readInputFromScanner(new Scanner(new FileInputStream(testf)), true);
                readingExpected = true;
                readInputFromScanner(new Scanner(new FileInputStream(outputf)), true);
                readingExpected = false;
                List<String> results = currentState.testCompare(expectedState);
                if (results.isEmpty()) {
                    System.out.println("Test" + i + " successful!");
                } else {
                    System.out.println("Test" + i + " failed!");
                    for (String s : results) {
                        System.out.println(s);
                    }
                }
                i++;
            }
            testMode = false;
        });
        commands.put("init", param -> {
            isReadingState = true;
        });
        commands.put("endinit", param -> {
            isReadingState = false;
            currentState.readState(inputBuffer.toString());
            inputBuffer.setLength(0);
        });
        commands.put("status", param -> {
            if (testMode) {
                isReadingState = true;
            } else {
                System.out.println(currentState);
            }
        });
        commands.put("endstatus", param -> {
            isReadingState = false;
            expectedState.readState(inputBuffer.toString());
            inputBuffer.setLength(0);
        });
    }

    private static boolean processLine(String _line) throws NonexistentObjectException, FileNotFoundException, UnexpectedErrorException, NecessaryParamsMissingException, NonexistentOperationException {
        String line = _line.toLowerCase().trim();
        if (isReadingState && !(line.length() < 3 || line.substring(0, 3).equals("end"))) {
            inputBuffer.append(line);
            inputBuffer.append('\n');
            return true;
        }
        String[] splittedLine = line.split(" ");
        if (!commands.containsKey(splittedLine[0])) return false;
        parameters.addAll(Arrays.asList(splittedLine).subList(1, splittedLine.length));
        commands.get(splittedLine[0]).execute(null);
        return true;
    }

    private static void readInputFromScanner(Scanner scanner, boolean fromfile) throws FileNotFoundException, NonexistentObjectException, UnexpectedErrorException, NecessaryParamsMissingException, NonexistentOperationException {
        boolean isValid = false;
        while (true) {
            if (fromfile && !scanner.hasNextLine()) break;
            if (!scanner.hasNextLine()) {
                continue;
            }

            isValid = processLine(scanner.nextLine());
            while (!isValid && !fromfile) {
                System.out.println("Please type in a valid command to use shell or enter 1 to run tests. Enter 0 to exit.");
                if (!scanner.hasNextLine()) {
                    continue;
                }
                isValid = processLine(scanner.nextLine());
            }
            parameters.clear();
            options.clear();
            agent = null;
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Campus Quest prototype program! \nType in any command to use shell or press 1 to run tests. Press 0 to exit.");
        Scanner scanner = new Scanner(System.in);
        try {
            readInputFromScanner(scanner, false);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}




