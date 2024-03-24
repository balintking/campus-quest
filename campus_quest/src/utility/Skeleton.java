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

        Room r = new Room(1, 10, false, false); //1
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

        Room r = new Room(1, 10, false, false); //1
        Teacher t = new Teacher();

        r.addPerson(t); //2

        t.setRoom(r);

        r.gas(); //3
        //end of init4

        t.gasStun();

    }
    private static void testCase5() {
        System.out.println("init5");

        Room r = new Room(1, 10, false, false); //1
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

        Room r = new Room(1, 10, false, false);    //1
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

    private static void testCase7(){
        System.out.println("init7");

        Room r = new Room(1, 10,true, false);    //1
        Student s = new Student();
        TVSZ t = new TVSZ();
        r.addPerson(s); //2
        s.setRoom(r);
        s.pickup(t);    //3
        t.setOwner(s);  //4
        r.tick();
    }

    private static void testCase8(){
        System.out.println("init8");

        Room r = new Room(1, 10, true, false);    //1
        Student s = new Student();
        Mask m = new Mask();
        r.addPerson(s); //2
        s.setRoom(r);
        s.pickup(m);    //3
        m.setOwner(s);  //4
        r.tick();
    }

    private static void testCase9() {
        System.out.println("init9");

        Room room = new Room(1,10,false,false);
        Student student1 = new Student();
        Teacher teacher1 = new Teacher();
        Beer beer = new Beer();
        TVSZ tvsz = new TVSZ();

        room.addPerson(student1);
        room.addPerson(teacher1);
        student1.addItem(tvsz);
        student1.addItem(beer);
        student1.setRoom(room);
        teacher1.setRoom(room);
        beer.setOwner(student1);
        tvsz.setOwner(student1);

        teacher1.initAttack();

    }

    private static void testCase10() {
        System.out.println("init10");


    }

    private static void testCase11() {
        System.out.println("init11");


    }

    private static void testCase12(){
        System.out.println("init12");

        SlideRule sr = new SlideRule();
        Room r = new Room(1,10, false, false);
        Student s = new Student();

        sr.changeRoom(r);
        s.setRoom(r);
        r.addPerson(s);
        r.addItem(sr);

        s.pickup(sr);
        r.removeItem(sr);
        sr.setOwner(s);
        s.slideRuleNotification(sr);

    }
    private static void testCase13(){
        System.out.println("init12");

        SlideRule sr = new SlideRule();
        Room r = new Room(1,10, false, false);
        Teacher t = new Teacher();

        t.setRoom(r);
        sr.changeRoom(r);
        r.addPerson(t);
        r.addItem(sr);

        t.pickup(sr);
        r.removeItem(sr);
        sr.setOwner(t);
        t.slideRuleNotification(sr);
        sr.setOwner(null);
        r.addItem(sr);
//        We are not taking the item away from the person

    }

    private static void testCase14(){
        Room r1 = new Room(1,10,false,false);
        Room r2 = new Room(2,10,false, false);
        Door d = new Door(r1, r2);

        Student s = new Student();

        Transistor t1 = new Transistor();
        Transistor t2 = new Transistor();

        s.setRoom(r1);
        r1.addPerson(s);
        s.addItem(t1);
        s.addItem(t2);

        t1.setOwner(s);
        t2.setOwner(s);

        s.initActivate(t1);
        t1.activate();

        s.initActivate(t2);
        t2.activate();

        s.drop(t1);

        s.move(d);
//        Ask from tester if the room is full
        s.drop(t2);

        t2.changeRoom(r2);
        t1.getRoom();
        s.teleport(r1);
//        Ask from tester if the room is full
        r2.removePerson(s);
        r1.addPerson(s);

        t2.deactivate();
        t1.deactivate();
    }
    private static void testCase15(){
        Room r = new Room(1,10, false, false);
        Teacher t = new Teacher();

        Transistor t1 = new Transistor();
        Transistor t2 = new Transistor();

        t.setRoom(r);
        t1.changeRoom(r);

        t.pickup(t1);
        r.removeItem(t1);

        t1.destroy();
        t2.reset();
    }
}



