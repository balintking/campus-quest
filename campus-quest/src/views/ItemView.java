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
                    if (item.getRoom() != null) {
                        GUI.getCurrentStudent().pickup(item);
                    } else {
                        GUI.getCurrentStudent().drop(item);
                    }
                } else if(e.getButton() == MouseEvent.BUTTON3){
                    if (item.getOwner() == GUI.getCurrentStudent()) {
                        item.activate();
                    }
                }
                GUI.update();
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
        setPreferredSize(new Dimension(100, 100));
        ImageIcon icon = GUI.rescaleIcon(new ImageIcon(iconPath), 1);
        setIcon(icon);

        if (item.getOwner() == GUI.getCurrentStudent()) {
            GUI.addToInventory(this);
        } else if (item.getOwner() == null && item.getRoom() == GUI.getCurrentStudent().getRoom()) {
            GUI.addToRoom(this, 100, 100);
        }
    }
}
