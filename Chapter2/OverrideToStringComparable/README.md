# toString 재정의
> toString 일반 규약 : 간결하면서 사람이 읽기 쉬운 형태의 유익한 정보

toString을 잘 구현한 클래스는 디버깅이 정말 쉬워진다. BaseballGameManager 클래스를 만들어 적당한 ```toString```을 만들어보자.

```java
public class BaseBallGameManager {

    private int strike = 0;
    private int ball = 0;

    public void doStrike() {
        strike++;
    }

    public void doBall() {
        ball++;
    }

    @Override
    public String toString() {
        return strike + " 스트라이크 " + ball + " 볼";
    }
}
```
> GameManager는 Game의 스코어를 띄우고, 저장하고, 변경시켜준다. gameManager를 toString으로 찍었을 때
> 주소값이 나오는 것보다, 현재 게임의 상황을 띄워주는 것이 훨씬 간결할 것이다.

# Comparable 구현

```Comparable```이 구현된 클래스의 인스턴스에는 자연적 순서가 존재한다. 그래서 ```Comparable```을 구현한 객체 배열은
```java.util.Arrays```에서 제공하는 ```sort``` 메서드를 이용하여 쉽게 정렬할 수 있다.

클래스를 만들때 정렬이 중요하다면 ```Comparable```을 상속받아 ```compareTo```메서드를 구현하자. 이 때 , 관계 연산자 
```<```, ```>```를 사용하는 것보다, ```java.util.Comparator```의 ```comparing~~```을 이용하여 비교하자.

```java
private static final Comparator<AlphabetAndNumber> COMPARATOR =
        comparingInt((AlphabetAndNumber an) -> an.number) // 숫자값 먼저 비교하고
        .thenComparing(an -> an.alphabet); // 숫자값이 같을 때는 알파벳값 비교

@Override
public int compareTo(AlphabetAndNumber o) {
    return COMPARATOR.compare(this, o); // 비교 메서드 구현
}
```

그리고 결과를 찍어보면 원하는 순서대로 잘 찍히는 것을 볼 수 있다.
```java
[AlphabetAndNumber{number=95, alphabet='zz'}, AlphabetAndNumber{number=100, alphabet='ab'}, AlphabetAndNumber{number=100, alphabet='cd'}]
```

## 마무리
1. 잘 구현된 ```toString```은 프로젝트를 깔끔하게 만든다. 하지만 JPA Entity에서의 ```toString```구현은 항상 고민해야한다.(양방향 연관관계 제외)
2. 순서를 고려해야 하는 값 클래스를 작성한다면 꼭 ```Comparable```을 구현하자. 그리고 비교연산자를 쓰지말고 라이브러리가 제공하는 비교 방법을 정적 메서드로 구현하고
이를 사용하여 개발에 임하자.
