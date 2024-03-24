package items;

import utility.Logger;

public class Mask extends Item {

    /**
     * Owner accepts Mask's protection offer
     */
    public void acceptProtection(){
        Logger.logCall("acceptProtection", "void");
        Logger.logReturn();
    }

    /**
     * Owner informs Mask about gas threat, Mask offers it protection
     */
    public void gasThreat(){
        Logger.logCall("gasThreat", "void");
        owner.offerProtection(this, 0);
        Logger.logReturn();
    }

}
