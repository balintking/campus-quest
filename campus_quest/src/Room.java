import java.util.List;

public class Room implements Entity{
    List<Person> people;//: Tárolja a benne lévő személyeket.
    List <Door> doors;//: Tárolja a hozzá tartozó ajtókat.
    List <Item> items;//: Tárolja a benne lévő tárgyakat.
    int id;// Szoba azonosítója.
    int capacity;//: A szoba befogadóképessége.
    boolean gased;//: El van-e gázosodva a szoba.
    boolean cursed;//: El van-e átkozva a szoba.
    boolean full;//: Tele van-e a szoba (entitásokkal)

    //: A paraméterként kapott ajtót hozzáadja a szobához.
    void addDoor(Door door){};

    //: A paraméterként kapott tárgyat hozzáadja a szobához.
    void addItem(Item item){};

    //: A paraméterként kapott személyt hozzáadja a szobához.
    void addPerson(Person person){};

    //: A szoba megszűnését jelentő függvény.
    void destroy(){};

    //: A szoba két részre osztódik.
    void divide(){};

    //: A szoba elgázosodik.
    void gas(){};

    //: Összeolvad egy találomra kiválasztott szomszédjával. 
    void merge(){};

    //): A paraméterként kapott ajtót eltávolítja a szobából.
    void removeDoor(Door door){};

    // A paraméterként kapott tárgyat eltávolítja a szobából.
    void removeItem(Item item){}; 

    //: A paraméterként kapott személyt kiveszi a szobából.
    void removePerson(Person person){};

    //: Hatására a szoba bizonyos valószínűséggel osztódik vagy összeolvad egy szomszédjával. 
    public void tick(){}
}
