# try - finally 보다 try - with - resources 사용하기

```java```로 데이터 연결을 하거나 , 입력값을 받을 때, 파일을 읽어올 때 항상 사용하는 구문이 있다.
바로 ```try - catch - finally``` 이다. 
매번 이 구조로 코드를 작성하는게 사실 크게 귀찮지는 않지만, 개발자는 항상 더 편한 방법을 추구해야한다.

그렇다면 한 번 자바 7버전 이후의 ```close```처리를 작성해보자.

### 복수의 try catch를 간편하게
```java
public String firstLineOfFile(String path, String defaultVal){
    try (BufferedReader br = new BufferedReader(new java.io.FileReader(path))){
        return br.readLine();
    }catch (IOException e){
        return defaultVal;
    }
}
```
> BufferedReader, FileReader 모두 close를 호출해줘야한다. 하지만 ```try - with - resources``` 를 이용해 간단하게 AutoClose를 호출한다.
> 그렇다면 이렇게 작동하는 원리는 무엇일까?

### AutoClose 객체 상속

> FileReader의 부모 클래스를 쭉 타고 올라가자
```java
public interface AutoCloseable {
    /....
}
```
바로 ```AutoCloseable``` 이라는 클래스를 상속 받는다. 이 클래스는 대표적으로 InputStreamReader를 상속하기 때문에, ```close```를 
호출해야 하는 대부분의 객체가 ```AutoCloseable```을 상속 받는다고 보면 된다.

또한 ```java.sql``` 패키지의 ```Connection``` 등 클래스도 ```AutoCloseable```을 상속 받는다.

```AutoCloseable```을 상속받는 클래스는 ```try - with - resources```로 호출했을 때 메서드 종료 시 자동으로 ```close()``` 메서드를 호출해준다.
따라서 자바 7 버전 이상을 사용할 때 close가 필요한 객체를 사용한다면 ```try - with - resources```의 사용은 필수이다. 