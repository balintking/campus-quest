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
                GUI.updateRoom(Color.green);
            } else {
                GUI.updateRoom(new Color(40,40,40));
            }
        }
    }


    @Override
    public boolean isDestroyed() {
        return room.isDestroyed();
    }
}
