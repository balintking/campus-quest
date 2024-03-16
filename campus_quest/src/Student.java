public class Student extends Person {

    //: Immunis a ronggyal szemben, ez egy üres implementáció    ami nem csinál semmit.
    public void clothStun(){}

    //: A gáz ellen megpróbál védekezni (tárgyak felé gasThreat() jelzés), ha nem kap védelmet akkor elejti tárgyait és megbénul 2 körig. 
    public void gasStun(){}

    //: Ezzel a hívással a hallgató védelmi ajánlatot kap gázzal szemben, elfogadja a legnagyobb prioritású ajánlatot. 
    public void gasProtection(Item protectionProvider,int priority){}

    //: A felvett tárgyat eltárolja ha van nála hely. 
    public void pickup(Item item){}

    //: Az oktató támádása ellen megpróbál védekezni (tárgyak felé teacherThreat() jelzés), ha nem kap védelmet, meghal. 
    public void teacherAttack(){}

    //: Ezzel a hívással a hallgató védelmi ajánlatot kap oktatóval szemben, elfogadja a legnagyobb prioritású ajánlatot.
    public void teacherProtection(Item protectionProvider,int priority){}
    
}
