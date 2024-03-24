package map;

import characters.Person;
import items.Item;
import utility.Entity;
import utility.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room implements Entity {
    List<Person> people = new ArrayList<>(); //Person objects in the room
    List<Door> doors = new ArrayList<>(); //Doors in the room.
    List<Item> items = new ArrayList<>(); //Item on the room's floor
    int id; //Room's id
    int capacity; //Amount of Person objects allowed in the room at a certain time
    boolean gassed; //Is there gas in the room
    boolean cursed; //Is the room cursed

    public Room(int id, int capacity, boolean cursed) {
        this.id = id;
        this.capacity = capacity;
        this.cursed = cursed;
    }

    //: A paraméterként kapott ajtót hozzáadja a szobához.
    public void addDoor(Door door) {
        Logger.logCall("addDoor",new Object[]{door},"void");
        doors.add(door);
        Logger.logReturn();
    }

    //: A paraméterként kapott tárgyat hozzáadja a szobához.
    public void addItem(Item item) {
        Logger.logCall("addItem",new Object[]{item},"void");
        items.add(item);
        Logger.logReturn();
    }

    //: A paraméterként kapott személyt hozzáadja a szobához.
    public void addPerson(Person person) {
        Logger.logCall("addPerson",new Object[]{person},"void");
        people.add(person);
        Logger.logReturn();
    }

    //: A szoba megszűnését jelentő függvény.
    public void destroy() {
        Logger.logCall("destroy","void");
        Logger.logDestroy(this,"Room");
        Logger.logReturn();
    }

    //: A szoba két részre osztódik.
    public void divide() {
        Logger.logCall("divide","void");
        Map<Room,Door> neighbors = new HashMap<>();
        for(Door d : doors) {
            neighbors.put(d.getDest(),d);
        }
        Room r2 = new Room(2,10,false);
        Logger.logCreate(r2,"Room","r2",new Object[]{2,10,false});
        Door d2 = new Door();
        Logger.logCreate(d2,"Door","d2");
        d2.setSrc(this);
        d2.setDest(r2);
        this.addDoor(d2);
        r2.addDoor(d2);
        if(Logger.testerInput("Is r2 getting r3 as its neighbor?")) {
            for(Map.Entry<Room,Door> n : neighbors.entrySet()) {
                Door d =  n.getValue();
                d.setSrc(r2);
                this.removeDoor(d);
                r2.addDoor(d);
            }
        }
        Logger.logReturn();
    }

    //: A szoba elgázosodik.
    public void gas() {
        gassed = true;
    }

    //: Összeolvad egy találomra kiválasztott szomszédjával. 
    public void merge() {
        Logger.logCall("merge","void");
        Room neighbour = doors.get(0).getDest();
        List<Door> neighbourDoors = neighbour.getDoors();
        neighbour.destroy();
        for(Door d : neighbourDoors) {
            d.destroy();
        }
        Logger.logReturn();
    }

    //): A paraméterként kapott ajtót eltávolítja a szobából.
    public void removeDoor(Door door) {
        Logger.logCall("removeDoor",new Object[]{door},"void");
        doors.remove(door);
        Logger.logReturn();
    }

    // A paraméterként kapott tárgyat eltávolítja a szobából.
    public void removeItem(Item item) {
        Logger.logCall("removeItem",new Object[]{item},"void");
        items.remove(item);
        Logger.logReturn();
    }

    //: A paraméterként kapott személyt kiveszi a szobából.
    public void removePerson(Person person) {
        Logger.logCall("removePerson",new Object[]{person},"void");
        people.remove(person);
        Logger.logReturn();
    }


    public List<Person> getPeople() {
        Logger.logCall("getPeople", "List<Person>");
        Logger.logReturn(people);
        return people;
    }

    public List<Door> getDoors() {
        Logger.logCall("getDoors","List<Door>");
        Logger.logReturn(doors);
        return doors;
    }

    public boolean isFull(){
        Logger.logCall("isFull","boolean");
        Logger.logReturn(people.size() >= capacity);
        return people.size() >= capacity;
    }

    public void tick() {
        Logger.logCall("tick", "void");
        if (gassed){
            for (Person p : people)
                p.gasStun();
        }
        Logger.logReturn();
    }
}
