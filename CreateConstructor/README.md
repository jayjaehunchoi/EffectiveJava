# 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

사전이 있다. 어떤 사용자는 영어사전, 어떤 사용자는 한글사전을 이용한다고 가정해보자.
그럴 때 아래와 같이 코드를 작성하면 좋은 설계라고 할 수 있을까?

```java
public class SpellChecker {
    private static final Dictionary dictionary = new Dictionary();

    private static final SpellChecker instance= new SpellChecker();

    private SpellChecker() {

    }
    public SpellChecker getSpellChecker() {
        return instance;
    }
    //...
}
```
당연히 아니다. 사전에는 여러 종류가 있는데, ```Dictionary``` 하나만 지원되는 이 코드는 절대 좋지 않다.
그렇다고 새로 생성자를 만들어 바꿀 수 있는 것도 아니다. 그렇다면 이 문제를 어떻게 해결할까?

의존 주입을 통해 해결할 수 있다.

### Dictionary
> Dictionary로 다형성 구현
```java
public interface Dictionary {
    public void findWord();

    static class KoreanDictionary implements Dictionary{
        @Override
        public void findWord() {
            System.out.println("한국어 사전 탐색");
        }
    }

    static class EnglishDictionary implements Dictionary{
        @Override
        public void findWord() {
            System.out.println("영어 사전 탐색");
        }
    }
}
```

### SpellChecker
> 의존성 주입 가능하게끔 세팅
```java
public class SpellChecker {
    private final Dictionary dictionary;

    public SpellChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void findSpell() {
        dictionary.findWord();
    }
}
```

# 불필요한 객체 생성을 피하라
똑같은 기능의 객체를 매번 생성하기 보다는 객체 하나를 재사용하는 편이 나을 때가 있다.
```java
// Before
String s = new String("문자열");

// After
String s = "문자열";
```
만약 ```Before```처럼 코드를 작성한다면, 반복문이 돌아갈때 수백개의 ```String```객체가 생성될 것이다.
하지만 이런 코드는 거의 작성하지 않아왔을 것이다.

또 다른 예시를 들어보자. 생성 비용이 아주 비싼 객체를 만든다고 가정해보자.
만약 다음과 같은 코드가 있다면 어떨까?
```java
public static boolean isPasswordMatches(String password){
    return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");
}

// 성능 100000개 기준 659 ms
```
비교를 할때마다 매번 Pattern을 생성하고 GC로 버려진다면 성능 측면에서 매우 비효율적일 것이다.
그렇다면 이 코드를 개선해보자
```java
private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");
public static boolean isPasswordMatches(String password){
        return PASSWORD_PATTERN.matcher(password).matches();
}

// 성능 100000개 기준 282 ms
```
사전에 사용할 패턴을 미리 생성해놓고, 이를 사용하는 형태로 코드를 작성하면 , 코드도 더 명확해질 뿐더러
GC가 동작하지 않으므로 계속 새 객체를 생성하지 않아 성능에서도 큰 이득이 있다.

또 다른 불필요한 객체를 만드는 예시가 있다.
바로 ```auto boxing```이다. 간단하게 ```long -> Long``` 에서 발생하는 자동 변환이라고 생각하면 된다.
의미를 봤을 때는 크게 문제가 될 것 같지 않지만 성능에서는 문제가 있다.

> 아래 두 코드를 비교해보자
```java
// auto boxing 있음
public static long sum(){
        Long sum = 0L; 
        for(long i = 0 ; i <= Integer.MAX_VALUE; i++){
            sum += i; // long -> Long
        }
        return sum; // Long -> long
}

// Integer.MAX_VALUE 기준 9145 ms
```
```java
// auto boxing 없음
public static long sum(){
        long sum = 0;
        for(long i = 0 ; i <= Integer.MAX_VALUE; i++){
            sum += i;
        }
        return sum;
}

// Integer.MAX_VALUE 기준 1711 ms
```
기본타입을 사용하는 것만으로 성능에 엄청난 개선이 된다.
하지만 이 또한 새로운 객체를 꼭 만들어야 하는 상황인지 아닌지 잘 구분하여 사용해야 한다.
