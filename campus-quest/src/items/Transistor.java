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
        if (!isActive) {
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
    public void setRoom(Room roomInput) {
        Logger.logCall("setRoom", new Object[]{roomInput}, "void");
        if(owner == null){
            room = roomInput;
        } else{
            room = roomInput;
            Room pairRoom = pair.getDestination();
            if (pairRoom != null) {
                if (!Logger.testerInput("Is the room full?")) {
                    lastOwner.teleport(pairRoom);
                    lastOwner = null;
                    deactivate();
                    pair.deactivate();
                }
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
        Logger.logCall("setPair",new Object[]{t},"void");
        pair = t;
        Logger.logReturn();
    }

    /**
     * Activates the transistor and sets its last owner as the present owner.
     */
    @Override
    public void activate() {
        Logger.logCall("activate", "void");
        isActive = true;
        lastOwner = owner;
        Logger.logReturn();
    }

    /**
     * Deactivates the transistor.
     */
    public void deactivate() {
        Logger.logCall("deactivate", "void");
        isActive = false;
        Logger.logReturn();
    }
    /**
     * Destroys the transistor and resets its pair.
     */
    @Override
    public void destroy() {
        Logger.logCall("destroy", "void");
        owner.removeItem(this);
        owner = null;
        room = null;
        pair.reset();
        Logger.logDestroy(this, "Transistor");
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
