package items;

import characters.Person;
import map.Room;

import java.io.Serializable;

/**
 * Represents a Mask item that protects its owner when in a gassed room.
 */
public class Mask extends Item implements Serializable {

    /**
     * Indicates whether the item is a fake or a genuine version.
     */
    private final boolean fake;

    /**
     * Constructor for Mask. Sets life to 3 and active to false by default.
     */
    public Mask(Person owner, Room room, boolean fake) {
        super(owner, room, 3, false);
        this.fake = fake;
    }

    /**
     * Default constructor
     */
    public Mask() {
        fake = false; lifetime = 3;
    }

    /**
     * The item gets notified about gas threat, and it offers its protection against it
     * with a priority equivalent to its lifetime if the item is not fake.
     */
    @Override
    public void gasThreat() {
        if (!fake && (owner != null)) {
            owner.gasProtection(this, lifetime);
        }
    }
    @Override
    public void acceptProtection() {
        activate();
        lifetime--;
    }

    /**
     * The item deactivates itself after every use, because every protection for a round costs one life only.
     */
    @Override
    public void tick() {
        if (active) {
            deactivate();
        }
        if (lifetime <= 0) {
            destroy();
        }
    }
}
