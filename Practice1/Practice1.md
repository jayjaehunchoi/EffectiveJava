# 생성자 대신 정적 팩터리 메서드를 고려하라

> 그동안 자바에서 인스턴스를 만들기 위해 public으로 선언된
> 클래스를 생성자를 통해 생성하였다. 하지만, 생성자가 많아질 경우 
> 어떤 생성자를 사용할지 헷갈리는 경우가 있었고, 비용이 비싼 클래스를 사용할 경우
> 메모리, 성능의 문제가 생기기도 하였다.

이러한 문제를 해결할 수 있는 방법 중 하나가 바로 생성자 대신 ```정적 메서드 생성```하는 것이다.

## 정적 팩터리 메서드의 장점
### 1. 이름을 가질 수 있다.
> 생성자를 이용하면, 반환될 객체가 어떤 특성을 지녔는지 정확히 알 수 없다.
> 하지만 이름을 가진 정적 팩터리 메서드는 이를 보완할 수 있다.

#### Coupon
```java
class Coupon {

    private String userEmail;
    private String couponNumber;
    private Boolean used;

    public Coupon(String userEmail, String couponNumber, Boolean used) {
        this.userEmail = userEmail;
        this.couponNumber = couponNumber;
        this.used = used;
    }
}
```
만약 이 클래스의 생성자를 이용해서 ```Coupon``` 인스턴스를 만들었다고 가정하면, 정확히 어떤 쿠폰인지 알 수 있을까?
일반적으로 이렇게 사용하긴 하지만, 정확하게 알 수는 없다. 특히 생성자가 많아진다면 더더욱 그렇다.
이런 문제를 다음 클래스를 추가하여 해결할 수 있다.

#### CouponFactory
```java
public class CouponFactory {

    private static String getCouponNumber() {
        return UUID.randomUUID().toString().substring(4, 23);
    }

    public static Coupon createCoupon(String userEmail) {
        return new Coupon(userEmail, getCouponNumber(), false);
    }
}
```
정적 메서드 ```createCoupon```을 만들어 아직 사용하지 않은 신규 쿠폰을 만들어 낸다는 것을 정확하게 알 수 있게 되었다.
지금은 ```Coupon```클래스의 생성자가 한개이지만, 생성자가 많아진다면 이처럼 정적 메서드를 만들어 이름을 주는 것도 좋은 방법이다.

### 2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
> String class로 예시를 들어보자

#### String.valueOf()
```java
public static String valueOf(Object obj) {
        return (obj == null) ? "null" : obj.toString();
}
```
> ```String.valueOf(obj)```를 호출하면 해당 클래스의 ```toString()```을 호출한다.

#### obj.toString()
```java
 public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
```
> 위 코드를 보면 어떠한 생성자도 호출하지 않는다. 그렇다면 인스턴스를 생성하지 않아도 되는 정적 팩터리 메서드의
> 이점은 무엇일까?

```java
public class StringStore {

    private static Map<Long,String> maps = new HashMap<>();

    private static Long sequence = 0L;

    public static void store(String s){
        maps.put(++sequence, s);
    }

    public static void main(String[] args) {
        for(int i = 0 ; i < 100000; i++){
            String s = String.valueOf("1");
            //String s1 = new String("1");
            StringStore.store(s);

        }
        checkMemory();
    }

    private static void checkMemory() {
        Runtime.getRuntime().gc();
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.print(usedMemory + " bytes");
    }
}
```
> 정적 팩토리 메서드인 s 객체 100000를 map에 저장했을 때와, 생성자로 생성된 s1을 map에 저장했을 때의 메모리를 비교해봤다.
#### 정적 팩토리 메서드 s
![image](https://user-images.githubusercontent.com/87312401/142722826-74c8e9c6-a7ce-4719-aaba-26d18c9b70d7.png)

#### 생성자 s1
![image](https://user-images.githubusercontent.com/87312401/142722804-310d5de1-6186-49cc-ac22-1a813aa2de28.png)


> 위 두개의 차이는 약 3MB로 그렇게 크지는 않지만 비용이 비싼 클래스들은 엄청나게 큰 차이가 발생할 수 있다.
> 정적 팩토리 메서드를 사용하는 데에는 메모리 절약, 성능 개선의 장점이 있다.


### 3. 반환 타입의 하위 타입 객체를 반환할 수 있다.
> 정적 팩토리 메서드를 만듦으로서 단지 해당클래스를 생성하는 것이 아니라, 파라미터에 따라
> 하위 타입 객체를 생성하여 API 크기를 경량화할 수있다.

코드를 통해 이 예시를 살펴보자.
### Grade
```java
public interface Grade {

    public static Grade of(int score) {
        if (score >= 80) {
            return new Advanced();
        } else if (score >= 60) {
            return new Intermediate();
        } else {
            return new Basic();
        }
    }
    public void showGrade();
}
```
> 참고 : Java 8부터는 인터페이스에 ```public static``` or ```default static```으로 구현이 가능하고
> 이렇게 구현된 메서드는 ```Override``` 없이 사용 가능하다.

```Grade```라는 API를 사용할 개발자에게 해당 인터페이스만 노출시키면 아래 구현 클래스를 모두 이용할 수 있다.
```java
static class Advanced implements Grade{
    @Override
    public void showGrade() {
        System.out.println("Advanced");
    }
}
static class Intermediate implements Grade{
    @Override
    public void showGrade() {
        System.out.println("Intermediate");
    }
}
static class Basic implements Grade{
    @Override
    public void showGrade() {
        System.out.println("Basic");
    }
}
```

#### 사용 예시
```java
public static void main(String[] args) {
    Grade.of(100).showGrade();
    Grade.of(70).showGrade();
    Grade.of(50).showGrade();
}
```
#### 결과값
![image](https://user-images.githubusercontent.com/87312401/142723111-c927b31b-604d-4d0d-b7be-bc02801d85da.png)

### 마무리
직접 사용해보고 정리한 결과 이렇게 결론지을 수 있을 듯하다.
1. 정적 팩터리 메서드와 public 생성자는 각 장점이 있다. 생성자가 하나라면 생성자를 사용하면 되고, 생성자가 많아질 때 정적 팩터리 메서드를 고려해보자
2. 메모리, 성능 이슈가 발생할 듯한 class를 사용할때는 정적 팩터리 메서드를 사용하자.
3. Jpa를 사용하다보면, ```Dto```를 통해 값을 전달받아 ```Entity```로 변환하고, ```Entity```에서 ```Dto```로 변환하는 경우가 많다. 이럴때 정적팩터리 메서드를 사용해보자

#### 3번 예시
```java
public static UserDto from(final User user){
    this.name = name;
    this.age =age;
}
```