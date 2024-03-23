package characters;

import items.Item;
import utility.Logger;

import java.util.List;

public class Teacher extends Person {

    //Gets stun by cloth for 3 rounds
    public void clothStun(){
        Logger.logCall("clothStun", "void");
        stunned = true;
        Logger.logReturn();
    }

    //Gets stun by gas for 2 rounds
    public void gasStun(){
        Logger.logCall("gasStun", "void");
        stunned = true;
        Logger.logReturn();
    };

    //The Teacher attacks all the people in their room
    public void initAttack(){
        Logger.logCall("initAttack","void");
        List<Person> targets = room.getPeople();
        for(Person p:targets){
            p.teacherAttack();
        }
        Logger.logReturn();
    }

    //The teacher destroys the picked up item
    public void pickup(Item item){}

    //Teachers are immune to attacks by teacher, in this case nothing happens
    public void teacherAttack(){
        Logger.logCall("teacherAttack","void");
        Logger.logReturn();
    }

    @Override
    public void offerProtection(Item i, int priority) {

    }



}
