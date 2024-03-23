package characters;

import items.Item;
import map.Door;
import map.Room;
import utility.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Person implements Entity {
    protected String name;
    protected boolean stunned;
    protected Room room;
    protected List<Item> items = new ArrayList<>();

    public Person(Room room){
        this.room = room;
    }

    public Person(String n){
        name=n;
    }

    public void setRoom(Room r){
        room=r;
    }

    /**
     * Alerts the Person about a cloth threat
     */
    public void clothStun() {}

    /**
     * Drops item in its current room
     * @param item
     */
    public void drop(Item item) {
        item.setOwner(null);
        item.changeRoom(room);
    }

    /**
     * Drops all its items in its current room
     */
    public void dropAll() {
        for (Item i : items)
            drop(i);
    }

    /**
     * An item offering protection against gas calls this function
     * @param protectionProvider
     * @param priority
     */
    public void gasProtection(Item protectionProvider, int priority) {

    }

    /**
     * Alerts the Person about gas threat
     */
    public void gasStun() {

    }


    /**
     * Tries to move through door into door's destination
     * @param door
     */
    public void move(Door door) {

    }


    /**
     * Picks up item from its room
     * @param item
     */
    public void pickup(Item item) {

    }

    /**
     * SlideRule calls this function whenever it's picked up
     * @param slideRule
     */
    public void slideRuleNotification(Item slideRule) {

    }


    /**
     * Alerts the Person about teacher threat
     */
    public void teacherAttack() {

    }


    /**
     * An item offering protection against teacher calls this function
     * @param protectionProvider
     * @param priority
     */
    public void teacherProtection(Item protectionProvider, int priority) {

    }

    /**
     * Teleports Person to roomTo
     * @param roomTo
     */
    public void teleport(Room roomTo) {

    }

    public void tick(){}

    public Room getRoom() {
        return room;
    }

    public abstract void offerProtection(Item i, int priority);
}
