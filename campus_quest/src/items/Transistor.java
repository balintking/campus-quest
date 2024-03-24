package items;

import characters.Person;
import map.Room;
import utility.Logger;

public class Transistor extends Item {
    int id;
    Transistor pair;
    Person lastOwner;

    /**
     * Returns the pair's room if its active.
     *
     * @return Room
     */
    public Room getDestination() {
        Logger.logCall("getDestination", "Room");
        Room ret;
        if (!active) {
            ret = null;
        } else {
            ret = room;
        }
        Logger.logReturn();
        return ret;
    }

    /**
     * Returns the pair's room if its active.
     *
     * @param roomInput
     */
    @Override
    public void changeRoom(Room roomInput) {
        Logger.logCall("changeRoom", new Object[]{room}, "void");
        room = roomInput;
        Room pairRoom = pair.getDestination();
        if (pairRoom != null) {
            if (Logger.testerInput("Is the room not full?")) {
                lastOwner.teleport(pairRoom);
                lastOwner = null;
                deactivate();
                pair.deactivate();
            }
        }
        Logger.logReturn();
    }

    /**
     * Sets the pair of the transistor
     *
     * @param t
     */
    public void setPair(Transistor t) {
        pair = t;
    }

    /**
     * Activates the transistor and sets its last owner as the present owner.
     */
    @Override
    public void activate() {
        Logger.logCall("activate", "void");
        active = true;
        lastOwner = owner;
        Logger.logReturn();
    }

    /**
     * Deactivates the transistor.
     */
    public void deactivate() {
        Logger.logCall("deactivate", "void");
        active = false;
        Logger.logReturn();
    }
    /**
     * Destroys the transistor and resets its pair.
     */
    @Override
    public void destroy() {
        Logger.logCall("destroy", "void");
        Logger.logDestroy(this, "Transistor");
        owner = null;
        room = null;
        pair.reset();
        Logger.logReturn();
    }

    /**
     * Deactivates the transistor
     */
    public void reset() {
        Logger.logCall("reset", "void");
        deactivate();
        Logger.logReturn();
    }
}
