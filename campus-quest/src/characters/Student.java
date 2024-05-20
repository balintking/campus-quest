package characters;

import items.Item;
import utility.Entity;
import utility.Logger;

import java.util.HashMap;
import java.util.Random;

public class Student extends Person implements Entity {
    /**
     * Offered protective items are stored here when protection is needed.
     */
    private final HashMap<Item, Integer> protectiveItems = new HashMap<>();

    private boolean won = false;

    /**
     * Constructor calls Person's constructor
     */
    public Student() {
        super();
    }

    /**
     * Constructor with name given
     */
    public Student(String name){
        super();
        this.name = name;
    }

    /**
     * Overrides the pickup method of the base class. Before picking up an item it checks if the inventory is full.
     *
     * @param item Item to pick up
     */
    public boolean pickup(Item item) {
        Logger.logCall("pickup", new Object[]{item}, "void");
        if (this.items.size() < 5) {
            items.add(item);
            item.setOwner(this);
            room.removeItem(item);
            Logger.logReturn(true);
            return true;
        }
        Logger.logReturn(false);
        return false;
    }


    /**
     * drops a random item from the inventory
     */
    public void dropRandomItem() {
        Random random = new Random();
        int x = random.nextInt(items.size());
        drop(items.get(x));
    }

    /**
     * Here we select the protective item with the lowest priority and accept its offer.
     */
    private void selectProtectionProvider() {
        int min = Integer.MAX_VALUE;
        Item minItem = null;

        for (Item i : protectiveItems.keySet()) {
            int val = protectiveItems.get(i);
            if (val < min) {
                min = val;
                minItem = i;
            }
        }
        if (minItem != null) {
            minItem.acceptProtection();
        } else {
            throw new NullPointerException("The protectiveItems list contains a null reference!");
        }
    }


    /**
     * Activates the given item
     */
    public void initActivate(Item i) {
        Logger.logCall("initActivate", new Object[]{i}, "void");
        i.activate();
        Logger.logReturn();
    }

    /**
     * when clothStun is called on a Student, nothing happens
     */
    @Override
    public void clothStun() {
        Logger.logCall("clothStun", "void");
        Logger.logReturn();
    }

    /**
     * An event handler for gas stun. It checks if there are protective items available and if yes
     * it selects the one with the lowest(min check) priority. If there is no protection then it suffers gas attack.
     *
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


    /**
     * Alerts the Student about Teacher threat
     */
    @Override
    public void teacherAttack() {
        Logger.logCall("teacherAttack", "void");
        for (Item i : items) {
            i.teacherThreat();
        }
        if (protectiveItems.isEmpty()) {
            // the student dies
            room.removePerson(this);
            destroyed = true;
            Logger.logDestroy(this, "Student");
        } else {
            selectProtectionProvider();
        }
        protectiveItems.clear();
        Logger.logReturn();
    }

    /**
     * An Item offering protection against Teacher calls this function
     */
    @Override
    public void teacherProtection(Item protectionProvider, int priority) {
        Logger.logCall("teacherProtection", new Object[]{protectionProvider, priority}, "void");
        protectiveItems.put(protectionProvider, priority);
        Logger.logReturn();
    }

    /**
     * An Item offering protection against gas calls this function
     *
     * @param priority protectionProvider's protection's priority against other protecting items
     */
    @Override
    public void gasProtection(Item protectionProvider, int priority) {
        Logger.logCall("gasProtection", new Object[]{protectionProvider, priority}, "void");
        protectiveItems.put(protectionProvider, priority);
        Logger.logReturn();
    }


    /**
     * SlideRule notifies the Student about being picked up, the Students win the game
     */
    @Override
    public void slideRuleNotification(Item slideRule) {
        Logger.logCall("slideRuleNotification", new Object[]{slideRule}, "void");
        Logger.logCall("win", "void");
        won = true;
        Logger.logReturn();
        Logger.logReturn();
    }


    @Override
    public void tick() {
        if (stunned) {
            setStunTimer(getStunTimer() - 1);
        }
        if (getStunTimer() == 0) {
            stunned = false;
        }
        //todo:kapott parameter szerinti mozgas
    }

    public boolean didWin(){
        return won;
    }
}
