package characters;

import items.Item;
import map.Room;

import java.util.HashMap;

public class Student extends Person {

    private HashMap<Item, Integer> protectiveItems = new HashMap<Item, Integer>();

    public Student(Room room) {
        super(room);
    }

    public void initActivate(Item i) {
        i.activate();
    }

    @Override
    public void gasStun() {
        for (Item i : items) {
            i.gasThreat();
        }
        if (protectiveItems.isEmpty()) {
            stunned = true;
            dropAll();
        } else {
            //TODO: a legkisebb prioritású protectiveItem-en meg kell hívni az accepProtection()-t
        }
        protectiveItems.clear();
    }

    @Override
    public void offerProtection(Item i, int priority) {
        protectiveItems.put(i, priority);
    }
}
