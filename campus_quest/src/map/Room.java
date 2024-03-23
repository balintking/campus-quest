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
    }

    //: A paraméterként kapott tárgyat hozzáadja a szobához.
    public void addItem(Item item) {
    }

    //: A paraméterként kapott személyt hozzáadja a szobához.
    public void addPerson(Person person) {
        people.add(person);
    }

    //: A szoba megszűnését jelentő függvény.
    public void destroy() {
    }

    //: A szoba két részre osztódik.
    public void divide() {
    }

    //: A szoba elgázosodik.
    public void gas() {
    }

    //: Összeolvad egy találomra kiválasztott szomszédjával. 
    public void merge() {
    }

    //): A paraméterként kapott ajtót eltávolítja a szobából.
    public void removeDoor(Door door) {
    }

    // A paraméterként kapott tárgyat eltávolítja a szobából.
    public void removeItem(Item item) {
    }

    //: A paraméterként kapott személyt kiveszi a szobából.
    public void removePerson(Person person) {
    }


    public List<Person> getPeople() {
        Logger.logCall("getPeople", "List<Person>");
        Logger.logReturn();
        return people;
    }

    public boolean isFull(){
        return people.size() >= capacity;
    }

    public void tick() {
        if (gassed){
            for (Person p : people)
                p.gasStun();
        }
    }
}
