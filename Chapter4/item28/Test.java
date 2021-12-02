package item28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Test<T> {
    private final List<T> aList;

    public Test(Collection<T> tests){
        aList = new ArrayList<>();
    }

    public Object test(){
        ThreadLocalRandom current = ThreadLocalRandom.current();
        return aList.get(current.nextInt(aList.size()));
    }
}
