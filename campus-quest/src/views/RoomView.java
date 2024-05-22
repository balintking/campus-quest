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
            if(room.isGassed()){
                GUI.updateRoom(new Color(52, 161, 8, 255));
            } else {
                GUI.updateRoom(new Color(202, 133, 48, 255));
            }
        }
    }
}
