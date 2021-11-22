# equals는 일반 규약을 지켜 재정의하라.

```eqauls```메서드는 ```Object``` 클래스의 공통 메서드이다. 공통 메서드는 새로운 클래스를 만들 때
재정의 해줘야 하는데, 만약 생성한 클래스를 자료구조에 넣고 ```remove```, ```contains``` 등 동등성 비교가 필요한 상황에 이용하려면 ```equals```를
꼭 재정의해야한다.
#### equals 재정의 하지 않는 상황

> - 각 인스턴스가 본질적으로 고유할 때 (절대 같은 객체는 없음)
> - 인스턴스의 논리적 동치성을 검사할 일이 없을 때
> - 상위 클래스에서 재정의한 ```equals```가 딱 맞을 때
> - 클래스가 ```private```이거나 ```equals```를 호출할 일이 없을 때
>   - equals를 override하고 호출 될 때 error를 throw 해주는 방법 있음


초반에 말했다시피 논리적 동치성을 비교해야 하는 상황 (Map의 key 값 등)에 클래스를 활용하기 위해서는
```equals```를 재정의 해줘야 한다. ```equals```를 재정의할 때 반드시 일반 규약을 따라야 한다.

#### equals 일반 규약
> * 반사성 : null이 아닌 모든 참조 값 x에 대해, ```x.equals(x)```는 ```true```
> * 대칭성 : null이 아닌 모든 참조 값 x,y에 대해, ```x.equals(y)```는 ```true```이면 ```y.equals(x)```도 ```true```
> * 추이성 : null이 아닌 모든 참조 값 x,y,z에 대해 ```x.equals(y)```, ```y.eqauls(z)```이면 ```z.equals(x)```도 ```true```
> * 일관성 : null이 아닌 모든 참조 값 x,y에 대해, ```x.equals(y)```를 반복해서 호출하면 항상 ```true```값 반환하거나 ```false```값 반환
> * null 아님 : null이 아닌 모든 참조값 x에 대해 ```x.equals(null)```은 항상 ```false```

그렇다면 위 규약을 모두 지켜 ```equals```를 재정의해보자

```java
public class PhoneNumber {
    private final int areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof PhoneNumber)) return false;
        PhoneNumber pn = (PhoneNumber) obj;
        return pn.areaCode == this.areaCode && pn.prefix == this.prefix && pn.lineNum == this.lineNum;
    }
}
```
#### 규약 별 비교
```java
PhoneNumber phoneNumber = new PhoneNumber(1, 2, 3);
PhoneNumber phoneNumber1 = new PhoneNumber(1, 2, 3);
PhoneNumber phoneNumber2 = new PhoneNumber(1, 2, 3);

System.out.println("결과는 true  = "+phoneNumber.equals(phoneNumber1));
System.out.println("결과는 true  = "+phoneNumber.equals(phoneNumber));
System.out.println("결과는 모두 true = " + phoneNumber.equals(phoneNumber1) + " " + phoneNumber1.equals(phoneNumber2) + " "+ phoneNumber.equals(phoneNumber2));
System.out.println("결과는 false  = "+phoneNumber.equals(null));

/**
 결과는 true  = true
 결과는 true  = true
 결과는 모두 true = true true true
 결과는 false  = false
 */
```

그렇다면 과연 이렇게 ```equals```만 재정의해줬을 때, 컬렉션의 원소로 사용하여 논리적으로 같은 객체임을 증명할 수 있을까?
```HashSet```을 통해 실험해보자. 위 main 메서드에 아래 내용을 추가했다

```java
Set<PhoneNumber> set = new HashSet<>();
set.add(phoneNumber);
set.add(phoneNumber1);
System.out.println(set.size());
```
자료구조 ```Set```은 논리적으로 같은 값이 들어왔을 때 중복을 제거해준다. 만약 ```phoneNumber```와 ```phoneNumber1```이 동일하다면
```set```의 size는 1이 돼야 한다 하지만 결과는 ```2``` 가 나온다. 그 이유는 다음과 같다.
```
// phoneNumber 참조 주소
OverrideEqualsAndHashCode.PhoneNumber@60addb54

// phoneNumber1 참조 주소
OverrideEqualsAndHashCode.PhoneNumber@3f2a3a5
```

둘은 물리적으로 값은 같지만 사실상 다른 참조값을 참조하고 있다. 그러니 Collection에서는 다른 객체로 인식할 수 밖에 없는 것이다.
이러한 문제점을 해결하기 위해서는 ```hashcode```를 override 해야 한다.

#### hashCode 오버라이드
```java
    @Override
    public int hashCode() {
        return Objects.hash(areaCode,prefix,lineNum);
    }
```

### 마무리
과거에도 정리한 적이 있는데 ```equals```와 ```hashCode```는 짝꿍이다. 논리적, 물리적 동등성을 동시에 비교하기 위해
함께 재정의 되어야 한다. 이를 정말 쉽게 해주는 플러그인이 있다. 바로 lombok이다. lombok에서 제공하는
애노테이션인 ```@EqaulsAndHashCode```를 이용하여 이를 재정의할 수 있다.

