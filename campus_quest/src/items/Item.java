package items;

import characters.*;
import map.*;
import utility.Entity;

public abstract class Item implements Entity {

    Person owner; //: Kinél van az adott tárgy. Ha szobában van, null.
    Room room; //: Melyik szobában van az adott tárgy. Ha személynél van, null.
    String name; //: A tárgy neve
    int life; //: Hátralévő élettartam, ennyiszer használható még a tárgy.
    boolean active; //: Jelzi, hogy aktív-e az item.
    
    //A Characters.Person ezzel a hívással jelez ha elfogadja a tárgyvédelmi ajánlatát.
    public void acceptProtection(){}

    /**
     * Activates the item
     */
    public void activate(){}

    // Beállítja a tárgy szobáját.
    public void changeRoom(Room room){}

    /**
     * Destroys the item
     */
    public void destroy(){
        owner = null;
        room = null;
    }

    //A Characters.Person ezzel a hívással jelez az összes általa birtokolt Items.Item felé ha gázos szobában tartózkodik.
    public void gasThreat(){}

    //Beállítja a tárgy tulajdonosát és kezeli azt az eseményt, hogy a tárgyat felvették, utóbbi néhány implementációban lényeges.
    public void setOwner(Person owner){
        this.owner = owner;
    }

    //A Characters.Person ezzel a hívással jelez az összes általa birtokolt Items.Item felé ha egy oktató megtámadta őt.
    public void teacherThreat(){}

    public void tick(){}
}
