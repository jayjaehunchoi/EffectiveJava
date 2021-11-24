# 클래스와 멤버의 접근 권한을 최소화하라

프로젝트를 하면 가장 고민되는 부분은 접근 권한과 패키징이다. 적절하게 패키지를 나눠야 접근 권한도 적당한 수준으로
설정할 수 있기 때문에 이 부분이 가장 어렵다고 생각한다.

간단하게 결론부터 말하자면, 프로그램이 돌아갈 수 있을 수준으로 접근 권한을 최소화시키는 것이 최적의 설계라고 할 수 있다.
정보 은닉은 다양한 장점이 있다.

>* 컴포넌트를 병렬로 개발하여 시스템 개발 속도를 높일 수 있다.
>* 컴포넌트를 더 빨리 파악하여 디버깅 할 수 있어 시스템 관리 비용을 낮출 수 있다.
>* 다른 컴포넌트에 영향을 주지 않고 최적화 할 수 있다.
>* 독자적으로 동작하는 컴포넌트라면 소프트웨어의 재사용성을 매우 높인다.
>* 시스템 전체가 완성되지 않아도 잘 돌아간다.

자바에는 정보은닉을 정말 쉽게 할 수 있다. 바로 접근제한자를 이용함으로써 정보은닉이 가능하다.

#### Java의 접근제한자
* private
* package-private(default)
* protected
* public

위에서 말했다시피 모든 클래스와 멤버의 접근성은 가능한 좁혀야한다.

### 클래스
톱레벨 클래스와 인터페이스에는 ```package-private```과 ```public```이 사용된다. 만약 외부에서 API를 사용할 
일이 없다면 ```package-private```을 사용하는 것이 맞다.

```java
// 실제 Hitter가 사용되는 외부에는 Player만 공개되면 된다.
// 따라서 package-private으로 실제 구현체의 동작을 숨긴다.
class Hitter implements Player{

    @Override
    public void play() {
        System.out.println("타자 스윙");
    }
}
```

### 멤버
모든 멤버는 웬만하면 ```private```으로 선언하자. 같은 패키지에서 사용해야 하는 멤버에 한해서만 ```package-private```
으로 선언해주자. 다만, 상속받을 부모 클래스의 멤버가 ```public```으로 선언됐다면, ```public```을 사용해야 한다.

### public 클래스의 필드
```public``` 클래스의 인스턴스 필드는 되도록 ```public```이 아니어야 한다. 필드가 가변 객체를 참조하거나,
final이 아닌 인스턴스 필드를 ```public```으로 선언하면 필드에 담을 수 있는 값을 제한할 힘을 잃는다.

### 접근자 메서드
위에서 말했다시피 ```public``` 클래스의 인스턴스 필드는 ```private```으로 선언해야 한다. 그렇다면 이때 어떻게 인스턴스 필드에
접근할 수 있을까? 바로 접근자 메서드이다. 접근자 메서드를 두어 ```private```으로 선언된 내부 표현 방식을 얼마든지 바꿀 수 있다.

```java
public class PlayerManager {

    private String gameStatus; // public 클래스의 인스턴스, 접근 제한자

    public static Player createHitter(){ 
        return new Hitter(); // 패키지 외부에서는 이와같이 Hitter 클래스를 생성해준다.
    }

    public static Player createPitcher(){
        return new Pitcher();
    }

    // 접근자 메서드를 public으로 두어, 얼마든 필드변수를 변경, 획득할 수 있다.
    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}
```

### 마무리
프로그램 요소의 접근은 가능한 최소한으로 하자. ```public``` 클래스에서는 상수용 ```public static final```
필드 외에는 어떠한 ```public``` 필드도 갖지 말자.