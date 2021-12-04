# 이왕이면 제네릭 타입으로 만들라

```collections```라이브러리에 있는 자료구조들은 대부분 ```제너릭```으로 구현되어 있다.
직접 ```제너릭```을 사용하지 않는 자료구조를 구현해보고 불편함을 인식한뒤,
이를 ```제너릭```으로 변환하면서 이에 대해 알아가보자.

### MyStack

```java

public class MyStack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public MyStack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop(){
        if(size == 0){
            throw new EmptyStackException();
        }
        Object element = elements[--size];
        elements[size] = null; // 참조해제
        return element;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private void ensureCapacity(){
        if(elements.length == size){
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```
제네릭 대신 ```Object```를 사용하여 어떤 객체든 들어올 수 있게끔 해두었다.
컴파일 타임에도 당연히 오류가 나지 않는다. 문제가 없는 것일까?

```java
public static void main(String[] args) {
    MyStack stack = new MyStack();
    stack.push(1);
    stack.push("1"); // 1

    System.out.println((int)stack.pop() + (int)stack.pop()); // 2
}
```
문제점이 보이는가? 사용자는 정수형 데이터를 stack으로 저장하고자 하는데,,,
1. 문자형 데이터가 스택 내부로 들어간다.
2. 매번 값을 가져올 때마다 형변환을 해야한다, 또 사용자는 숫자인 줄 알고 ```int```로 형변환해서 더하는데.. 실행시켜보면 런타임 에러가 발생한다.

```
Exception in thread "main" java.lang.ClassCastException: class java.lang.String cannot be cast to class java.lang.Integer (java.lang.String and java.lang.Integer are in module java.base of loader 'bootstrap')
at item29.MyStack.main(MyStack.java:44)
```

지금 발생하는 이 불편함과, 에러는 제너릭 타입으로 변경하면서 해결된다.
한 번 제너릭 타입으로 변경해보자.

#### 타입 매개변수
먼저 클래스 선언 시 타입 매개변수를 추가해준다. 자료구조는 주로 제너릭 ```E```를 사용한다.
> E, T 이런것들이 따로 의미가 있지는 않고 , E는 자료구조의 Element 타입을 선언할 때 많이 사용한다.
> 하나의 약속이다.

```java
public class MyStack<E> { //... }
```
그리고 기존에 ```Object```로 선언된 배열을 제너릭 ```E```로 변경한다.

```java
public class MyStack<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public MyStack(){
        elements = new E[DEFAULT_INITIAL_CAPACITY]; // 1 
    }

    public void push(E e){
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop(){
        if(size == 0){
            throw new EmptyStackException();
        }
        E element = elements[--size];
        elements[size] = null; // 참조해제
        return element;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private void ensureCapacity(){
        if(elements.length == size){
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```
위와 같이 코드를 작성하면 , 1번 지점에서 오류가 발생한다.
> 제너릭 E는 실체화 불가하다.
하지만 배열은 실체화 되어 런타임에도 요소의 타입을 구분할 수 있어야한다.
따라서 이렇게 코드를 작성하면 컴파일 에러가 발생한다.

간단하게 배열을 ```Object```로 생성하고, 제너릭 타입으로 형변환 하면 문제가 해결된다.
```java
    public MyStack(){
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY]; 
    }
```

하지만 이러한 비검사 형변환은, 타입의 안전성이 보장되지 않기때문에 컴파일러에서 아래와 같은 경고를 띄운다.
> Unchecked cast: 'java.lang.Object[]' to 'E[]'

그러나 우리는 안전성을 보장할 수 있다. 이 배열로 접근하기 위해서 사용하는 메서드는 ```push```이다.
```push```로 들어오는 요소의 타입은 무엇일까?

```java
public void push(E e){
    ensureCapacity();
    elements[size++] = e;
}
```
바로 제너릭 ```E```이다. 안정성을 보장할 수 있다. 컴파일러가 경고를 내지 않도록 ```@SupressWarning```을 작성해주자.

### 마무리
클라이언트에서 직접 형변환해야하는 타입보다 제너릭 타입이 더 안전하고 사용하기 편하다.
제너릭 타입은 사용자도 편하고 개발자도 편한 아주 좋은 자료형이다 !
