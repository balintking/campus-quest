package views;

import map.Room;

public class RoomView extends View {
    Room room;

    public RoomView(Room room, String path) {
        super(path);
        this.room = room;
    }

    @Override
    public void draw(){
        // TODO
    }
}
