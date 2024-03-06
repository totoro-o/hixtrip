package com.hixtrip.sample.infra.db.PayRepositoryImpl;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.repository.PayRepository;

/**
 * @author HuYuDe
 */
@Component
@Qualifier("success")
public class PaymentSuccessCallback implements PayRepository {
    @Autowired
    private OrderDomainService orderDomainService;
    /**
     * 支付成功回调策略
     */
    @Override
    public void handleCallback(CommandPay commandPay) {
        // 处理支付成功的逻辑
        orderDomainService.orderPaySuccess(commandPay);
    }
}
