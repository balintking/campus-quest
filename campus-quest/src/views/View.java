package views;

public abstract class View {

    protected String path;

    protected View(String path) {}

    public abstract void draw();

    public void leftClick() {}

    public void rightClick() {}

}
