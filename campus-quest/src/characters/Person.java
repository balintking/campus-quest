package characters;

import items.Item;
import items.Transistor;
import map.Door;
import map.Room;
import utility.Entity;
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
        room = r;
    }

    /**
     * Alerts the Person about a cloth threat
     */
    public void clothStun() {}

    /**
     * Drops item in its current room
     */
    public void drop(Item item) {
        items.remove(item);
        item.setRoom(room);
        room.addItem(item);
        item.setOwner(null);
    }

    /**
     * Drops all its items in its current room
     */
    public void dropAll() {
        List<Item> tempItems = new ArrayList<>(items);
        for (Item i : tempItems){
            drop(i);
        }
        tempItems.clear();
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
        Room r = door.getDest();
        if (stunned) {
            return false;
        }
        if(r.addPerson(this)){
            this.getRoom().removePerson(this);
            this.setRoom(r);
            return true;
        }
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
        items.add(item);
    }

    public void removeItem(Item item){
        items.remove(item);
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
        room.removePerson(this);
        this.setRoom(roomTo);
        roomTo.addPerson(this);
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
