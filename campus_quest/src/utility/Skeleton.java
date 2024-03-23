package utility;

import characters.Student;
import characters.Teacher;
import items.*;
import map.Room;

import java.util.ArrayList;
import java.util.Scanner;

public class Skeleton {

    private static ArrayList<TestCase> testCases = new ArrayList<>();

    public static void main(String[] args) {
        testCases.add(Skeleton::testCase1);
        testCases.add(Skeleton::testCase2);
        testCases.add(Skeleton::testCase3);
        testCases.add(Skeleton::testCase4);
        testCases.add(Skeleton::testCase5);
        testCases.add(Skeleton::testCase6);
        testCases.add(Skeleton::testCase7);
        testCases.add(Skeleton::testCase8);


        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a testcase from below!");

        while (!scanner.hasNextInt()) {
            System.out.println("Choose a number between 1 and " + testCases.size());
            scanner.next();
        }

        int number = scanner.nextInt();
        while (number < 1 || number > testCases.size()){
            System.out.println("Choose a number between 1 and " + testCases.size());
            while (!scanner.hasNextInt()) {
                System.out.println("Choose a number between 1 and " + testCases.size());
                scanner.next();
            }
            number = scanner.nextInt();
        }
        scanner.close();
        testCases.get(number - 1).execute();
    }


    private static void testCase1() {
        System.out.println("init1");
    }
    private static void testCase2() {
        System.out.println("init2");
    }
    private static void testCase3() {
        System.out.println("init3");

        Room r = new Room(1, 10, false, false);
        Student s = new Student("student1");
        Teacher t = new Teacher("teacher1");
        TVSZ tvsz = new TVSZ();

        r.addPerson(s);
        r.addPerson(t);
        
        s.setRoom(r);
        t.setRoom(r);

        s.pickup(tvsz);

        tvsz.setOwner(s);
    }
    private static void testCase4() {
        System.out.println("init4");

        Room r = new Room(1, 10, false, false);
        Teacher t = new Teacher("teacher1");

        r.addPerson(t);

        t.setRoom(r);

        r.gas();
    }
    private static void testCase5() {
        System.out.println("init5");

        Room r = new Room(1, 10, false, false);
        Student s = new Student("student1");
        Teacher t = new Teacher("teacher1");
        Cloth c = new Cloth();

        r.addPerson(s);
        r.addPerson(t);
        
        s.setRoom(r);
        t.setRoom(r);

        s.pickup(c);

        c.setOwner(s);
    }
    private static void testCase6() {
        System.out.println("init6");

        Room r = new Room(1, 10, false, false);    //1
        Student s = new Student(r);
        Teacher t = new Teacher(r);
        Camembert c = new Camembert();

        r.addPerson(t); //2
        r.addPerson(s);

        s.pickup(c);    //3

        c.setOwner(s);  //4

        s.initActivate(c);
    }

    private static void testCase7(){
        System.out.println("init7");

        Room r = new Room(1, 10, true, false);    //1
        Student s = new Student(r);
        TVSZ t = new TVSZ();
        r.addPerson(s); //2
        s.pickup(t);    //3
        t.setOwner(s);  //4
        r.tick();
    }

    private static void testCase8(){
        System.out.println("init8");

        Room r = new Room(1, 10, true, false);    //1
        Student s = new Student(r);
        Mask m = new Mask();
        r.addPerson(s); //2
        s.pickup(m);    //3
        m.setOwner(s);  //4
        r.tick();
    }
}



