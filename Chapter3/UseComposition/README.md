# 상속보다는 컴포지션을 사용하라

상속은 코드를 재사용하는 강력한 수단이다. 하지만 잘못 사용하면 오류를 발생시킬 수 있다.
완벽한 ```is-a``` 관계라면 상속을 사용하는 것이 좋겠지만, 그것이 아니라면 조립하자.

> 상속을 잘못 사용하게되면, 예기치 않는 오류를 발생시킬 수 있다. 인스턴스화 하여 사용할 때 
> 상위 클래스의 메서드를 사용한다든지, 혹은 상속시 상위클래스 메서드를 사용하게 되면서 예상치 못한
> 동작이 발생한다든지. 또 너무 많은 클래스가 생겨날 가능성도 있다.

같은 상황에서 상속과 컴포지션으로 클래스를 구현해보고 차이점을 비교해보자.

### 상속
```java
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
}
```
위 코드는 ```ArrayList```를 상속 받아 사용된다. 겉보기에는 문제가 전혀 없는 것 같다.
그렇다면 이 ```API```를 이용하는 사용자 입장에서는 어떨까?

```java
public static void main(String[] args) {
    Trunk trunk = new Trunk();
    trunk.put(new Luggage(5));
    trunk.add(new Luggage(10)); // 제한무게가 10이기 때문에 오류가 나야한다. 하지만 그렇지 않다.
}
```
```trunk```를 입력하고 ```IDE```의 도움을 받으면, ```ArrayList```의 메서드가 잔뜩나온다.
그러면 사용자는 ```put```메서드를 쓸지, ```add```메서드를 쓸지 헷갈린다.
그렇게 두개를 동시에 사용한 사용자는 , 결국 제한무게 10이 초과했음에도 짐을 트렁크에 실어버리게 된다.

이 문제점은 조립으로 간단하게 해결된다.

```java
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
}
```
```ArrayList```를 상속받지 않고, 필드 변수 하나로 선언하였다. 그리고 ```add``` 메서드를 두어 트렁크에
짐을 실는 기능을 구현하였다. 이 클래스를 한 번 사용해보자.

```java
public static void main(String[] args) {
    TrunkComp trunkComp = new TrunkComp();
    trunkComp.add(new Luggage(5));
    trunkComp.add(new Luggage(10));
}

// Exception in thread "main" java.lang.IllegalArgumentException: 무게초과
```
사용자가 원하는대로 무게초과라는 결과를 받을 수 있다. 

### 마무리
상속도 물론 좋지만, 상속의 취약점을 피하려면 상속 대신 컴포지션, 전달을 사용하자