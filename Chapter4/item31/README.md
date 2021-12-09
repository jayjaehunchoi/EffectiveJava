# 한정적 와일드카드를 사용해 API 유연성을 높이라

매개변수화 타입은 불공변이다, ```List<String>```은 ```List<Object>```의 하위 타입이 아니다. (후자가 하는일을 전자는 못함)
같은 ```제너릭 E```로 인자를 받았지만 완벽하게 그 기능을 수행하지 못한다. 이를 해결하는 방법으로 ```한정적 와일드카드```가 있다.

### 예시
```Stack```에 일련의 원소를 넣는 작업을 하는 메서드가 있다고 가정하자.
```java
public void pushAll(Iterable<E> src) {
    for(E e : src) {
        push(e);
        }
}
```

이 메서드를 활용해보자
```java
Stack<Number> stack = new Stack<>();

stack.pushAll(intVal...); // 자료형 Integer
```
```Integer```은 ```Number```의 하위 타입이다. 과연 잘 동작할까?
정답은 ```NO```이다.

매개변수 타입이 불공변이기 때문에, ```Integer```가 ```Number```의 하위타입이더라도, 제너릭에 대입한 상황에서는 정확히 하위 기능을 수행하지 못한다.
이 문제를 한정적 와일드 카드를 통해 해결할 수 있다.

```java
public void pushAll(Iterable<? extends E> src) {
    for(E e : src) {
        push(e);
        }
}
```
입력되는 매개변수를 ```E를 포함한 E의 하위타입```이라는 의미를 가진 한정적 와일드 카드로 선언한다.
그렇다면 반대로 ```popAll```메서드를 만들고 매개변수 컬렉션에 ```pop```한 값을 넣는다고 가정해보자

```java
public void popAll(Collection<E> dst) {
    while (!isEmpty()){
        dst.add(pop());    
    }    
}
```
아무리 제너릭 타입이더라도, ```Stack```의 타입은 ```String``` , ```Collection```의 타입은 ```Integer``` 이라면 로직이 잘 수행될까?
또, 만약 상위, 하위 타입의 관계를 갖더라도 불공변하는 제너릭 타입이 잘 컴파일될까?

당연히 아니다. ```popAll```메서드를 잘 수행하기 위해서는 ```Stack```의 타입, 상위 타입이 들어와야 한다. 이를 수정해보자
```java
public void popAll(Collection<? super E> dst) {
    while (!isEmpty()){
        dst.add(pop());    
    }    
}
```

이처럼 원소의 생산자, 소비자 입력 매개변수에 와일드 카드 타입을 사용하면 유연성이 극대화되고 사용자는 타입으로 스트레스 받지 않아도된다.

조금 복잡하더라도 와일드카드 타입을 사용하면 API가 훨씬 유연해진다. ```생산자는 - extends``` , ```소비자는 - super```를 사용해 유연한 API를 만들자

