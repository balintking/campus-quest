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

    public void win() {
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
        testCases.add(Skeleton::testCase16);
        testCases.add(Skeleton::testCase17);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose a testcase from below! Enter 0 for exit.");

            //König
            System.out.println("1. Student picks up a Beer item");
            System.out.println("2. Teacher picks up a Beer item");
            System.out.println("3. Teacher kills Student");
            System.out.println("4. Teacher attacks another teacher");

            //Lilla
            System.out.println("5. TVSZ use");
            System.out.println("6. Teacher in gassed room");
            System.out.println("7. Cloth use");

            //Miki
            System.out.println("8. Camembert use");
            System.out.println("9. Student in gassed room");
            System.out.println("10. Mask use");

            //Máté
            System.out.println("11. Student with Beer and TVSZ is attacked by Teacher");
            System.out.println("12. Room division");
            System.out.println("13. Room merge");

            //Jan
            System.out.println("14. Student picks the SlideRule up");
            System.out.println("15. Teacher picks the SlideRule up");
            System.out.println("16. Transistor use");
            System.out.println("17. Teacher picks up a Transistor item");


            while (!scanner.hasNextInt()) {
                System.out.println("Choose a number between 1 and " + testCases.size());
                System.out.println("Enter 0 for exit");
                scanner.next();
            }

            int number = scanner.nextInt();
            while (number < 0 || number > testCases.size()) {
                System.out.println("Choose a number between 1 and " + testCases.size());
                System.out.println("Enter 0 for exit");
                while (!scanner.hasNextInt()) {
                    System.out.println("Choose a number between 1 and " + testCases.size());
                    System.out.println("Enter 0 for exit");
                    scanner.next();
                }
                number = scanner.nextInt();
            }
            if (number == 0) {
                break;
            }
            Logger.reset();
            testCases.get(number - 1).execute();
        }
    }

    /**
     * The student picks up an item. If the inventory is not full it adds the item to the student's inventory
     * and removes it from the room. In this case, the item is a beer glass.
     * The behavior is consistent with other items, except for the slide rule.
     */
    private static void testCase1() {
        System.out.println("init1--------------------------------");
        Room room = new Room(1, 10, false);
        Logger.logCreate(room, "Room", "room", new Object[]{1, 10, false});
        Student student = new Student();
        Logger.logCreate(student, "Student", "student");
        Teacher teacher = new Teacher();
        Logger.logCreate(teacher, "Teacher", "teacher");
        Beer beer = new Beer();
        Logger.logCreate(beer, "Beer", "beer");

        room.addPerson(student);
        room.addPerson(teacher);
        room.addItem(beer);
        student.setRoom(room);
        teacher.setRoom(room);
        System.out.println("End of init1-------------------------");

        student.pickup(beer);
    }

    /**
     * When a teacher picks up an item, it is destroyed immediately. In this case, the item is a beer glass.
     * This behavior is consistent with other items, except for the slide rule.
     */
    private static void testCase2() {
        System.out.println("init1--------------------------------");
        Room room = new Room(1, 10, false);
        Logger.logCreate(room, "Room", "room", new Object[]{1, 10, false});
        Student student = new Student();
        Logger.logCreate(student, "Student", "student");
        Teacher teacher = new Teacher();
        Logger.logCreate(teacher, "Teacher", "teacher");
        Beer beer = new Beer();
        Logger.logCreate(beer, "Beer", "beer");

        room.addPerson(student);
        room.addPerson(teacher);
        room.addItem(beer);
        student.setRoom(room);
        teacher.setRoom(room);
        System.out.println("End of init1-------------------------");

        teacher.pickup(beer);
    }

    private static void testCase3() {
        System.out.println("init1");
    }

    private static void testCase4() {
        System.out.println("init2");
    }

    /**
     * A Teacher attacks a Student because they are in the same room,
     * but since the Student has a TVSZ, it defends them against the Teacher.
     */
    private static void testCase5() {
        System.out.println("init3---------------------------------");

        Room room = new Room(1, 10, false); //1
        Logger.logCreate(room, "Room", "room", new Object[]{1, 10, false});
        Student student1 = new Student();
        Logger.logCreate(student1, "Student", "student1");
        Teacher teacher1 = new Teacher();
        Logger.logCreate(teacher1, "Teacher", "teacher1");
        TVSZ tvsz = new TVSZ();
        Logger.logCreate(tvsz, "TVSZ", "tvsz");

        room.addPerson(student1); //2
        room.addPerson(teacher1);

        student1.setRoom(room);
        teacher1.setRoom(room);

        student1.pickup(tvsz); //3

        tvsz.setOwner(student1); //4

        System.out.println("End of init3-------------------------");

        teacher1.initAttack();
    }


    /**
     * A Teacher is attacked by a gassed room. The Teacher gets gassed.
     */
    private static void testCase6() {
        System.out.println("init4---------------------------------");

        Room room = new Room(1, 10, false); //1
        Logger.logCreate(room, "Room", "r", new Object[]{1, 10, false});
        Teacher teacher1 = new Teacher();
        Logger.logCreate(teacher1, "Teacher", "teacher1");

        room.addPerson(teacher1); //2

        teacher1.setRoom(room);

        room.gas(); //3

        System.out.println("End of init4-------------------------");

        teacher1.gasStun();

    }

    /**
     * A Student activates the Cloth they have, which stuns the Teachers in the room.
     */
    private static void testCase7() {
        System.out.println("init5---------------------------------");

        Room room = new Room(1, 10, false); //1
        Logger.logCreate(room, "Room", "r", new Object[]{1, 10, false});
        Student student1 = new Student();
        Logger.logCreate(student1, "Student", "student1");
        Teacher teacher1 = new Teacher();
        Logger.logCreate(teacher1, "Teacher", "teacher1");
        Cloth cloth = new Cloth();
        Logger.logCreate(cloth, "Cloth", "c");

        room.addPerson(student1); //2
        room.addPerson(teacher1);

        student1.setRoom(room);
        teacher1.setRoom(room);

        student1.pickup(cloth); //3

        cloth.setOwner(student1); //4

        System.out.println("End of init5--------------------------");

        student1.initActivate(cloth);
    }

    private static void testCase8() {
        System.out.println("init6---------------------------------");

        Room r = new Room(1, 10, false);    //1
        Logger.logCreate(r, "Room", "r", new Object[]{1, 10, false});
        Student s = new Student();
        Logger.logCreate(s, "Student", "s");
        Teacher t = new Teacher();
        Logger.logCreate(t, "Teacher", "t");
        Camembert c = new Camembert();
        Logger.logCreate(c, "Camembert", "c");

        r.addPerson(t); //2
        r.addPerson(s);
        s.setRoom(r);
        t.setRoom(r);

        s.pickup(c);    //3

        c.setOwner(s);  //4
        System.out.println("End of init6--------------------------");
        s.initActivate(c);
    }

    private static void testCase9() {
        System.out.println("init7---------------------------------");

        Room r = new Room(1, 10, false);    //1
        Logger.logCreate(r, "Room", "r", new Object[]{1, 10, false});
        r.gas();
        Student s = new Student();
        Logger.logCreate(s, "Student", "s");
        TVSZ t = new TVSZ();
        Logger.logCreate(t, "TVSZ", "t");
        r.addPerson(s); //2
        s.setRoom(r);
        s.pickup(t);    //3
        t.setOwner(s);  //4
        System.out.println("End of init7--------------------------");
        r.tick();
    }

    private static void testCase10() {
        System.out.println("init8---------------------------------");

        Room r = new Room(1, 10, false);    //1
        Logger.logCreate(r, "Room", "r", new Object[]{1, 10, false});
        r.gas();
        Student s = new Student();
        Logger.logCreate(s, "Student", "s");
        Mask m = new Mask();
        Logger.logCreate(m, "Mask", "m");
        r.addPerson(s); //2
        s.setRoom(r);
        s.pickup(m);    //3
        m.setOwner(s);  //4
        System.out.println("End of init8--------------------------");
        r.tick();
    }

    /**
     * A teacher attacks a student because they are in the same room.
     * The student has a Beer and a TVSZ with them.
     * If the Beer is not activated, then the TVSZ will provide protection; however,
     * if it is activated, then the Beer will provide defense because it always has higher priority.
     */
    private static void testCase11() {
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

    /**
     * In each round, rooms can randomly 'decide' to split.
     * The splitting room divides into two rooms that become neighbors,
     * and they share the properties and neighbors of the original room.
     * After the split, a two-way door is created between the two 'new' rooms.
     * The capacity of the two 'new' rooms will be equal to the capacity of the original room.
     */
    private static void testCase12() {
        System.out.println("init10--------------------------------");
        Room r1 = new Room(1, 10, false);
        Logger.logCreate(r1, "Room", "r1", new Object[]{1, 10, false});
        Room r3 = new Room(2, 10, false);
        Logger.logCreate(r3, "Room", "r3", new Object[]{2, 10, false});
        Door d1 = new Door();
        Logger.logCreate(d1, "Door", "d1");

        r1.addDoor(d1);
        r3.addDoor(d1);
        d1.setSrc(r1);
        d1.setDest(r3);
        System.out.println("End of init10-------------------------");

        Logger.logCall("tick", "void");
        r1.divide();
        Logger.logReturn();

    }

    /**
     * In each round, rooms can randomly 'decide' to merge with one of their neighbors.
     * The new room formed by the merger inherits the properties, neighbors, items, and entities of the two original rooms,
     * but its capacity will be equal to the capacity of the larger room.
     * Two rooms can only merge if the sum of entities in both rooms does not exceed the capacity of the larger room.
     */

//    BELSŐ DÖNTÉS A MERGE
    private static void testCase13() {
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

        Logger.logCall("tick", "void");
        r1.merge();
        Logger.logReturn();

    }

    /**
     * A Student picks up a slideRule in a room. The game is won.
     */
    private static void testCase14() {
        System.out.println("init12--------------------------------");

        SlideRule sr = new SlideRule();
        Room r = new Room(1, 10, false);
        Logger.logCreate(r, "Room", "r", new Object[]{1, 10, false});
        Student s = new Student();
        Logger.logCreate(s, "Student", "s");

        sr.setRoom(r);
        s.setRoom(r);
        r.addPerson(s);
        r.addItem(sr);

        System.out.println("End of init12-------------------------");

        s.pickup(sr);

    }

    /**
     * A teacher picks up a slideRule in a room. It immediately drops it thereafter.
     */
    private static void testCase15() {
        System.out.println("init13--------------------------------");

        SlideRule sr = new SlideRule();
        Logger.logCreate(sr, "SlideRule", "sr");
        Room r = new Room(1, 10, false);
        Logger.logCreate(r, "Room", "r", new Object[]{1, 10, false});
        Teacher t = new Teacher();
        Logger.logCreate(t, "Teacher", "t");

        t.setRoom(r);
        sr.setRoom(r);
        r.addPerson(t);
        r.addItem(sr);

        System.out.println("End of init13-------------------------");

        t.pickup(sr);
    }

    /**
     * A student is in a room and it has two transistors. It activates these.
     * After this it goes to another room, where it drops the other one and with that it is teleported
     * to the room it came from. The transistors are deactivated.
     */
    private static void testCase16() {
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

    /**
     * A teacher picks up a dropped transistor, which is connected to another.
     * The Teacher destroys it and with that the pair is reset.
     */
    private static void testCase17() {
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
        t1.setRoom(r1);
        t2.setRoom(r2);

        t1.setPair(t2);
        t2.setPair(t1);

        System.out.println("End of init15-------------------------");

        t.pickup(t1);
    }
}



