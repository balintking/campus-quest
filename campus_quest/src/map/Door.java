package map;

import utility.Entity;

public class Door implements Entity {
    Room source;//: Szomszédos szoba, innen nyílik.
    Room destination;//: Szomszédos szoba, ide vezet.
    boolean hidden;//: Használható-e az adott ajtó. (eltűnt-e)

    //: Megsemmisíti az ajtót.
    void destroy(){};

    public void tick(){}
}
