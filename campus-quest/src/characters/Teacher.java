package characters;

import items.Item;
import utility.Logger;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

    /**
     * Gets stunned by cloth.
     */
    public void clothStun(){
        Logger.logCall("clothStun", "void");
        stunned = true;
        Logger.logReturn();
    }

    /**
     * Gets stunned by gas.
     */
    public void gasStun(){
        Logger.logCall("gasStun", "void");
        stunned = true;
        Logger.logReturn();
    }

    /**
     * Attacks all the people in the room.
     */
    public void initAttack(){
        Logger.logCall("initAttack","void");
        List<Person> targets =new ArrayList<>(room.getPeople());
        for(Person p:targets){
            p.teacherAttack();
        }
        Logger.logReturn();
    }

    /**
     * Picks up item and destroys it.
     * @param item
     */
    public void pickup(Item item){
        Logger.logCall("pickup", new Object[]{item}, "void");
        room.removeItem(item);
        item.setOwner(this);
        item.destroy();
        Logger.logReturn();
    }

    /**
     * Teachers are immune to attacks by teacher, in this case nothing happens.
     */
    public void teacherAttack() {
        Logger.logCall("teacherAttack","void");
        Logger.logReturn();
    }

    /**
     * SlideRule notifies the Teacher about being picked up, so the Teacher can drop it.
     * @param slideRule
     */
    @Override
    public void slideRuleNotification(Item slideRule) {
        Logger.logCall("slideRuleNotification",new Object[]{slideRule}, "void");
        this.drop(slideRule);
        Logger.logReturn();
    }

}