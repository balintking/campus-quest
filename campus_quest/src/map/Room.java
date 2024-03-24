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
    /**
     * Person objects in the room.
     */
    List<Person> people = new ArrayList<>();
    /**
     * Doors in the room.
     */
    List<Door> doors = new ArrayList<>();
    /**
     * Item on the room's floor.
     */
    List<Item> items = new ArrayList<>();
    /**
     * Room's id.
     */
    int id;
    /**
     * Amount of Person objects allowed in the room at a certain time.
     */
    int capacity;
    /**
     * Is there gas in the room.
     */
    boolean gassed;
    /**
     * Is the room cursed.
     */
    boolean cursed;

    /**
     * Constructor.
     *
     * @param id
     * @param capacity
     * @param cursed
     */
    public Room(int id, int capacity, boolean cursed) {
        this.id = id;
        this.capacity = capacity;
        this.cursed = cursed;
    }

    /**
     * Adds the Door received as a parameter to the room.
     *
     * @param door
     */
    public void addDoor(Door door) {
        Logger.logCall("addDoor", new Object[]{door}, "void");
        doors.add(door);
        Logger.logReturn();
    }

    /**
     * Adds the Item received as a parameter to the room.
     *
     * @param item
     */
    public void addItem(Item item) {
        Logger.logCall("addItem", new Object[]{item}, "void");
        items.add(item);
        Logger.logReturn();
    }

    /**
     * Adds the Person received as a parameter to the room.
     *
     * @param person
     */
    public void addPerson(Person person) {
        Logger.logCall("addPerson", new Object[]{person}, "void");
        people.add(person);
        Logger.logReturn();
    }


    /**
     * Function indicating the termination of the room.
     */
    public void destroy() {
        Logger.logCall("destroy", "void");
        Logger.logDestroy(this, "Room");
        Logger.logReturn();
    }

    /**
     * The room divides into two rooms. After division, each of the two resulting
     * rooms will have the same capacity as the original room. A two-way door will
     * be created between the two "new" rooms formed after division.
     */
    public void divide() {
        Logger.logCall("divide", "void");
        Map<Room, Door> neighbors = new HashMap<>();
        for (Door d : doors) {
            neighbors.put(d.getDest(), d);
        }
        Room r2 = new Room(2, capacity, cursed);
        Logger.logCreate(r2, "Room", "r2", new Object[]{2, 10, false});
        Door d2 = new Door();
        Logger.logCreate(d2, "Door", "d2");
        d2.setSrc(this);
        d2.setDest(r2);
        this.addDoor(d2);
        r2.addDoor(d2);
        if (Logger.testerInput("Is r2 getting r3 as its neighbor?")) {
            for (Map.Entry<Room, Door> n : neighbors.entrySet()) {
                Door d = n.getValue();
                d.setSrc(r2);
                this.removeDoor(d);
                r2.addDoor(d);
            }
        }
        Logger.logReturn();
    }

    /**
     * The room becomes gased.
     */
    public void gas() {
        Logger.logCall("gas", "void");
        gassed = true;
        Logger.logReturn();
    }


    /**
     * The room merges with a random neighbouring room.
     * Checks if there is enough space to accommodate all people, if not it returns without merging.
     */
    public void merge() {
        Logger.logCall("merge", "void");
        Room neighbour = doors.get(0).getDest();
        this.capacity = Math.max(this.capacity, neighbour.capacity);
        List<Person> neighbourPeople = neighbour.getPeople();
        if (this.capacity < (people.size() + neighbourPeople.size())) {
            return;
        }
        List<Item> neighbourItems = neighbour.getItems();
        for (Item i : neighbourItems) {
            this.addItem(i);
            i.setRoom(this);
        }
        List<Door> neighbourDoors = neighbour.getDoors();
        neighbour.destroy();
        for (Door d : neighbourDoors) {
            d.destroy();
        }
        Logger.logReturn();
    }

    /**
     * Removes the door received as a parameter from the room.
     *
     * @param door
     */
    public void removeDoor(Door door) {
        Logger.logCall("removeDoor", new Object[]{door}, "void");
        doors.remove(door);
        Logger.logReturn();
    }

    /**
     * Removes the item received as a parameter from the room.
     *
     * @param item
     */
    public void removeItem(Item item) {
        Logger.logCall("removeItem", new Object[]{item}, "void");
        items.remove(item);
        Logger.logReturn();
    }

    /**
     * Removes the person received as a parameter from the room.
     *
     * @param person
     */
    public void removePerson(Person person) {
        Logger.logCall("removePerson", new Object[]{person}, "void");
        people.remove(person);
        Logger.logReturn();
    }


    /**
     * Returns the list of Person in the room.
     *
     * @return people
     */
    public List<Person> getPeople() {
        Logger.logCall("getPeople", "List<Person>");
        Logger.logReturn(people);
        return people;
    }
    /**
     * Returns the list of doors in the room.
     *
     * @return doors
     */
    public List<Door> getDoors() {
        Logger.logCall("getDoors", "List<Door>");
        Logger.logReturn(doors);
        return doors;
    }

    /**
     * Returns the list of items in the room
     *
     * @return items
     */
    public List<Item> getItems() {
        Logger.logCall("getItems", "List<Item>");
        Logger.logReturn(items);
        return items;
    }

    /**
     * Checks if the room is full
     *
     * @return people.size() >= capacity
     */
    public boolean isFull() {
        Logger.logCall("isFull", "boolean");
        Logger.logReturn(people.size() >= capacity);
        return people.size() >= capacity;
    }

    /**
     * With a tick passing the Room gasses all the people in it.
     */
    public void tick() {
        Logger.logCall("tick", "void");
        if (gassed) {
            for (Person p : people)
                p.gasStun();
        }
        Logger.logReturn();
    }
}
