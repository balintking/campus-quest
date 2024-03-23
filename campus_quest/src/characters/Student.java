package characters;

import items.Item;
import map.Room;
import utility.Logger;

import java.util.HashMap;

public class Student extends Person {

    private HashMap<Item, Integer> protectiveItems = new HashMap<>();

    // just to avoid copying code
    private void selectProtectionProvider() {
        // here we select the protective item with the lowest prioirity and we accept its offer
        int min = Integer.MAX_VALUE;
        Item minItem = null;

        for(Item i : protectiveItems.keySet()) {
            int val = protectiveItems.get(i);
            if(val < min) {
                min = val;
                minItem = i;
            }
        }
        if(minItem != null) {
            minItem.acceptProtection();
        } else {
            throw new NullPointerException("The protectiveItems list contains a null reference!");
        }
    }

    public void initActivate(Item i) {
        Logger.logCall("initActivate", new String[]{i.toString()},"void");
        i.activate();

        Logger.logReturn();
    }

    /**
     * An event handler for gas stun. It checks if there are protective items available and if yes
     * it selects the one with the lowest(min check) priority. If there is no protection then it suffers gas attack.
     * @throws NullPointerException If the protectiveItems list has a null reference inside by mistake.
     */
    @Override
    public void gasStun() {
        Logger.logCall("gasStun", "void");
        for (Item i : items) {
            i.gasThreat();
        }
        if (protectiveItems.isEmpty()) {
            stunned = true;
            dropAll();
        } else {
            selectProtectionProvider();
        }
        protectiveItems.clear();
        Logger.logReturn();
    }

    @Override
    public void teacherAttack() {
        Logger.logCall("teacherAttack", "void");
        for (Item i : items) {
            i.teacherThreat();
        }
        if (protectiveItems.isEmpty()) {
            // the student dies
            room.removePerson(this);
        } else {
            selectProtectionProvider();
        }
        protectiveItems.clear();
        Logger.logReturn();
    }

    @Override
    public void offerProtection(Item i, int priority) {
        Logger.logCall("offerProtection", new String[]{i.toString(), Integer.toString(priority)}, "void");
        protectiveItems.put(i, priority);
        Logger.logReturn();
    }
}
