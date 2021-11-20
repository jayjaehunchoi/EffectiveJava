package Practice1;

import java.util.UUID;

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

public class CouponFactory {

    private static String getCouponNumber(){
        return UUID.randomUUID().toString().substring(4,23);
    }

    public static Coupon createCoupon(String userEmail){
        return new Coupon(userEmail, getCouponNumber(), false);
    }
    public static void main(String[] args) {

        Coupon coupon = CouponFactory.createCoupon("wogns0108");
    }
}

