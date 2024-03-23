package items;

import utility.Logger;

public class TVSZ extends Item {

    public TVSZ() {
        super();
        life = 3;
    }
    
    //: Értesül az oktató támadásáról és védelmet kínál tulajdonosának.
    @Override
    public void teacherThreat(){
        Logger.logCall("teacherThreat","void");
        owner.teacherProtection(this,life);
        Logger.logReturn();
    }

    //: A Characters.Person elfogadja a védelmi ajánlatot. Az élettartam eggyel csökken, ha eléri a 0-t megsemmisül.
    @Override
    public void acceptProtection(){
        Logger.logCall("acceptProtection","void");
        life--;
        if(life == 0) {
            destroy();
        }
        Logger.logReturn();
    }

}
