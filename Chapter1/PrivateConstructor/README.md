# private 생성자로 싱글턴 보증

무상태 객체나 설계상 유일해야하는 시스템 컴포넌트는 싱글턴으로 설계돼야한다.
싱글턴 설계를 보증하기 위해서는 생성자를 ```private```으로 막아두고, 
혹시 모를 두번째 생성자 호출에서는 ```error```를 발생시켜주면 된다.

간단하게 ```MemoryRepository```를 만들고 이를 싱글턴으로 설계해보자.

### MemoryRepository
```java
// Generic 싱글턴 패턴을 만들어 편리하게 이용이 가능하다.
public class MemoryRepository<E> {

    // ConcurrentHashMap으로 동시성 문제 해결
    private ConcurrentHashMap<Long, E> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    private MemoryRepository() {
        // 이미 최초 호출로 INSTANCE가 생성된 뒤, 다시 호출이 이뤄지면 AssertionError 발생
        if (INSTANCE != null) {
            throw new AssertionError("절대 최초생성만 가능.");
        }
    }
    private static final MemoryRepository INSTANCE = new MemoryRepository();

    public static MemoryRepository getInstance() {
        return INSTANCE;
    }

    public void save(E e) {
        store.put(++sequence, e);
    }

    public List<E> findAll() {
        ArrayList<E> strings = new ArrayList<>();
        store.forEach((key, value) -> strings.add(value));
        return strings;
    }
}
```

> 이처럼 private을 이용하여 간단하게 싱글턴을 보증해줄 수 있다.

# private 생성자로 인스턴스화 예방
객체 지향적으로 코드를 작성하려고 하면, 상수값을 모아놓은 클래스를 만들 때가 있다.
그 클래스의 필드 변수들은 모두 ```public static final```로 이뤄져 있다.
그 클래스를 인스턴스화 시킬 필요가 있을까? 당연히 없다.
그렇다면 private 생성자를 이용하여, 클래스 인스턴스화를 막아두자. 
이때,혹시 모를 호출 상황에 대비하여 ```AssertionError```로 생성을 두 번 막자.

### UserUtil
```java
public class UserUtil {
    public static final String NAME = "최재훈";
    public static final int AGE = 25;
    public static final String EDUCATION = "한양대학교";
    public static final String MAJOR = "교육공학과";

    private UserUtil(){
        throw new AssertionError("상수용 클래스 입니다만 ^^");
    }
}
```

## 마무리
내가 만든 API를 사용할 사람이 API 문서를 읽지 않아도 쉽게 API를 사용할 수 있다면 정말 좋은 설계라고 생각한다.
```private``` 생성자를 통한 제어는 설계의 세심한 부분 중 하나인 것 같다. 
싱글턴 보증, 인스턴스화 방지 등 제어 상황을 고려하여 코드를 작성해 나가는 개발자가 되자.