package characters;

import items.Item;
import map.Door;
import map.Room;
import utility.Entity;
import utility.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class Person implements Entity {
    protected String name;
    protected boolean stunned;
    protected Room room;
    protected List<Item> items = new ArrayList<>();


    /**
     * Sets Person's room to r
     * @param r
     */
    public void setRoom(Room r) {
        Logger.logCall("setRoom",new Object[]{r},"void");
        room = r;
        Logger.logReturn();
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
        Logger.logCall("drop", new Object[]{item}, "void");
        items.remove(item);
        item.setRoom(room);
        item.setOwner(null);
        Logger.logReturn();
    }

    /**
     * Drops all its items in its current room
     */
    public void dropAll() {
        Logger.logCall("dropAll", "void");
        List<Item> tempItems = new ArrayList<>();
        for (Item i : items){
            tempItems.add(i);
        }
        for (Item i : tempItems){
            drop(i);
        }
        tempItems.clear();
        Logger.logReturn();
    }

    /**
     * An Item offering protection against gas calls this function
     * @param protectionProvider
     * @param priority protectionProvider's protection's priority against other protecting items
     */
    public void gasProtection(Item protectionProvider, int priority) {}

    /**
     * Alerts the Person about gas threat
     */
    public void gasStun() {}


    /**
     * Tries to move through door into door's destination
     * @param door
     */
    public void move(Door door) {
        room = door.getDest();
    }


    /**
     * Picks up item from its room
     * @param item
     */
    public abstract void pickup(Item item);


    /**
     * Adds item to the Person's list of Items
     * @param item
     */
    public void addItem(Item item) {
        Logger.logCall("addItem",new Object[]{item},"void");
        items.add(item);
        Logger.logReturn();
    }

    public void removeItem(Item item){
        Logger.logCall("removeItem",new Object[]{item},"void");
        items.remove(item);
        Logger.logReturn();
    }

    /**
     * SlideRule calls this function whenever it's picked up
     * @param slideRule
     */
    public void slideRuleNotification(Item slideRule) {
    }


    /**
     * Alerts the Person about Teacher threat
     */
    public abstract void teacherAttack();


    /**
     * An Item offering protection against Teacher calls this function
     *
     * @param protectionProvider
     * @param priority
     */
    public void teacherProtection(Item protectionProvider, int priority) {}

    /**
     * Teleports Person to roomTo
     *
     * @param roomTo
     */
    public void teleport(Room roomTo) {
        Logger.logCall("teleport", new Object[]{roomTo}, "void");
        room.removePerson(this);
        roomTo.addPerson(this);
        Logger.logReturn();
    }

    public void tick() {}

    /**
     * It returns the room which the person is located in.
     * @return room
     */
    public Room getRoom() {
        Logger.logCall("getRoom", "Room");
        Logger.logReturn(room.toString());
        return room;
    }


    @Override
    public String toString() {
        return name;
    }
}
