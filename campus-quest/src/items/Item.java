package items;

import characters.*;
import map.*;
import utility.Entity;

/**
 * Represents the abstract base class for items within the game.
 * This class stores an item's name and lifetime, and manages its active state.
 * It provides mechanisms to activate an item and to self-destroy when necessary.
 * <p>
 * This class is designed to be extended by other specific item types which
 * implement the specific behaviors of those items in the system.
 * </p>
 */
public abstract class Item implements Entity {

    /**
     * Who has the item? If it is in a room the value is null.
     */
    protected Person owner;

    /**
     * In which room the item is. If it is at a person the value is null.
     */
    protected Room room;

    /**
     * Remaining lifetime of the item, it can be used this much more times.
     */
    protected int life;

    /**
     * Tells that the item is active or not
     */
    protected boolean active;


    /**
     * Initializer constructor
     */
    protected Item(Person owner, Room room, int life, boolean active) {
        this.owner = owner;
        this.room = room;
        this.life = life;
        this.active = active;
    }


    /**
     * Activates the item
     */
    public void activate() {
        if (life > 0) {
            active = true;
        }
    }

    /**
     * Activates the item
     */
    public void deactivate() {
        active = false;
    }

    /**
     * Returns the items owner.
     * @return owner
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the item and handles the event of the item
     * when it is picked up and sets the room to null.
     */
    public void setOwner(Person owner) {
        this.owner = owner;
        if (owner != null) {
            this.room = null;
        }
    }

    /**
     * Returns the room where the item is.
     * @return room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room of the item when it is dropped or placed in a room.
     */
    public void setRoom(Room room) {
        this.room = room;
        if (room != null) {
            this.owner = null;
        }
    }

    /**
     * With this call, a Person signals a gas threat to all items they possess
     * if they are in a gas-filled room.
     * The implementation is empty by default, items with capability override this.
     */
    public void gasThreat() {}

    /**
     * With this call, a Person signals a gas threat to all items they possess
     * if they are in a gas-filled room.
     * The implementation is empty by default, items with capability override this.
     */
    public void teacherThreat() {}

    /**
     * A Person signals acceptance of the item's defense offer with this call.
     * Activates the item.
     */
    public void acceptProtection() {
        activate();
    }

    /**
     * Destroys the item
     */
    public void destroy() {
        life = 0;
        deactivate();
        if (owner != null) {
            owner.removeItem(this);
            owner = null;
        }
        if (room != null) {
            room.removeItem(this);
            room = null;
        }
    }


    public void tick() {
        if (active) {
            life--;
        }
        if (life <= 0) {
            destroy();
        }
    }
}
