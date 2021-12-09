# 제네릭
제네릭을 지원하기 전에는 컬렉션에서 객체를 꺼낼 때마다 형변환을 해야 했다. 누군가 실수로 엉뚱한 타입의
객체를 넣어두면 런타임에 형변환 오류가 나곤 했다. 반면, 제네릭을 사용하면 컬렉션이 담을 수 있는 타입을 컴파일러에게
알려주게 된다. 컴파일러는 알아서 형변환 코드를 추가할 수 있고, 엉뚱한 객체를 넣으려고하면, 컴파일 과정에서 차단되어
안전하고 명확한 프로그램을 만들어 준다.

# 로 타입은 사용하지 말라
> 로타입은 제네릭에 구체 클래스가 입력되지 않은 ```List``` 같은 것이라고 생각하면 된다.

자바 5 전까지는 제너릭이란 것이 존재하지 않았기 때문에, 과거의 코드들을 사용하기 위해 로타입을 지원하지만 
웬만하면 이런 코드를 작성하면 안된다.

```java
List list = new ArrayList();
        list.add(1);
        list.add("a");
        System.out.println(list);
```
> 위 코드는 컴파일에러가 발생하지 않는다. 값도 잘 들어간다.
> 하지만 List를 이렇게 사용하는 것이 맞을까? 만약 저 상태에서 다음과 같은 비즈니스 로직을 작성한다고 가정해보자

```java
System.out.println((int)list.get(0) * (int)list.get(1));
```
> 값을 뺄 때 형변환을 해줘야 할 뿐 아니라 "a"는 ```ClassCastException```을 발생시킨다.

값을 넣는 시점에서 알아채지 못했으니, 로직을 실행시키는 시점에 발견한 에러는 한참을 디버깅해야 할 것이다.
즉, 제너릭이 주는 안정성과 표현력 모두 잃는 것이다.

하지만 로타입을 사용해야 하는 순간이 아예 없는 것은 아니다.

1. class 리터럴
```java
List.class
```

2. instanceof
```java
if(list instanceof List){
    
}
```

위 두가지를 제외하고는 절대 로타입을 사용하지말고 제네릭을 받아 사용하자.
