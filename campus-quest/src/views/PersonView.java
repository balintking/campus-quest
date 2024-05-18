package views;

import characters.Person;

public class PersonView extends View {
    Person person;

    public PersonView(Person person, String path) {
        super(path);
        this.person = person;
    }

    @Override
    public void draw(){
        // TODO
    }
}
