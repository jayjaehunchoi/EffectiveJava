package CreateConstructor;

import java.util.regex.Pattern;

public class PasswordValidator {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");

    public static boolean isPasswordMatches1(String password){
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");
    }

    public static boolean isPasswordMatches(String password){
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String password = "qwer1234!";
        for(int i = 0 ; i < 1000000 ; i++){
            isPasswordMatches(password);
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start + " ms");

    }
}
