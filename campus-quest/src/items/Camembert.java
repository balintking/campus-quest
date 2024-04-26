package items;

import characters.Person;
import map.Room;
import utility.Logger;

public class Camembert extends Item {


    /**
     * Calls gasStun() on all Person objects in its owner's room.
     */
    public void activate() {
        Logger.logCall("activate", "void");
        Room r = owner.getRoom();
        r.gas();
        destroy();
        Logger.logReturn();
    }
}
