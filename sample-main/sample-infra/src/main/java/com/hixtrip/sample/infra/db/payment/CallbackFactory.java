package com.hixtrip.sample.infra.db.payment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
//构造一个bean工厂
public final class CallbackFactory {

        public CallbackFactory() {
        }

        public static Map<String , CallbackService> map = new ConcurrentHashMap<>();

        //遍历CallbackService类型bean，以订单状态为key放入map
        static {
            Map<String, CallbackService> beansOfType = ApplicationContextHelper.getBeansOfType(CallbackService.class);
            for(Map.Entry<String, CallbackService> entry: beansOfType.entrySet()){
                CallbackService callbackService = entry.getValue();
                map.put(callbackService.getPayStatus(), callbackService);
            }
        }

        public static CallbackService getPayStatus(String payStatus){
            return map.get(payStatus);
        }



}
