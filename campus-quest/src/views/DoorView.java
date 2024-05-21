package views;

import map.Door;

import java.awt.*;

public class DoorView extends View {
    Door door;

    public DoorView(Door door) {
        super(door);
        this.door = door;
    }

    @Override
    public void draw(){
        //Draw
        if (door.getSrc().isCursed())
            return;
        String iconPath = path + "default.png";
        Point position = new Point(700, 50); // TODO: helyes pozícionálás
        draw(iconPath, 1, position);
    }
}
