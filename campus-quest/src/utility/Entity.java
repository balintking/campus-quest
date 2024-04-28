package utility;

public interface Entity {

    /**
     * Indicates the passing of time.
     */
    public void tick();

    /**
     * Returns whether the entity is destroyed
     */
    public boolean isDestroyed();
}
