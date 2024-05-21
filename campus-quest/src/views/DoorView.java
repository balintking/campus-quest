package views;

import map.Door;
import utility.GUI;

import javax.swing.*;
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
        if(GUI.getCurrentStudent().getRoom() == door.getSrc()) {
            String iconPath = path + "default.png";
            GUI.addToDoors(this);
            ImageIcon icon = GUI.rescaleIcon(new ImageIcon(iconPath), 1);
            setIcon(icon);

        }
    }
}
