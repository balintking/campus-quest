package utility;

import characters.Student;
import views.View;

public class GUI {
    private static GameState state;

    public static Student getCurrentStudent() {
        return state.getCurrentStudent();
    }

    public static void update() {
        for(View v : state.getViews())
            v.draw();
    }

    public static void win() {
        // TODO
    }

    public static void lose() {
        // TODO
    }

    public static void setState(GameState state) {
        GUI.state = state;
    }

    public static void next() {
        Student s = state.nextStudent();
        // TODO
    }
}
