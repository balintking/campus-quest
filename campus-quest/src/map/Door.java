package map;

import utility.Entity;
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

    /**
     * Destroys the door.
     */
    void destroy() {
        source.removeDoor(this);
        destination.removeDoor(this);
        destroyed = true;
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
        return source;
    }

    /**
     * Setter for the sourceRoom
     */
    public void setSrc(Room source) {
        this.source = source;
    }
    /**
     * Gets destination of the door.
     *
     * @return destination
     */
    public Room getDest() {
        return destination;
    }
    /**
     * Sets the destination of the door.
     */
    public void setDest(Room destination) {
        this.destination = destination;
    }

}
