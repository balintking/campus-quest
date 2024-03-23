package items;

public class TVSZ extends Item {

    public TVSZ() {
        super();
        life = 3;
    }
    
    //: Értesül az oktató támadásáról és védelmet kínál tulajdonosának.
    @Override
    public void teacherThreat(){
        owner.teacherProtection(this,life);
    }

    //: A Characters.Person elfogadja a védelmi ajánlatot. Az élettartam eggyel csökken, ha eléri a 0-t megsemmisül.
    @Override
    public void acceptProtection(){
        life--;
        if(life == 0) {
            destroy();
        }
    }

}
