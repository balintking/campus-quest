package items;

public class Transistor extends Item{
    int id;//: Ha felvesznek egy tranzisztort és aktiválják, kap egy id-t az aktiválásuk sorrendjében, és ezek szerint az id-k szerint fognak összekapcsolódni.
    Transistor pair;//: Az összekapcsolt tranzisztor párja.

    //: A tranzisztor bekapcsolása. Ha még nincs párja akkor aktiváláskor id-t kap. 
    public void activate(){};

    //: Ezt a függvényt hívja meg a tranzisztor magán és a párján, amikor a hallgató elteleportál.
    public void deactivate(){};

    //: A párja hívja meg ezt a függvényt, amikor elpusztul (oktató megsemmisíti). Ekkor a tranzisztor deaktiválódik, amíg újra fel nem veszi egy hallgató és össze nem köti egy új tranzisztorral. 
    public void reset(){};
}
