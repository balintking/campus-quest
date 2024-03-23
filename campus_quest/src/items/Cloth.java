package items;

import utility.Logger;
import characters.Person;
import java.util.List;

public class Cloth extends Item{

    // a Items.Cloth meghívja a szoba összes Personjének a clothStun() függvényét majd megsemmisül.
    public void activate(){
        Logger.logCall("activate","void");
        room=owner.getRoom(); //probably nem kell ha van Room valtozoja az Izemnek, de szekvencian rajta van
        List<Person> targets=room.getPeople();
        for(Person p: targets){
            p.clothStun();
        }
        this.destroy();
        Logger.logReturn();
    };

}
