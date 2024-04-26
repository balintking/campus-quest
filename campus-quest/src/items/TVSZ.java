package items;

import utility.Logger;

public class TVSZ extends Item {
    /**
     *
     */
    public TVSZ() {
        super();
        lifetime = 3;
    }

    /**
     * The item gets notified about a teacher threat, and it offers its protection against it
     */
    @Override
    public void teacherThreat() {
        Logger.logCall("teacherThreat", "void");
        owner.teacherProtection(this, lifetime);
        Logger.logReturn();
    }

    /**
     * The owner accepted the protection offer, so the life decreases by 1, if the life becomes 0, the item gets destroyed
     */
    @Override
    public void acceptProtection() {
        Logger.logCall("acceptProtection", "void");
        lifetime--;
        if (lifetime == 0) {
            destroy();
        }
        Logger.logReturn();
    }

}
