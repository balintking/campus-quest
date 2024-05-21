package views;

import characters.Person;

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
        Point position = new Point(50, 50); // TODO: helyes pozícionálás
        draw(iconPath, 1, position);
    }
}
