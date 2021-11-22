package PrivateConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

// generic E(Element) vs T(Type)
// E : 말그대로 요소, 특정 자료구조 내부의 값이라면 Generic E가 어울림
// T : Type 리스트 내 요소가 아닌 모든 것은 T가 더 어울림
public class MemoryRepository<E> {

    private ConcurrentHashMap<Long, E> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;
    private MemoryRepository() {
        if(INSTANCE != null){
            throw new AssertionError("절대 최초생성만 가능.");
        }
    }
    private static final MemoryRepository INSTANCE = new MemoryRepository();

    public static MemoryRepository getInstance(){
        return INSTANCE;
    }

    public void save(E e){
        store.put(++sequence, e);
    }

    public List<E> findAll(){
        ArrayList<E> strings = new ArrayList<>();
        store.forEach((key,value) -> strings.add(value));
        return strings;
    }

    public static void main(String[] args) {
        MemoryRepository repository = getInstance();

        for(int i = 1 ; i < 11 ; i++){
            repository.save("저장"+i);
        }

        System.out.println(repository.findAll());
    }

}
