package items;

import characters.Person;
import map.Room;

/**
 * Represents a TVSZ (Tanulmanyi Vizsga Szabalyzat) item that activates to protect a student
 * when attacked by a teacher, if the student does not have a higher-priority protection.
 */
public class TVSZ extends Item {

    /**
     * Indicates whether the item is a fake or a genuine version.
     */
    private final boolean fake;

    /**
     * Constructor for TVSZ. Sets life to 3 and active to false by default.
     */
    public TVSZ(Person owner, Room room, boolean fake) {
        super(owner, room, 3, false);
        this.fake = fake;
    }

    /**
     * The item gets notified about a teacher threat, and it offers its protection against it
     * with a priority equivalent to its lifetime if it is not fake.
     */
    @Override
    public void teacherThreat() {
        if (!fake && (owner != null)) {
            owner.teacherProtection(this, life);
        }
    }

    /**
     * The item deactivates itself after every use, because every protection for a round costs one life only.
     */
    @Override
    public void tick() {
        if (active) {
            life--;
            deactivate();
        }
        if (life <= 0) {
            destroy();
        }
    }
}
