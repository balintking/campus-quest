import java.util.List;

public abstract class Person implements Entity{
    private String name;
    private boolean stunned;
    private Room currentRoom;
    private List<Item> items;

    // A személyt megbénítja egy rongy
    public void clothStun() {
        // Implementáció szükség szerint
    }

    // Eldobja a szobában lévő tárgyat
    public void drop(Item item) {
        // Implementáció szükség szerint
    }

    // Eldobja az összes tárgyat a szobában
    public void dropAll() {
        // Implementáció szükség szerint
    }

    // Védelmet kínál a gázzal szemben
    public void gasProtection(Item protectionProvider, int priority) {
        // Implementáció szükség szerint
    }

    // A személyt megbénítja a gáz
    public void gasStun() {
        // Implementáció szükség szerint
    }

    // A személy átlép egy ajtón
    public void move(Door door) {
        // Implementáció szükség szerint
    }

    // Felveszi a tárgyat a szobából
    public void pickup(Item item) {
        // Implementáció szükség szerint
    }

    // A SlideRule jelzi a személynek, hogy felvette
    public void slideRuleNotification(Item slideRule) {
        // Implementáció szükség szerint
    }

    // Az oktató megtámadja a személyt
    public void teacherAttack() {
        // Implementáció szükség szerint
    }

    // Védelmet kínál az oktató támadása ellen
    public void teacherProtection(Item protectionProvider, int priority) {
        // Implementáció szükség szerint
    }

    // A személy átkerül egy másik szobába
    public void teleport(Room roomTo) {
        // Implementáció szükség szerint
    }

    public void tick(){}
}
