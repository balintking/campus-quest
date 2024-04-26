package items;

import utility.Logger;

public class Beer extends Item {

    public Beer() {
        super();
        lifetime = 2;
    }

    /**
     * The item gets notified about a teacher threat, and it offers its protection againts it if it is active.
     */
    @Override
    public void teacherThreat() {
        Logger.logCall("teacherThreat", "void");
        if (isActive) {
            owner.teacherProtection(this, 0);
        }
        Logger.logReturn();
    }

    /**
     * When time passes, the lifetime of the item decreases, if it hits 0, it gets destroyed.
     */
    @Override
    public void tick() {
        Logger.logCall("tick", "void");
        if (isActive) {
            lifetime--;
        }
        if (lifetime == 0) {
            destroy();
        }
        Logger.logReturn();
    }

}

