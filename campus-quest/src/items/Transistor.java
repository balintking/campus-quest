package items;

import characters.Person;
import map.Room;

import java.io.Serializable;

enum TransistorState implements Serializable {
    UNPAIRED,
    PAIRING,
    PAIRED
}

/**
 * Represents a Transistor, a teleportation device within the game.
 * When paired with another transistor, it allows a student to
 * instantly teleport between the two connected locations.
 */
 public class Transistor extends Item implements Serializable {

    /**
     * The actual state of the transistor.
     */
    private TransistorState state;

    /**
     * Pair of transistor if it has been paired.
     */
    private Transistor pair;

    /**
     * Initializer constructor. Sets the transistor to default unpaired state.
     */
    public Transistor(Person owner, Room room) {
        super(owner, room, 1, false);
        this.state = TransistorState.UNPAIRED;
        this.pair = null;
    }

    /**
     * Default constructor
     */
    public Transistor() {
    }

    /**
     * Activates the transistor. Prepares to pair or pairs the transistor with owner's
     * transistorToPair depending on its state.
     */
    @Override
    public void activate() {
        if (!active && owner != null) {
            Transistor transistorToPair = owner.getTransistorToPair();
            if (transistorToPair != null) {
                pair = transistorToPair;
                state = TransistorState.PAIRED;
                transistorToPair.pair = this;
                transistorToPair.state = TransistorState.PAIRED;
                owner.setTransistorToPair(null);
            } else {
                owner.setTransistorToPair(this);
                state = TransistorState.PAIRING;
            }
            active = true;
        }
    }

    /**
     * Deactivates the transistor and its pair. Resets pairs and states according to the previous state.
     */
    public void deactivate() {
        if (active) {
            active = false;
            if (state == TransistorState.PAIRING && owner != null) {
                owner.setTransistorToPair(null);
            } else if (state == TransistorState.PAIRED) {
                Transistor other = pair;
                pair = null;
                if (other.active) {
                    other.deactivate();
                }
            }
            state = TransistorState.UNPAIRED;
        }
    }

    /**
     * Handles the dropping of this item, taking into account its current state.
     * Dropping item while it was pairing resets it,
     * dropping item and the pair has been placed before: owner teleports.
     */
    @Override
    public void setRoom(Room room) {
        this.room = room;
        
        if (room != null && owner != null) {
            //dropping item while it was pairing resets it
            if (state == TransistorState.PAIRING) {
                deactivate();
            }
            //dropping item and the pair has been placed before
            else if (state == TransistorState.PAIRED && (pair.room != null)) {
                    owner.teleport(pair.room);
                    deactivate();
            }
            //dropping the item as the first one of the pair doesn't require any special steps
            this.owner = null;
        }
    }

    /**
     * Time does not affect this item
     */
    @Override
    public void tick() { /* Time does not affect this item */ }
}
