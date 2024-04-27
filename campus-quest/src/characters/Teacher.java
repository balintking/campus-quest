package characters;

import items.Item;
import utility.Logger;
import map.Door;
import map.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public boolean pickup(Item item){
        Logger.logCall("pickup", new Object[]{item}, "void");
        room.removeItem(item);
        item.setOwner(this);
        item.destroy();
        Logger.logReturn(true);
        return true;
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

    /**
     * if stunned, the teacher doesn't attack or move and the stuntimer goes down,
     * else it attacks and then moves to a random neighbouring room with a chance of 1/3
     */
    public void tick(){
        if(stunned){
            stunTimer--;
        }
        if (stunTimer == 0) {
            stunned=false;
        }
        if(!stunned){
            initAttack();
            List<Door> reachableDoors=this.getRoom().getDoors();
            Random random1=new Random();
            Random random2=new Random();
            int y=random1.nextInt(3);
            if (y == 1) {
                int x=random2.nextInt(reachableDoors.size());
                this.move(reachableDoors.get(x-1));
            }
        }
    }

}
