package views;

import map.Room;
import utility.GUI;

import java.awt.*;

public class RoomView extends View {
    Room room;

    public RoomView(Room room) {
        super(room);
        this.room = room;
    }

    @Override
    public void draw(){
        if (room.getPeople().contains(GUI.getCurrentStudent())){
            //Draw
            String iconPath = path + (room.isGassed() ? "gassed" : "default") + ".png";
            Point position = new Point(50, 50); // TODO: helyes pozícionálás
            draw(iconPath, 1, position);
        }
    }
}
