public class Teacher extends Person {

    //A rongy hatására megbénul 3 körig.
    public void clothStun(){}; 

    //A gáz hatására megbénul 2 körig.
    public void gasStun(){};

    //Megtámadja a vele egy szobában tartózkodó személyeket.
    public void initAttack(){};

    //A felvett tárgyat megsemmisíti.
    public void pickup(Item item){};

    //Oktató támadására immunis, ez az implementáció semmit nem csinál. 
    public void teacherAttack(){};

}
