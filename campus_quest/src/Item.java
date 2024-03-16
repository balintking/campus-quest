public abstract class Item implements Entity {

    Person owner; //: Kinél van az adott tárgy. Ha szobában van, null.
    Room room; //: Melyik szobában van az adott tárgy. Ha személynél van, null.
    String name; //: A tárgy neve
    int life; //: Hátralévő élettartam, ennyiszer használható még a tárgy.
    boolean active; //: Jelzi, hogy aktív-e az item.
    
    //A Person ezzel a hívással jelez ha elfogadja a tárgyvédelmi ajánlatát.
    void acceptProtection(){};

    //A tárgy aktiválása.
    void activate(){};

    // Beállítja a tárgy szobáját.
    void changeRoom(Room room){};

    //A tárgy megsemmisítése.
    void destroy(){};

    //A Person ezzel a hívással jelez az összes általa birtokolt Item felé ha gázos szobában tartózkodik.
    void gasThreat(){};

    //Beállítja a tárgy tulajdonosát és kezeli azt az eseményt, hogy a tárgyat felvették, utóbbi néhány implementációban lényeges.
    void setOwner(Person owner){};

    //A Person ezzel a hívással jelez az összes általa birtokolt Item felé ha egy oktató megtámadta őt.
    void teacherThreat(){};

    public void tick(){}
}
