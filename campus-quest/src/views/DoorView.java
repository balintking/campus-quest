package views;

import map.Door;

public class DoorView extends View {
    Door door;

    public DoorView(Door door, String path) {
        super(path);
        this.door = door;
    }

    @Override
    public void draw(){
        // TODO
    }
}
