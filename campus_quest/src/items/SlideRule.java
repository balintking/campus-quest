package items;

import characters.Person;
import utility.Logger;

public class SlideRule extends Item{

    //Ha egy Characters.Person felveszi, akkor slideRuleNotification-t küld a számára.
    public void setOwner(Person person){
        Logger.logCall("setOwner",new Object[]{owner},"void");
        this.owner = person;
        owner.slideRuleNotification(this);
        Logger.logReturn();
    }
}
