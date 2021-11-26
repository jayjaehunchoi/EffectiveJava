# 추상클래스보다는 인터페이스를 우선하라
자바의 다중 구현 메커니즘에는 ```interface```와 ```abstract class```가 존재한다. 자바 8 이후 두 메커니즘 모두
구현 형태로 제공할 수 있다.

하지만 가장 큰 차이점은 추상 클래스는 다중 상속을 받지 못한다는 점이다. 인터페이스는 다중 상속에 자유롭다.
이외에도 인터페이스를 사용해야 하는 이유는 여러가지이다.

## 1. 기존 클래스에도 손쉽게 인터페이스를 구현할 수 있다.
만약 원하는 기능이 없다면 , 인터페이스에 메서드를 추가하고 구현 클래스에 ```implements```만 달아줘도 된다. 하지만 추상클래스는
단 하나만 상속받을 수 있다보니, 진정으로 하위 클래스일때만 상속해주는 것이 좋다.

## 2. 인터페이스는 믹스인 정의에 안성 맞춤이다.
간단하게 설명하자면, 기존의 타입에서 제공하는 기능 외에 ```Comparable``` 등 다른 기능을 수행하는 인터페이스를
받아 그 타입을 쉽게 사용할 수 있다. 추상클래스는 불가하다.

## 3. 인터페이스는 계층구조가 없는 타입 프레임워크를 만들 수 있다.
```가수```와 ```작곡가```라는 직업이 있다. 하지만 ```SingerSongWriter```라는 가수겸 작곡가라는 직업이 있다.
이걸 코드로 옮기면 어떻게 해야할까? 추상클래스에서는 무조건 하위관계로 만들어야하고, 게다가 상속받아야 할 클래스가 두 개이기 때문에
조합 폭발이 발생한다. 그럼 인터페이스로 코드를 작성하면 어떨까? 변화 과정을 살펴보자

### abstract Singer
```java
public abstract class Singer {
    abstract void sing();
}
```

### interface Singer
```java
public interface Singer {
    void sing();
}
```

> Songwriter도 동일한 형태이므로 생략

### abstract로 작성된 경우
```java
public class SingerSongWriter {

    public void sing() {
        System.out.println("노래한다");
    }

    public void compose() {
        System.out.println("작곡한다");
    }
}
```
> 하위개념도 아닌데다가, 두개의 클래스를 상속받을 수 없어 결국 메서드를 다시 조합하여 클래스를 만든다.

### interface로 작성된 경우
```java
public class SingerSongWriter implements Singer, SongWriter{
    @Override
    public void sing() {
        System.out.println("노래한다");
    }

    @Override
    public void compose() {
        System.out.println("작곡한다");
    }
}
```
> 깔끔하게 두개의 클래스를 상속받아 두 메서드 모두 활용할 수 있다.