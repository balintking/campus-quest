package items;

import utility.Logger;

public class Beer extends Item{

    public Beer() {
        super();
        life = 2;
    }

    @Override
    public void teacherThreat() {
        Logger.logCall("teacherThreat","void");
        if(active) {
            owner.teacherProtection(this, 0);
        }
        Logger.logReturn();
    }

    @Override
    public void tick() {
        Logger.logCall("tick","void");
        if(active) {
            life--;
        }
        if(life == 0) {
            destroy();
        }
        Logger.logReturn();
    }

}

