package OverrideEqualsAndHashCode;

import java.util.*;

public class PhoneNumber {
    private final int areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof PhoneNumber)) return false;
        PhoneNumber pn = (PhoneNumber) obj;
        return pn.areaCode == this.areaCode && pn.prefix == this.prefix && pn.lineNum == this.lineNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaCode,prefix,lineNum);
    }

    public static void main(String[] args) {
        PhoneNumber phoneNumber = new PhoneNumber(1, 2, 3);
        PhoneNumber phoneNumber1 = new PhoneNumber(1, 2, 3);
        PhoneNumber phoneNumber2 = new PhoneNumber(1, 2, 3);

        System.out.println("결과는 true  = "+phoneNumber.equals(phoneNumber1));
        System.out.println("결과는 true  = "+phoneNumber.equals(phoneNumber));
        System.out.println("결과는 모두 true = " + phoneNumber.equals(phoneNumber1) + " " + phoneNumber1.equals(phoneNumber2) + " "+ phoneNumber.equals(phoneNumber2));
        System.out.println("결과는 false  = "+phoneNumber.equals(null));

        System.out.println(phoneNumber);
        System.out.println(phoneNumber1);
        System.out.println(phoneNumber2);


        Set<PhoneNumber> set = new HashSet<>();
        set.add(phoneNumber);
        set.add(phoneNumber1);
        System.out.println(set.size());
    }
}
