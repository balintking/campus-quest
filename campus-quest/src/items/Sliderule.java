package items;

import characters.Person;
import utility.Logger;

public class Sliderule extends Item {

    /**
     * Called whenever picked up.
     * Notifies person that they picked the SlideRule up, so they can act accordingly.
     * @param ownerInput
     */
    @Override
    public void setOwner(Person ownerInput) {
        Logger.logCall("setOwner", new Object[]{ownerInput}, "void");
        owner = ownerInput;
        if(owner != null){
            owner.slideRuleNotification(this);
        }
        Logger.logReturn();
    }
    @Override
    public void destroy(){

    }
}
