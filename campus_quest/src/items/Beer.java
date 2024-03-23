package items;

public class Beer extends Item{

    public Beer() {
        super();
        life = 2;
    }

    @Override
    public void teacherThreat() {
        if(active) {
            owner.teacherProtection(this, 0);
        }
    }

    @Override
    public void tick() {
        if(active) {
            life--;
        }
        if(life == 0) {
            destroy();
        }
    }

}

