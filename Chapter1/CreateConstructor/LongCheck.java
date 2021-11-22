package CreateConstructor;

public class LongCheck {

    public static long sum(){
        long sum = 0;
        for(long i = 0 ; i <= Integer.MAX_VALUE; i++){
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        sum();
        long end = System.currentTimeMillis();

        System.out.println(end - start + " ms");
    }
}
