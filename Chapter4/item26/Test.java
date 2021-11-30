package item26;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(1);
        list.add("a");
        System.out.println((int)list.get(0) * (int)list.get(1));
    }
}
