package utility;

import characters.Student;
import characters.Teacher;
import items.*;
import map.Door;
import map.Room;

import java.util.ArrayList;
import java.util.Scanner;

public class Skeleton {

    private static ArrayList<TestCase> testCases = new ArrayList<>();

    public void win(){
        Logger.logCall("win", "void");
        Logger.logReturn();
    }

    public static void main(String[] args) {
        testCases.add(Skeleton::testCase1);
        testCases.add(Skeleton::testCase2);
        testCases.add(Skeleton::testCase3);
        testCases.add(Skeleton::testCase4);
        testCases.add(Skeleton::testCase5);
        testCases.add(Skeleton::testCase6);
        testCases.add(Skeleton::testCase7);
        testCases.add(Skeleton::testCase8);
        testCases.add(Skeleton::testCase9);
        testCases.add(Skeleton::testCase10);
        testCases.add(Skeleton::testCase11);
        testCases.add(Skeleton::testCase12);
        testCases.add(Skeleton::testCase13);
        testCases.add(Skeleton::testCase14);
        testCases.add(Skeleton::testCase15);


        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a testcase from below!");

        while (!scanner.hasNextInt()) {
            System.out.println("Choose a number between 1 and " + testCases.size());
            scanner.next();
        }

        int number = scanner.nextInt();
        while (number < 1 || number > testCases.size()) {
            System.out.println("Choose a number between 1 and " + testCases.size());
            while (!scanner.hasNextInt()) {
                System.out.println("Choose a number between 1 and " + testCases.size());
                scanner.next();
            }
            number = scanner.nextInt();
        }
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

        Room r = new Room(1, 10, false); //1
        Student s = new Student();
        Teacher t = new Teacher();
        TVSZ tvsz = new TVSZ();

        r.addPerson(s); //2
        r.addPerson(t);

        s.setRoom(r);
        t.setRoom(r);

        s.pickup(tvsz); //3

        tvsz.setOwner(s); //4
        //end of init3

        t.initAttack();

    }

    private static void testCase4() {
        System.out.println("init4");

        Room r = new Room(1, 10, false); //1
        Teacher t = new Teacher();

        r.addPerson(t); //2

        t.setRoom(r);

        r.gas(); //3
        //end of init4

        t.gasStun();

    }

    private static void testCase5() {
        System.out.println("init5");

        Room r = new Room(1, 10, false); //1
        Student s = new Student();
        Teacher t = new Teacher();
        Cloth c = new Cloth();

        r.addPerson(s); //2
        r.addPerson(t);

        s.setRoom(r);
        t.setRoom(r);

        s.pickup(c); //3

        c.setOwner(s); //4
        //end of init5

        s.initActivate(c);
    }

    private static void testCase6() {
        System.out.println("init6");

        Room r = new Room(1, 10, false);    //1
        Student s = new Student();
        Teacher t = new Teacher();
        Camembert c = new Camembert();

        r.addPerson(t); //2
        r.addPerson(s);
        s.setRoom(r);
        t.setRoom(r);

        s.pickup(c);    //3

        c.setOwner(s);  //4

        s.initActivate(c);
    }

    private static void testCase7() {
        System.out.println("init7");

        Room r = new Room(1, 10, false);    //1
        r.gas();
        Student s = new Student();
        TVSZ t = new TVSZ();
        r.addPerson(s); //2
        s.setRoom(r);
        s.pickup(t);    //3
        t.setOwner(s);  //4
        r.tick();
    }

    private static void testCase8() {
        System.out.println("init8");

        Room r = new Room(1, 10, false);    //1
        r.gas();
        Student s = new Student();
        Mask m = new Mask();
        r.addPerson(s); //2
        s.setRoom(r);
        s.pickup(m);    //3
        m.setOwner(s);  //4
        r.tick();
    }

    private static void testCase9() {
        System.out.println("init9---------------------------------");

        Room room = new Room(1, 10, false);
        Logger.logCreate(room, "Room", "room", new Object[]{1, 10, false});
        Student student1 = new Student();
        Logger.logCreate(student1, "Student", "student1");
        Teacher teacher1 = new Teacher();
        Logger.logCreate(teacher1, "Teacher", "teacher1");
        Beer beer = new Beer();
        Logger.logCreate(beer, "Beer", "beer");
        TVSZ tvsz = new TVSZ();
        Logger.logCreate(tvsz, "TVSZ", "tvsz");

        room.addPerson(student1);
        room.addPerson(teacher1);
        student1.addItem(tvsz);
        student1.addItem(beer);
        student1.setRoom(room);
        teacher1.setRoom(room);
        beer.setOwner(student1);
        tvsz.setOwner(student1);
        System.out.println("End of init9--------------------------");

        if (Logger.testerInput("Would you like to activate the Beer you have?")) {
            beer.activate();
        }
        teacher1.initAttack();

    }

    private static void testCase10() {
        System.out.println("init10--------------------------------");
        Room r1 = new Room(1, 10, false);
        Logger.logCreate(r1, "Room", "r1", new Object[]{1, 10, false});
        Room r3 = new Room(3, 10, false);
        Logger.logCreate(r3, "Room", "r3", new Object[]{3, 10, false});
        Door d1 = new Door();
        Logger.logCreate(d1, "Door", "d1");

        r1.addDoor(d1);
        r3.addDoor(d1);
        d1.setSrc(r1);
        d1.setDest(r3);
        System.out.println("End of init10-------------------------");

        r1.divide();

    }

    private static void testCase11() {
        System.out.println("init11--------------------------------");
        Room r1 = new Room(1, 10, false);
        Logger.logCreate(r1, "Room", "r1", new Object[]{1, 10, false});
        Room r2 = new Room(2, 10, false);
        Logger.logCreate(r2, "Room", "r2", new Object[]{2, 10, false});
        Door d1 = new Door();
        Logger.logCreate(d1, "Door", "d1");
        Door d2 = new Door();
        Logger.logCreate(d2, "Door", "d2");

        r1.addDoor(d1);
        r2.addDoor(d1);
        r2.addDoor(d2);
        d1.setSrc(r1);
        d1.setDest(r2);

        System.out.println("End of init11-------------------------");

        r1.merge();

    }

    private static void testCase12() {
        System.out.println("init12--------------------------------");

        SlideRule sr = new SlideRule();
        Room r = new Room(1, 10, false);
        Logger.logCreate(r, "Room", "r", new Object[]{1, 10, false});
        Student s = new Student();
        Logger.logCreate(s, "Student", "s");

        sr.changeRoom(r);
        s.setRoom(r);
        r.addPerson(s);
        r.addItem(sr);

        System.out.println("End of init12-------------------------");

        s.pickup(sr);

    }

    private static void testCase13() {
        System.out.println("init13--------------------------------");

        SlideRule sr = new SlideRule();
        Logger.logCreate(sr, "SlideRule", "sr");
        Room r = new Room(1, 10, false);
        Logger.logCreate(r, "Room", "r", new Object[]{1, 10, false});
        Teacher t = new Teacher();
        Logger.logCreate(t, "Teacher", "t");

        t.setRoom(r);
        sr.changeRoom(r);
        r.addPerson(t);
        r.addItem(sr);

        System.out.println("End of init13-------------------------");

        t.pickup(sr);
    }

    private static void testCase14() {
        System.out.println("init14--------------------------------");

        Room r1 = new Room(1, 10, false);
        Logger.logCreate(r1, "Room", "r1", new Object[]{1, 10, false});

        Room r2 = new Room(2, 10, false);
        Logger.logCreate(r2, "Room", "r2", new Object[]{2, 10, false});

        Door d = new Door(r1, r2);
        Logger.logCreate(d, "Door", "d");

        Student s = new Student();
        Logger.logCreate(s, "Student", "s");

        Transistor t1 = new Transistor();
        Logger.logCreate(t1, "Transistor", "t1");
        Transistor t2 = new Transistor();
        Logger.logCreate(t2, "Transistor", "t2");

        s.setRoom(r1);
        r1.addPerson(s);
        s.addItem(t1);
        s.addItem(t2);

        t1.setOwner(s);
        t2.setOwner(s);

        System.out.println("End of init14-------------------------");

        s.initActivate(t1);

        s.initActivate(t2);

        t1.setPair(t2);
        t2.setPair(t1);

        s.drop(t1);
        if (Logger.testerInput("Is the room not full?")) {
            s.move(d);
            s.drop(t2);
        }
    }

    private static void testCase15() {
        System.out.println("init15--------------------------------");
        Room r1 = new Room(1, 10, false);
        Logger.logCreate(r1, "Room", "r", new Object[]{1, 10, false});

        Room r2 = new Room(1, 10, false);
        Logger.logCreate(r2, "Room", "r", new Object[]{2, 10, false});

        Teacher t = new Teacher();
        Logger.logCreate(t, "Teacher", "t");

        Transistor t1 = new Transistor();
        Logger.logCreate(t1, "Transistor", "t1");
        Transistor t2 = new Transistor();
        Logger.logCreate(t2, "Transistor", "t2");

        t.setRoom(r1);
        t1.changeRoom(r1);
        t2.changeRoom(r2);

        t1.setPair(t2);
        t2.setPair(t1);

        System.out.println("End of init15-------------------------");

        t.pickup(t1);
    }
}



