package Chapter3.UseComposition;

import java.util.ArrayList;

public class Trunk extends ArrayList<Luggage> {
    private int limit = 10;

    public void put(Luggage luggage){
        super.add(luggage);
        isWeightOverLimit(luggage);
        limit -= luggage.getWeight();
    }

    private void isWeightOverLimit(Luggage luggage) {
        if(limit - luggage.getWeight() < 0){
            throw new IllegalArgumentException("무게초과");
        }
    }

    public static void main(String[] args) {
        Trunk trunk = new Trunk();
        trunk.put(new Luggage(5));

        trunk.add(new Luggage(10)); // 제한무게가 10이기 때문에 오류가 나야한다
    }
}
