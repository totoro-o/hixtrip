package com.hixtrip.sample.infra.db.payment;

public class SignCheck {
    public static boolean signCheck(String sign){
        if ("true".equals(sign)){
            return true;
        }else {
            return false;
        }
    }
}
