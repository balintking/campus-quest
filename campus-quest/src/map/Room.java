package map;

import characters.Person;
import items.Item;
import utility.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Room implements Entity, Serializable {
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

    int cleanliness = 10;
    /**
     * Is there gas in the room.
     */
    boolean gassed;
    /**
     * Is the room cursed.
     */
    boolean cursed;
    private boolean destroyed = false;

    /**
     * Default constructor to help testing
     */
    public Room() {
        capacity = 10;
        cursed = false;
    }

    /**
     * Constructor.
     */
    public Room(int id, int capacity, boolean cursed) {
        this.id = id;
        this.capacity = capacity;
        this.cursed = cursed;
    }

    /**
     * Adds the Door received as a parameter to the room.
     */
    public void addDoor(Door door) {
        doors.add(door);
    }

    /**
     * Adds the Item received as a parameter to the room.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Adds the Person received as a parameter to the room.
     */
    public boolean addPerson(Person person) {
        if (capacity == people.size()) {
            return false;
        } else {
            people.add(person);
            cleanliness--;
            return true;
        }
    }


    /**
     * Function indicating the termination of the room.
     * Practically no use
     */
    public void destroy() {
        destroyed = true;
    }

    /**
     * The room divides into two rooms. After division, each of the two resulting
     * rooms will have the same capacity as the original room. A two-way door will
     * be created between the two "new" rooms formed after division.
     */
    public void divide() {
        Map<Room, Door> neighbors = new HashMap<>();
        for (Door d : doors) {
            neighbors.put(d.getDest(), d);
        }
        Room r2 = new Room(2, capacity, cursed);
        Door d1 = new Door();
        Door d2 = new Door();
        d1.setSrc(this);
        d1.setDest(r2);
        d2.setSrc(r2);
        d2.setDest(this);
        this.addDoor(d1);
        r2.addDoor(d2);
        for (Map.Entry<Room, Door> n : neighbors.entrySet()) {
            Door d = n.getValue();
            d.setSrc(r2);
            this.removeDoor(d);
            r2.addDoor(d);
        }
    }

    /**
     * The room becomes gased.
     */
    public void gas() {
        gassed = true;
    }

    public void unGas() {
        gassed = false;
    }


    /**
     * The room merges with a random neighbouring room.
     * Checks if there is enough space to accommodate all people, if not it returns without merging.
     */
    public void merge() {
        if (!doors.isEmpty()) {
            Random rand = new Random();
            int value = rand.nextInt(doors.size());
            Room neighbour = doors.get(value).getDest();
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
            for(Door d : doors) {
                if(d.getDest() == neighbour) {
                    d.destroy();
                    break;
                }
            }
            List<Door> neighbourDoors = new ArrayList<>(neighbour.getDoors());
            for (int i = 0; i < neighbour.getDoors().size(); i++) {
                neighbourDoors.get(i).destroy();
            }
            neighbourDoors.clear();
            neighbour.setDoors(neighbourDoors);
            neighbour.destroy();
        }
    }

    /**
     * Removes the door received as a parameter from the room.
     */
    public void removeDoor(Door door) {
        doors.remove(door);
    }

    /**
     * Removes the item received as a parameter from the room.
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Removes the person received as a parameter from the room.
     */
    public void removePerson(Person person) {
        people.remove(person);
    }


    /**
     * Returns the list of Person in the room.
     *
     * @return people
     */
    public List<Person> getPeople() {
        return people;
    }

    /**
     * Returns the list of doors in the room.
     *
     * @return doors
     */
    public List<Door> getDoors() {
        return doors;
    }

    public void setDoors(List<Door> d){
        doors = d;
    }

    /**
     * Returns the list of items in the room
     *
     * @return items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * With a tick passing the Room gasses all the people in it.
     * If the room is cursed it ticks its doors where the door decides if it disappears
     * or not based on chance.
     * What happens if there are two-way doors ?!
     */
    public void tick() {
        if (gassed) {
            for (Person p : people)
                p.gasStun();
        }
        if (cursed) {
            for (Door d : doors) {
                d.tick();
            }
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isGassed() { return gassed; }
    public boolean isCursed() {return cursed; }

    public void evacuate() {
        for (Person p : people) {
            int value = 0;
            boolean moved = false;
            while (!moved && !doors.isEmpty()) {
                moved = p.move(doors.get(value));
                if (value == doors.size() - 1) {
                    moved = true;
                }
                value++;
            }
        }
    }

    public void clean() {
        cleanliness = 10;
    }

}
