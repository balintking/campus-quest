package views;

import utility.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class View extends JLabel {

    protected String path;

    protected View(String path) {
        this.path = "campus-quest" + File.separator + "resources" + File.separator + path + File.separator;
    }

    public abstract void draw();

    public void leftClick() {}

    public void rightClick() {}

    protected void draw(String iconPath, double iconScale, Point position){
        ImageIcon icon = GUI.rescaleIcon(new ImageIcon(iconPath), iconScale);
        setIcon(icon);
        setBounds(position.x, position.y, icon.getIconWidth(), icon.getIconHeight());
    }
}
