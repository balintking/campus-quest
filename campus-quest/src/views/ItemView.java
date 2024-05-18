package views;

import items.Item;

public class ItemView extends View {
    Item item;

    public ItemView(Item item, String path) {
        super(path);
        this.item = item;
    }

    @Override
    public void draw(){
        // TODO
    }
}
