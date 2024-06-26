package items;

import characters.Person;
import map.Room;

import java.io.Serializable;

/**
 * Represents a Beer item that, when activated by a student,
 * provides protection against instructors for three rounds.
 */
public class Beer extends Item implements Serializable {

    /**
     * Constructor for Beer. Sets life to 3 and active to false by default.
     */
    public Beer(Person owner, Room room) {
        super(owner, room, 3, false);
    }

    /**
     * Default constructor
     */
    public Beer() {
        lifetime = 3;
    }

    /**
     * Activates the item and calls dropRandomItem on owner.
     */
    @Override
    public void activate() {
        if (lifetime > 0) {
            active = true;
            if (owner != null) {
                owner.dropRandomItem();
            }
        }
    }

    /**
     * The item gets notified about a teacher threat, and it offers its protection against it
     * with 0 priority if it is active.
     */
    @Override
    public void teacherThreat() {
        if (active && (owner != null)) {
            owner.teacherProtection(this, 0);
        }
    }
}

