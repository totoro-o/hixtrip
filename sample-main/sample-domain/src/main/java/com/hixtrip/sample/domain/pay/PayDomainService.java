package com.hixtrip.sample.domain.pay;

import com.hixtrip.sample.domain.common.SimpleRedisLock;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.strategy.StatusResolveStrategy;
import com.hixtrip.sample.domain.strategy.StatusResolveStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 支付领域服务
 * todo 不需要具体实现, 直接调用即可
 */
@Component
@RequiredArgsConstructor
public class PayDomainService {

    private final StatusResolveStrategyFactory<CommandPay, StatusResolveStrategy<CommandPay>> statusResolveStrategyFactory;
    private final SimpleRedisLock redisLock;


    /**
     * 记录支付回调结果
     * 【高级要求】至少有一个功能点能体现充血模型的使用。
     */
    public void payRecord(CommandPay commandPay) {
        redisLock.lock("order:" + commandPay.getOrderId(), Duration.ofSeconds(1), () -> {
            statusResolveStrategyFactory.resolve(commandPay);
        }, () -> {
            // 加锁失败处理，可记录到人工处理队列中，进行人工补偿
        });
    }
}
