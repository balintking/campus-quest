package items;

import map.Room;
import characters.Person;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a Cloth item that, when active, attempts to stun everyone in the room,
 * but only affects teachers. The item self-destructs after use.
 */
public class Cloth extends Item implements Serializable {

    /**
     * Constructor for Cloth. Sets life to 1 and active to false by default.
     */
    public Cloth(Person owner, Room room) {
        super(owner, room, 1, false);
    }

    /**
     * Default constructor
     */
    public Cloth() {
    }

    /**
     * On every Person in the room this method calls the clothStun method, then the Cloth destroys itself.
     */
    @Override
    public void activate() {
        active = true;

        Room currentRoom = null;
        if (owner != null) {
            currentRoom = owner.getRoom();
        } else if (room != null) {
            currentRoom = room;
        }

        if (currentRoom != null) {
            List<Person> targets = currentRoom.getPeople();
            for (Person p : targets) {
                p.clothStun();
            }
        }

        this.destroy();
    }

}
