package items;

import utility.Logger;
import characters.Person;

import java.util.List;

public class Cloth extends Item {

    /**
     * On every Person in the room this method calls the clothStun() method, then the Cloth destroys itself.
     */
    public void activate() {
        Logger.logCall("activate", "void");
        room = owner.getRoom();
        List<Person> targets = room.getPeople();
        for (Person p : targets) {
            p.clothStun();
        }
        this.destroy();
        Logger.logReturn();
    }

}
