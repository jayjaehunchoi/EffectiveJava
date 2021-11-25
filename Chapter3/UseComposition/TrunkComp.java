package Chapter3.UseComposition;

import java.util.ArrayList;
import java.util.List;

public class TrunkComp {
    private int limit = 10;
    private List<Luggage> store = new ArrayList<>();

    public void add(Luggage luggage){
        store.add(luggage);
        isWeightOverLimit(luggage);
        limit -= luggage.getWeight();
    }

    private void isWeightOverLimit(Luggage luggage) {
        if(limit - luggage.getWeight() < 0){
            throw new IllegalArgumentException("무게초과");
        }
    }

    public static void main(String[] args) {
        TrunkComp trunkComp = new TrunkComp();
        trunkComp.add(new Luggage(5));
        trunkComp.add(new Luggage(10));
    }
}
