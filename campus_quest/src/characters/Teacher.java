package characters;

import items.Item;
import map.Room;

public class Teacher extends Person {

    public Teacher(Room room) {
        super(room);
    }

    public Teacher(String name) {
        super(name);
    }

    //A rongy hatására megbénul 3 körig.
    public void clothStun(){}

    //A gáz hatására megbénul 2 körig.
    public void gasStun(){
        stunned = true;
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
