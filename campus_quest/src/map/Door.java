package map;

import utility.Entity;
import utility.Logger;

public class Door implements Entity {
    Room source;//: Szomszédos szoba, innen nyílik.
    Room destination;//: Szomszédos szoba, ide vezet.
    boolean hidden;//: Használható-e az adott ajtó. (eltűnt-e)

    public Door() {}
    public Door(Room src, Room dst){
        source = src;
        destination = dst;
    }

    //: Megsemmisíti az ajtót.
    void destroy(){
        Logger.logDestroy(this,"Door");
    }

    public void tick(){}

    public Room getSrc() {
        Logger.logCall("getSrc","Room");
        Logger.logReturn(source);
        return source;
    }

    public void setSrc(Room source) {
        Logger.logCall("setSrc",new Object[]{source},"void");
        Logger.logReturn();
        this.source = source;
    }

    public Room getDest() {
        Logger.logCall("getDest","Room");
        Logger.logReturn(destination);
        return destination;
    }

    public void setDest(Room destination) {
        Logger.logCall("setDest",new Object[]{destination},"void");
        Logger.logReturn();
        this.destination = destination;
    }
}
