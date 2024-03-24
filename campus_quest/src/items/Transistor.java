package items;

import characters.Person;
import map.Room;
import utility.Logger;

public class Transistor extends Item{
    int id;//: Ha felvesznek egy tranzisztort és aktiválják, kap egy id-t az aktiválásuk sorrendjében, és ezek szerint az id-k szerint fognak összekapcsolódni.
    Transistor pair;//: Az összekapcsolt tranzisztor párja.
    public Person lastOwner;

    public Room getDestination(){
        Logger.logCall("getDestination","Room");
        Room ret;
        if(!active){
            ret = null;
        } else{
            ret = room;
        }
        Logger.logReturn();
        return ret;
    }

    @Override
    public void changeRoom(Room roomInput){
        Logger.logCall("changeRoom", new Object[]{room}, "void");
        room = roomInput;
        Room pairRoom = pair.getDestination();
        if(pairRoom != null){
            if(Logger.testerInput("Is the room not full?")){
                lastOwner.teleport(pairRoom);
                lastOwner = null;
                deactivate();
                pair.deactivate();
            }
        }
        Logger.logReturn();
    }

    public void setPair(Transistor t){
        pair = t;
    }

    //: A tranzisztor bekapcsolása. Ha még nincs párja akkor aktiváláskor id-t kap.
    @Override
    public void activate(){
        Logger.logCall("activate","void");
        active = true;
        lastOwner = owner;
        Logger.logReturn();
    }

    //: Ezt a függvényt hívja meg a tranzisztor magán és a párján, amikor a hallgató elteleportál.
    public void deactivate(){
        Logger.logCall("deactivate","void");
        active = false;
        Logger.logReturn();
    }
    @Override
    public void destroy(){
        Logger.logCall("destroy", "void");
        owner = null;
        room = null;
        pair.reset();
        Logger.logReturn();
    }

    //: A párja hívja meg ezt a függvényt, amikor elpusztul (oktató megsemmisíti). Ekkor a tranzisztor deaktiválódik, amíg újra fel nem veszi egy hallgató és össze nem köti egy új tranzisztorral. 
    public void reset(){
        Logger.logCall("reset", "void");
        deactivate();
        Logger.logReturn();
    }
}
