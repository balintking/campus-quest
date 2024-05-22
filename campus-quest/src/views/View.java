package views;

import utility.Entity;
import utility.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class View extends JLabel {

    protected String path;
    private static Map<String,String> pathCatalog = new HashMap<String,String>();


    static {
        pathCatalog.put("room","room");
        pathCatalog.put("door","door");
        pathCatalog.put("airfresehener","airfresehener");
        pathCatalog.put("beer","beer");
        pathCatalog.put("camembert","camembert");
        pathCatalog.put("tvsz","tvsz");
        pathCatalog.put("cloth","cloth");
        pathCatalog.put("sliderule","sliderule");
        pathCatalog.put("transistor","transistor");
        pathCatalog.put("mask","mask");
        pathCatalog.put("student","student");
        pathCatalog.put("cleaner","cleaner");
        pathCatalog.put("teacher","teacher");
    }

    protected View(Entity e) {
        this.path = "campus-quest" + File.separator + "resources" + File.separator + pathCatalog.get(e.getClass().getSimpleName().toLowerCase()) + File.separator;
    }

    public abstract void draw();

    protected void draw(String iconPath, double iconScale, Point position){
        ImageIcon icon = GUI.rescaleIcon(new ImageIcon(iconPath), iconScale);
        setIcon(icon);
        setBounds(position.x, position.y, icon.getIconWidth(), icon.getIconHeight());
    }
}
