package map;

import utility.Entity;
import utility.Logger;
import java.util.Random;

public class Door implements Entity {
    /**
     * Neighbouring room, opens from here.
     */
    Room source;
    /**
     * Neighbouring room, opens to here.
     */
    Room destination;

    private boolean destroyed = false;

    /**
     *Is the given door usable? (Has it disappeared?)
     */
    private boolean hidden;

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
        source.removeDoor(this);
        destination.removeDoor(this);
        destroyed = true;
        Logger.logReturn();
    }

    /**
     * If called this function makes the door disappear with a 10% chance
     */
    public void tick() {
        Random rand = new Random();
        int value = rand.nextInt(10);
        if(value == 1){
            hidden = !hidden;
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
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
     */
    public void setDest(Room destination) {
        Logger.logCall("setDest", new Object[]{destination}, "void");
        Logger.logReturn();
        this.destination = destination;
    }

}
