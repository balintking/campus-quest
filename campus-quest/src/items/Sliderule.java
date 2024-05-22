package items;

import characters.Person;
import map.Room;

import java.io.Serializable;

/**
 * Represents the Slide rule artifact, the central objective in the game.
 */
public class Sliderule extends Item implements Serializable {

    /**
     * Indicates whether the item is a fake or a genuine version.
     */
    private final boolean fake;

    /**
     * Constructor for SlideRule. Sets life to 1 and active to false by default.
     */
    public Sliderule(Person owner, Room room, boolean fake) {
        super(owner, room, 1, false);
        this.fake = fake;
    }

    /**
     * Default constructor
     */
    public Sliderule() {
        fake = false;
    }

    /**
     * Sends a notification to its owner about the possession of the slide rule, if it is not fake.
     */
    @Override
    public void activate() {
        active = true;
        if (!fake) {
            owner.slideRuleNotification(this);
        }
    }

    /**
     * Whenever picked up, activates the item immediately.
     */
    @Override
    public void setOwner(Person owner) {
        this.owner = owner;
        if (owner != null) {
            this.room = null;
        }
        if(owner != null){
            activate();
        }
    }
}
