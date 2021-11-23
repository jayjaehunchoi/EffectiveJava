package Chapter2.OverrideToStringComparable;

import java.util.*;

import static java.util.Comparator.*;

public class AlphabetAndNumber implements Comparable<AlphabetAndNumber>{

    private int number;
    private String alphabet;

    private static final Comparator<AlphabetAndNumber> COMPARATOR =
            comparingInt((AlphabetAndNumber an) -> an.number)
            .thenComparing(an -> an.alphabet);

    @Override
    public int compareTo(AlphabetAndNumber o) {
        return COMPARATOR.compare(this, o);
    }

    public AlphabetAndNumber(int number, String alphabet) {
        this.number = number;
        this.alphabet = alphabet;
    }

    @Override
    public String toString() {
        return "AlphabetAndNumber{" +
                "number=" + number +
                ", alphabet='" + alphabet + '\'' +
                '}';
    }

    public static void main(String[] args) {
        AlphabetAndNumber ab = new AlphabetAndNumber(100, "ab");
        AlphabetAndNumber cd = new AlphabetAndNumber(100, "cd");
        AlphabetAndNumber zz = new AlphabetAndNumber(95, "zz");

        List<AlphabetAndNumber> aList = new ArrayList<>(Arrays.asList(ab,cd,zz));
        Collections.sort(aList);
        System.out.println(aList);

    }
}
