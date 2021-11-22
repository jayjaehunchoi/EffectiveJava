package PrivateConstructor;

public class UserUtil {
    public static final String NAME = "최재훈";
    public static final int AGE = 25;
    public static final String EDUCATION = "한양대학교";
    public static final String MAJOR = "교육공학과";

    private UserUtil(){
        throw new AssertionError("상수용 클래스 입니다만 ^^");
    }
}
