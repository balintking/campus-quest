package characters;

import items.Item;

public class Cleaner extends Person{

    boolean cleaning = false;

    @Override
    public void pickup(Item item) {

    }

    @Override
    public void teacherAttack() {

    }

    public void initClean(){
        cleaning = true;
//      Code can come here if the impl/map branch is merged

        cleaning = false;
    }
}
