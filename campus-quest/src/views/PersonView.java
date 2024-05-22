package views;

import characters.Person;
import utility.GUI;

import javax.swing.*;
import java.awt.*;

public class PersonView extends View {
    Person person;

    public PersonView(Person person) {
        super(person);
        this.person = person;
    }

    @Override
    public void draw(){
        //Draw
        String iconPath = path + (person.isStunned() ? "stunned" : "default") + ".png";

        ImageIcon icon = GUI.rescaleIcon(new ImageIcon(iconPath), 1);
        setIcon(icon);
        setPreferredSize(new Dimension(100, 200));

        if (person.getRoom() == GUI.getCurrentStudent().getRoom()) {
            GUI.addToRoom(this, 100, 200);
        }
    }


    @Override
    public boolean isDestroyed() {
        return person.isDestroyed();
    }
}
