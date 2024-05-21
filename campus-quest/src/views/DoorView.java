package views;

import map.Door;
import utility.GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DoorView extends View {
    Door door;

    public DoorView(Door door) {
        super(door);
        this.door = door;

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1)
                    GUI.getCurrentStudent().move(door);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    @Override
    public void draw(){
        //Draw
        if (door.getSrc().isCursed())
            return;
        String iconPath = path + "default.png";
        Point position = new Point(700, 50); // TODO: helyes pozícionálás
        GUI.addToDoors(this);
        draw(iconPath, 1, position);
    }
}
