package map;

import utility.Entity;
import utility.Logger;

public class Door implements Entity {
    /**
     * Neighbouring room, opens from here.
     */
    Room source;
    /**
     * Neighbouring room, opens to here.
     */
    Room destination;

    /**
     *Is the given door usable? (Has it disappeared?)
     */
    boolean hidden;

    public Door() {
    }

    public Door(Room src, Room dst) {
        source = src;
        destination = dst;
    }

    /**
     * Destroys the door.
     */
    void destroy() {
        Logger.logCall("destroy", "void");
        Logger.logDestroy(this, "Door");
        Logger.logReturn();
    }

    public void tick() {
    }

    /**
     *Getter for the sourceRoom
     *
     * @return source
     */
    public Room getSrc() {
        Logger.logCall("getSrc", "Room");
        Logger.logReturn(source);
        return source;
    }

    /**
     * Setter for the sourceRoom
     *
     * @param source
     */
    public void setSrc(Room source) {
        Logger.logCall("setSrc", new Object[]{source}, "void");
        Logger.logReturn();
        this.source = source;
    }
    /**
     * Gets destination of the door.
     *
     * @return destination
     */
    public Room getDest() {
        Logger.logCall("getDest", "Room");
        Logger.logReturn(destination);
        return destination;
    }
    /**
     * Sets the destination of the door.
     *
     * @param destination
     */
    public void setDest(Room destination) {
        Logger.logCall("setDest", new Object[]{destination}, "void");
        Logger.logReturn();
        this.destination = destination;
    }
}
