# 배열보다는 리스트를 사용하라
배열과 제너릭은 정말 큰 차이점이 있다.

1. 배열은 공변이고, 제너릭은 불공변이다.
   - 배을은 함께 변한다. 제너릭은 아니다.

```java
Object[] objectArr = new Long[1];
objectArr[0] = "문자";
```
놀랍게도 위 코드는 컴파일 오류가 나지 않는다.
문법상으로는 ```Long```이 ```Object```의 하위 타입이니 가능하겠지만, 문자가 들어가는 순간 ```ArrayStoreException```을 던진다.

하지만 제너릭은 다르다.
```java
List<Object> ol = new ArrayList<Long>();
ol.add("문자");
```
컴파일 에러가 난다. 양쪽 다 ```Long```저장소에 ```String```을 넣는 상황이고 넣을 수 없다는 것은 동일하다.
런타임에러보다는 컴파일에러가 훨씬 낫지 않을까?

2. 배열은 실체화된다. 하지만 제너릭은 런타임 시 소거된다.

> 배열은 런타임에도 자신이 담기로 한 원소의 타입을 인지하고 확인한다.
> 하지만 제너릭은 컴파일 때 원소 타입을 검사하며 런타임에는 원소의 타입을 알 수 조차 없다.

이 둘은 조합 자체가 맞지 않는 것이다. 제너릭 배열은 컴파일에서부터 에러가 난다.
만약 컴파일 에러가 나지 않는다면? 문제가 발생할 것이다.

```java
List<String>[] aList = new List<String>[1];
List<Integer> intList = List.of(42);
Object[] objects = aList;
objects[0] = intList;
String s = aList[0].get(0);
```
만약 위 코드가 컴파일에 통과한다고 가정해보자
그러면 ```Integer```값이 ```String```에 들어가게 된다. 완전히 그 의도를 잘못 해석하게 되어버린 것이다.


그렇다면 이제부터 왜 제너릭 리스트를 사용해야 하는지 코드로 앏아보자.
```java
public class Test {
    private final Object[] arr;
    
    public Test(Collection tests){
        arr = tests.toArray();
    }
    
    public Object test(){
        ThreadLocalRandom current = ThreadLocalRandom.current();
        return arr[current.nextInt(arr.length)];
    }
}
```
위 코드는 매번 ```Object```를 사용하고자 하는 형태로 형변환해줘야 하고,
만약 예상치 못한 타입이 들어올떄마다 에러가 난다. 그대로 제너릭으로 바꿔보자.

```java
public class Test<T> {
    private final T[] arr;

    public Test(Collection<T> tests){
        arr = (T[])tests.toArray(); 
    }

    public Object test(){
        ThreadLocalRandom current = ThreadLocalRandom.current();
        return arr[current.nextInt(arr.length)];
    }
}
```
컴파일은 되지만 , 이런 경고 문구를 남겨준다.
```
Unchecked cast: 'java.lang.Object[]' to 'T[]' 
```
컴파일 되는 순간, ```T```의 타입을 보장하지 못한다. 즉, 이렇게 사용하면 안된다. 그렇다면 리스트를 사용하여 수정해보자.

```java
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
```
오류도 나지 않고 컴파일도 잘된다. 이렇게 사용하자 의심의 여지가 없다.

### 마무리
배열과 제너릭은 상극이다. 특히 배열은 원소 타입 체크를 계속해야하는데, 제너릭은 원소 타입이
런타임시에는 소거되어 확인할 수가 없다. 그러니, List를 사용하여 문제를 해결하자.