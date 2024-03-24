package items;

import utility.Logger;

public class Mask extends Item {
    /**
     * Creates the mask, sets its lifetime to 3.
     */
    public Mask() {
        super();
        life = 3;
    }

    /**
     * Owner accepts Mask's protection offer
     */
    public void acceptProtection() {
        Logger.logCall("acceptProtection", "void");
        Logger.logReturn();
    }

    /**
     * Owner informs Mask about gas threat, Mask offers it protection
     */
    public void gasThreat() {
        Logger.logCall("gasThreat", "void");
        owner.gasProtection(this, life);
        life--;
        if (life == 0) {
            super.destroy();
        }
        Logger.logReturn();
    }

}
