package characters;

import items.Item;
import utility.Logger;

public class Teacher extends Person {

    //A rongy hatására megbénul 3 körig.
    public void clothStun(){}

    //A gáz hatására megbénul 2 körig.
    public void gasStun(){
        Logger.logCall("gasStun", "void");
        stunned = true;
        Logger.logReturn();
    };

    //Megtámadja a vele egy szobában tartózkodó személyeket.
    public void initAttack(){}

    //A felvett tárgyat megsemmisíti.
    public void pickup(Item item){}

    //Oktató támadására immunis, ez az implementáció semmit nem csinál. 
    public void teacherAttack(){}

    @Override
    public void offerProtection(Item i, int priority) {

    }



}
