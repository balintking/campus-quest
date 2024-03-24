package map;

import characters.Person;
import items.Item;
import utility.Entity;
import utility.Logger;

import java.util.ArrayList;
import java.util.List;

public class Room implements Entity {
    List<Person> people = new ArrayList<>(); //Person objects in the room
    List<Door> doors = new ArrayList<>(); //Doors in the room.
    List<Item> items = new ArrayList<>(); //Item on the room's floor
    int id; //Room's id
    int capacity; //Amount of Person objects allowed in the room at a certain time
    boolean gassed; //Is there gas in the room
    boolean cursed; //Is the room cursed

    public Room(int id, int capacity, boolean gassed, boolean cursed) {
        this.id = id;
        this.capacity = capacity;
        this.gassed = gassed;
        this.cursed = cursed;
    }

    //: A paraméterként kapott ajtót hozzáadja a szobához.
    public void addDoor(Door door) {
        Logger.logCall("addDoor",new Object[]{door},"void");
        Logger.logReturn();
    }

    //: A paraméterként kapott tárgyat hozzáadja a szobához.
    public void addItem(Item item) {
        Logger.logCall("addItem",new Object[]{item},"void");
        Logger.logReturn();
    }

    //: A paraméterként kapott személyt hozzáadja a szobához.
    public void addPerson(Person person) {
        Logger.logCall("addItem",new Object[]{person},"void");
        Logger.logReturn();
        people.add(person);
    }

    //: A szoba megszűnését jelentő függvény.
    public void destroy() {
        Logger.logCall("destroy","void");
        Logger.logReturn();
    }

    //: A szoba két részre osztódik.
    public void divide() {
        Logger.logCall("divide","void");
        Logger.logReturn();
    }

    //: A szoba elgázosodik.
    public void gas() {
        gassed = true;
    }

    //: Összeolvad egy találomra kiválasztott szomszédjával. 
    public void merge() {
        Logger.logCall("merge","void");
        Logger.logReturn();
    }

    //): A paraméterként kapott ajtót eltávolítja a szobából.
    public void removeDoor(Door door) {
        Logger.logCall("removeDoor",new Object[]{door},"void");
        Logger.logReturn();
    }

    // A paraméterként kapott tárgyat eltávolítja a szobából.
    public void removeItem(Item item) {
        Logger.logCall("removeItem",new Object[]{item},"void");
        Logger.logReturn();
    }

    //: A paraméterként kapott személyt kiveszi a szobából.
    public void removePerson(Person person) {
        Logger.logCall("removePerson",new Object[]{person},"void");
        Logger.logReturn();
    }


    public List<Person> getPeople() {
        Logger.logCall("getPeople", "List<Person>");
        Logger.logReturn(people);
        return people;
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
