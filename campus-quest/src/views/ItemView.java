package views;

import items.Item;

import java.awt.*;

public class ItemView extends View {
    Item item;

    public ItemView(Item item) {
        super(item);
        this.item = item;
    }

    @Override
    public void draw(){
        //Draw
        String iconPath = path + (item.isActive() ? "active" : "inactive") + ".png";
        Point position = new Point(50, 50); // TODO: helyes pozícionálás
        draw(iconPath, 1, position);
    }
}
