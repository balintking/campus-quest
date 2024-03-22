package items;

import characters.Person;
import map.Room;

public class Camembert extends Item{


    /**
     * Calls gasStun() on all Person objects in its owner's room
     */
    public void activate(){
        Room r = owner.getRoom();
        for (Person p : r.getPeople()){
            p.gasStun();
        }
        super.destroy();
    }
}
