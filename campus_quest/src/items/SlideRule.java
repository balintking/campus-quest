package items;

import characters.Person;
import utility.Logger;

public class SlideRule extends Item {

    /**
     * Called whenever picked up.
     * Notifies person that they picked the SlideRule up, so they can act accordingly.
     * @param person
     */
    public void setOwner(Person person) {
        Logger.logCall("setOwner", new Object[]{owner}, "void");
        this.owner = person;
        owner.slideRuleNotification(this);
        Logger.logReturn();
    }
}
