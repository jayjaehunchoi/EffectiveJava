package Practice1;

import java.util.HashMap;
import java.util.Map;

public class StringStore {

    private static Map<Long,String> maps = new HashMap<>();

    private static Long sequence = 0L;

    public static void store(String s){
        maps.put(++sequence, s);
    }

    public static void main(String[] args) {
        for(int i = 0 ; i < 100000; i++){
            String s = String.valueOf("1");
           // String s = new String("1");
            StringStore.store(s);

        }
        checkMemory();
    }

    private static void checkMemory() {
        Runtime.getRuntime().gc();
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.print(usedMemory + " bytes");
    }
}
