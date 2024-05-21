package items;

import characters.Person;
import map.Room;

import java.io.Serializable;

/**
 * Represents an Air Freshener item that can neutralize the effects of gas when placed in a gassed room.
 */
public class AirFreshener extends Item implements Serializable {

    /**
     * Initializer constructor.
     */
    public AirFreshener(Person owner, Room room) {
        super(owner, room, 1, false);
    }

    /**
     * Default constructor
     */
    public AirFreshener() {

    }

    /**
     * Ungasses the room it is located in, and destroys itself.
     */
    @Override
    public void activate() {
        if (lifetime > 0) {
            active = true;

            Room currentRoom = null;
            if (owner != null) {
                currentRoom = owner.getRoom();
            } else if (room != null) {
                currentRoom = room;
            }

            if (currentRoom != null) {
                currentRoom.unGas();
            }

            destroy();
        }
    }

    /**
     * Sets the room of the item and if it is gassed, activates itself immediately.
     */
    @Override
    public void setRoom(Room room) {
        this.room = room;
        if (room != null) {
            this.owner = null;
            activate();
        }
    }
}
