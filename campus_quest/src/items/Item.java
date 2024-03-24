package items;

import characters.*;
import map.*;
import utility.Entity;
import utility.Logger;

public abstract class Item implements Entity {

    /**
     * Who has the item. if it is in a room the value is null.
     */
    Person owner;
    /**
     * In which room the item is. if it is at a person the value is null.
     */
    Room room;
    /**
     * Remaining lifetime of the item, it can be used this much more times.
     */
    int life;
    /**
     * shows that the item is active or not
     */
    boolean active;


    /**
     * A Person signals acceptance of the item's defense offer with this call.
     */
    public void acceptProtection() {
        Logger.logCall("acceptProtection", "void");
        Logger.logReturn();
    }

    /**
     * Activates the item
     */
    public void activate() {
        Logger.logCall("activate", "void");
        active = true;
        Logger.logReturn();
    }

    /**
     * Sets the room
     *
     * @param room
     */
    public void setRoom(Room room) {
        Logger.logCall("changeRoom", new Object[]{room}, "void");
        this.room = room;
        Logger.logReturn();
    }

    public void changeRoom(Room roomInput) {
    }

    /**
     * Returns the room where the item is.
     *
     * @return room
     */
    public Room getRoom() {
        Logger.logCall("getRoom", "Room");
        Logger.logReturn(room);
        return room;
    }

    /**
     * Destroys the item
     */
    public void destroy() {
        Logger.logCall("destroy", "void");
        Logger.logDestroy(this, "Item");
        owner = null;
        room = null;
        Logger.logReturn();
    }

    /**
     * With this call, a Person signals a gas threat to all items they possess if they are in a gas-filled room.
     */
    public void gasThreat() {
        Logger.logCall("gasThreat", "void");
        Logger.logReturn();
    }


    /**
     * Sets the owner of the item and handles the event of the item
     * being picked up, which is significant in some implementations.
     *
     * @param owner
     */
    public void setOwner(Person owner) {
        Logger.logCall("setOwner", new Object[]{owner}, "void");
        this.owner = owner;
        Logger.logReturn();
    }

    /**
     * The item gets notified about a teacher threat, and it offers its protection againts it.
     */
    public void teacherThreat() {
    }


    public void tick() {
    }
}
