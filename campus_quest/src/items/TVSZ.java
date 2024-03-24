package items;

import utility.Logger;

public class TVSZ extends Item {
    /**
     *
     */
    public TVSZ() {
        super();
        life = 3;
    }

    /**
     * The item gets notified about a teacher threat, and it offers its protection againts it
     */
    @Override
    public void teacherThreat() {
        Logger.logCall("teacherThreat", "void");
        owner.teacherProtection(this, life);
        Logger.logReturn();
    }

    /**
     * The owner accepted the protection offer, so the life decreases by 1, if the life becomes 0, the item gets destroyed
     */
    @Override
    public void acceptProtection() {
        Logger.logCall("acceptProtection", "void");
        life--;
        if (life == 0) {
            destroy();
        }
        Logger.logReturn();
    }

}
