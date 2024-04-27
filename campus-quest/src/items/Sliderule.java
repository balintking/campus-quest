package items;

import characters.Person;
import map.Room;

/**
 * Represents the Slide rule artifact, the central objective in the game.
 */
public class Sliderule extends Item {

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
     * This item cannot be activated
     */
    @Override
    public void activate() { /* This item cannot be activated */ }

    /**
     * Called whenever picked up.
     * Notifies person that they picked the SlideRule up, so they can act accordingly.
     */
    @Override
    public void setOwner(Person owner) {
        this.owner = owner;
        if(owner != null && !fake){
            owner.slideRuleNotification(this);
        }
    }

    /**
     * Time does not affect the state of this Item
     */
    @Override
    public void tick() { /* Time does not affect the state of this Item */ }
}
