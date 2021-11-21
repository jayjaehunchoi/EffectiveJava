# 다 쓴 객체 참조를 해제하라
```Java```에는 최고 수준의 ```Garbage Collector```가 존재한다. 덕분에 개발하면서
메모리 누수에 대한 걱정을 한 적은 없었던 것 같다. 하지만 ```Java```개발자라면 메모리 관리에 대해 확실히 알고 넘어가야 한다고 생각한다.
간단하게 GC를 정리해보고 본문을 시작하자.

#### 언제 Garbage Collector의 대상이 되는가
```
1. 스택영역에 쌓이는 기본 자료형, 참조자는 Stack이 초기화 되는 순간 GC된다.
2. heap 영역에 생성된 참조 자료형 주소는 더이상 참조가 존재하지 않고 끊기게 되면 GC된다.
```

#### 왜 신경써야 하지?
이렇게 깔끔하게 메모리 관리를 해주는데 왜 신경써야 할까? 자바에는 직접 메모리를 관리하는 클래스가 존재한다.
```***Reader```, ```***Writer```,```Connection``` 등 개발자가 직접 ```close```해줘야 하는 클래스
그리고 ```Stack``` 등이 있다. 

또한 익명 클래스를 사용할 때도 그러하다.
> 익명 클래스는 클래스를 구현하지 않고 interface를 받아와 1회성으로 사용하는 클래스 정도로 생각하면 된다.

직접 ```close```를 해주거나, 더이상 사용하지 않는 객체에 대해 ```null```체크를 해주는 방법 등이 있고,
자료구조 같은 경우에는 ```scope```바깥으로 제외시키는 방법이 있다. 예시를 통해 다쓴 객체 참조를 해제해보자.

```java
public class MyStack {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public MyStack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return elements[--size]; // 메모리 누수 발생
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```
> 주석을 달아놓은 곳에서 메모리 누수가 발생한다. 왜일까?

바로 ```elements``` 배열에 size index에 있는 객체를 배열 내에 그대로 두기 때문이다.
Stack 같은 경우에는 ```Vector```를 상속 받는다. 즉 일정 크기의 배열을 두고, 그 크기를 초과할 때마다
자리를 추가해주는 형태이다. 따라서 따로 null 체크를 해주지 않는다면, ```pop``` 메서드를 수행하더라도 실제로 pop 되는 것이 아니다.

#### 개선
```java
    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object element = elements[--size];
        elements[size] = null; // 메모리 누수 해결
        return element;
    }
```
더이상 참조하지 않아야 할 공간에 대해서 참조를 ```null```을 이용하여 끊어버린다.
> 개발자가 Reachability를 직접 관리하여 GC를 해줄 수도 있다. soft, phantom, weak 등
> 아직 Soft나 Phantom은 이해도가 부족하여 직접 작성할 수 없지만 weak Reachablity에 대해 간단히 공유하겠다.

```java
WeakReference<String> wr = new WeakReference<>(new String("ok")); 
String s = wr.get(); // weakRechability를 가진 String을 직접 참조 한다.
s = null; // 참조를 해제시켜주면, 이때 직접 참조되는 주소값은 제거되고 WeakReference만 남는다.

// wr 은 GC 가 돌아갈 때 가비지 컬렉터가 작동한다.
```
이렇게 ```java.lang.ref``` 클래스의 ```WeakRef```를 알아본 이유는 바로 캐시 메모리 누수, 콜백, 리스너 메모리 누수 등을 해결하기 위함이다.
```WeakHashMap``` 은 객체를 ```WeakReference```로 만들어 참조자와 참조값의 관계를 약하게 만들어준다.
이때, ```WeakHashMap```의 key값에 존재하는 객체는 GC에 의해 즉시 수거된다.

참고 : https://d2.naver.com/helloworld/329631