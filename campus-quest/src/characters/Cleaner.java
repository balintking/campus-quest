package characters;

import items.Item;
import map.Door;
import map.Room;
import utility.Logger;

import java.util.List;
import java.util.Random;

public class Cleaner extends Person{

    boolean cleaning = false;

    @Override
    public boolean pickup(Item item) {
        return false;
    }

    @Override
    public void teacherAttack() {

    }

    /**
     * @param door
     * @return
     * it moves through the given door if the door's destination isn't full,
     * unless it is currently cleaning, then it stayes in the room (it not evacuates itself)
     */
    @Override
    public boolean move(Door door){
        Logger.logCall("move",new Object[]{door},"void");
        if(cleaning){
            Logger.logReturn(false);
            return false;
        }
        Room r = door.getDest();
        if(r.addPerson(this)){
            this.getRoom().removePerson(this);
            this.setRoom(r);
            Logger.logReturn(true);
            return true;
        }
        Logger.logReturn(false);
        return false;
    }

    /**
     * It cleans the room, evacuates it and ungas it.
     */
    public void initClean(){
        cleaning = true;
//      Code can come here if the impl/map branch is merged
        /*this.getRoom().clean();
        this.getRoom().evacuate();
        this.getRoom().unGas();*/
        cleaning = false;
    }

    /**
     * It cleans the room, evacuates it and ungas it, after the Cleaner moves
     * to a neighbouring room with a chance of 1/2, if the chosen room isnt full
     */
    public void tick(){
        initClean();
        List<Door> reachableDoors=this.getRoom().getDoors();
        Random random1=new Random();
        Random random2=new Random();
        int y=random1.nextInt(2);
        if (y == 1) {
            int x=random2.nextInt(reachableDoors.size());
            this.move(reachableDoors.get(x-1));
        }
    }
}
