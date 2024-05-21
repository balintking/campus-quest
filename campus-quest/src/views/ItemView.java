package views;

import items.Item;
import utility.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ItemView extends View {
    Item item;

    public ItemView(Item item) {
        super(item);
        this.item = item;

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    item.activate();
                } else if(e.getButton() == MouseEvent.BUTTON2){
                    GUI.getCurrentStudent().drop(item);
                }
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

    /**
     * Adds the item to the inventory or to the room corresponding to the item's state.
     */
    @Override
    public void draw(){
        //Draw
        String iconPath = path + (item.isActive() ? "active" : "inactive") + ".png";
        ImageIcon icon = GUI.rescaleIcon(new ImageIcon(iconPath), 1);
        setIcon(icon);
        setPreferredSize(new Dimension(50, 50));

        if (item.getOwner() == GUI.getCurrentStudent()) {
            GUI.addToInventory(this);
        } else if (item.getOwner() == null && item.getRoom() == GUI.getCurrentStudent().getRoom()) {
            GUI.addToRoom(this);
        }

//        Point position = new Point(50, 50); // TODO: helyes pozícionálás
//        draw(iconPath, 1, position);

    }
}
