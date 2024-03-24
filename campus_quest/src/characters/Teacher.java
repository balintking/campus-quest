package characters;

import items.Item;
import utility.Logger;

import java.util.List;

public class Teacher extends Person {

    /**
     * Gets stunned by cloth
     */
    public void clothStun(){
        Logger.logCall("clothStun", "void");
        stunned = true;
        Logger.logReturn();
    }

    /**
     * Gets stunned by gas
     */
    public void gasStun(){
        Logger.logCall("gasStun", "void");
        stunned = true;
        Logger.logReturn();
    }

    /**
     * The Teacher attacks all the people in their room
     */
    public void initAttack(){
        Logger.logCall("initAttack","void");
        List<Person> targets = room.getPeople();
        for(Person p:targets){
            p.teacherAttack();
        }
        Logger.logReturn();
    }

    /**
     * Pickes up item and destroys it
     * @param item
     */
    public void pickup(Item item){
        Logger.logCall("pickup", new Object[]{item}, "void");
        room.removeItem(item);
        item.destroy();
        Logger.logReturn();
    }

    //Teachers are immune to attacks by teacher, in this case nothing happens
    public void teacherAttack(){
        Logger.logCall("teacherAttack","void");
        Logger.logReturn();
    }

    @Override
    public void slideRuleNotification(Item slideRule) {
        Logger.logCall("slideRuleNotification",new Object[]{slideRule}, "void");
        this.drop(slideRule);
        Logger.logReturn();
    }

}
