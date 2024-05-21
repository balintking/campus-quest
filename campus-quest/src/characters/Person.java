package characters;

import items.Item;
import items.Transistor;
import map.Door;
import map.Room;
import utility.Entity;
import utility.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public abstract class Person implements Entity, Serializable {
    protected String name;
    protected boolean stunned;
    protected Room room;
    protected List<Item> items = new ArrayList<>();
    private int stunTimer;
    /**
     * The first activated transistor of the student from a pair
     */
    protected Transistor transistorToPair;

    protected boolean destroyed = false;

    public Person(){

    }

    /**
     * Sets Person's room to r
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
     */
    public void drop(Item item) {
        Logger.logCall("drop", new Object[]{item}, "void");
        items.remove(item);
        item.setRoom(room);
        room.addItem(item);
        item.setOwner(null);
        Logger.logReturn();
    }

    /**
     * Drops all its items in its current room
     */
    public void dropAll() {
        Logger.logCall("dropAll", "void");
        List<Item> tempItems = new ArrayList<>(items);
        for (Item i : tempItems){
            drop(i);
        }
        tempItems.clear();
        Logger.logReturn();
    }

    /**
     * drops a random item from the inventory
     */
    public void dropRandomItem(){}

    /**
     * An Item offering protection against gas calls this function
     * @param priority protectionProvider's protection's priority against other protecting items
     */
    public void gasProtection(Item protectionProvider, int priority) {}

    /**
     * Alerts the Person about gas threat
     */
    public void gasStun() {}


    /**
     * Tries to move through door into door's destination
     */
    public boolean move(Door door) {
        Logger.logCall("move",new Object[]{door},"void");
        Room r = door.getDest();
        if (stunned) {
            Logger.logReturn(false);
            return false;
        }
        if(r.addPerson(this)){
            this.getRoom().removePerson(this);
            this.setRoom(r);
            Logger.logReturn(true);
            return true;
        }
        Logger.logReturn(false);
        return false;
    }


    /**
     * Picks up item from its room
     */
    public abstract boolean pickup(Item item);


    /**
     * Adds item to the Person's list of Items
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
     */
    public void slideRuleNotification(Item slideRule) {
    }


    /**
     * Alerts the Person about Teacher threat
     */
    public abstract void teacherAttack();


    /**
     * An Item offering protection against Teacher calls this function
     */
    public void teacherProtection(Item protectionProvider, int priority) {}

    /**
     * Teleports Person to roomTo
     */
    public void teleport(Room roomTo) {
        Logger.logCall("teleport", new Object[]{roomTo}, "void");
        room.removePerson(this);
        roomTo.addPerson(this);
        Logger.logReturn();
    }

    @Override
    public void tick() {
    }

    @Override
    public boolean isDestroyed(){
        return destroyed;
    }

    /**
     * It returns the room which the person is located in.
     * @return room
     */
    public Room getRoom() {
        Logger.logCall("getRoom", "Room");
        Logger.logReturn(room.toString());
        return room;
    }

    public Transistor getTransistorToPair(){
        return transistorToPair;
    }

    public void setTransistorToPair(Transistor t){
        transistorToPair = t;
    }


    @Override
    public String toString() {
        return name;
    }

    public int getStunTimer() {
        return stunTimer;
    }

    public void setStunTimer(int stunTimer) {
        this.stunTimer = stunTimer;
    }

    public boolean isStunned(){ return stunned; }
}
