package items;

public class Mask extends Item {

    //: A Characters.Person elfogadja a védelmi ajánlatot, az active attribútum igaz értéket kap, a life értéke eggyel csökken.
    public void acceptProtection(){};

    //A Items.Mask értesül az elgázosított szobáról és védelmet kínál tulajdonosának.
    public void gasThreat(){};

}
