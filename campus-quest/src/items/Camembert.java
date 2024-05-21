package items;

import characters.Person;
import map.Room;

import java.io.Serializable;

/**
 * Represents a Camembert item that, when activated, gasses the room it is located in.
 * It signals to the room that it has been gassed.
 */
public class Camembert extends Item implements Serializable {

    /**
     * Constructor for Camembert. Sets life to 1 and active to false by default.
     */
    public Camembert(Person owner, Room room) {
        super(owner, room, 1, false);
    }

    /**
     * Default constructor
     */
    public Camembert() {
        lifetime = 1;
    }

    /**
     * Sets the state of the room it's located in to gassed, and destroys itself.
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
                currentRoom.gas();
            }

            destroy();
        }
    }
}
