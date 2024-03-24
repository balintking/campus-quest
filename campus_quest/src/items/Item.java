package items;

import characters.*;
import map.*;
import utility.Entity;
import utility.Logger;

public abstract class Item implements Entity {

    Person owner; //: Kinél van az adott tárgy. Ha szobában van, null.
    Room room; //: Melyik szobában van az adott tárgy. Ha személynél van, null.
    String name; //: A tárgy neve
    int life; //: Hátralévő élettartam, ennyiszer használható még a tárgy.
    boolean active; //: Jelzi, hogy aktív-e az item.
    
    //A Characters.Person ezzel a hívással jelez ha elfogadja a tárgyvédelmi ajánlatát.
    public void acceptProtection(){
        Logger.logCall("acceptProtection","void");
        Logger.logReturn();
    }

    /**
     * Activates the item
     */
    public void activate() {
        Logger.logCall("activate","void");
        active = true;
        Logger.logReturn();
    }

    // Beállítja a tárgy szobáját.
    public void changeRoom(Room room){
        Logger.logCall("changeRoom", new Object[]{room}, "void");
        this.room = room;
        Logger.logReturn();
    }

    public Room getRoom(){
        Logger.logCall("getRoom","Room");
        Logger.logReturn(room);
        return room;
    }

    /**
     * Destroys the item
     */
    public void destroy(){
        Logger.logCall("destroy", "void");
        Logger.logDestroy(this,"Item");
        owner = null;
        room = null;
        Logger.logReturn();
    }

    //A Characters.Person ezzel a hívással jelez az összes általa birtokolt Items.Item felé ha gázos szobában tartózkodik.
    public void gasThreat(){
        Logger.logCall("gasThreat", "void");
        Logger.logReturn();
    }

    //Beállítja a tárgy tulajdonosát és kezeli azt az eseményt, hogy a tárgyat felvették, utóbbi néhány implementációban lényeges.
    public void setOwner(Person owner){
        Logger.logCall("setOwner",new Object[]{owner},"void");
        this.owner = owner;
        Logger.logReturn();
    }

    //A Characters.Person ezzel a hívással jelez az összes általa birtokolt Items.Item felé ha egy oktató megtámadta őt.
    public void teacherThreat(){}

    public void tick(){}
}
