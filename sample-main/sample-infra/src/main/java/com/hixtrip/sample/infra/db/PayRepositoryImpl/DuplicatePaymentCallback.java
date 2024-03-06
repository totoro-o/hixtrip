package com.hixtrip.sample.infra.db.PayRepositoryImpl;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.repository.PayRepository;

/**
 * @author HuYuDe
 */
@Component
@Qualifier("duplicate")
public class DuplicatePaymentCallback implements PayRepository {
    @Autowired
    private OrderDomainService orderDomainService;
    /**
     * 重复支付回调策略
     */
    @Override
    public void handleCallback(CommandPay commandPay) {
        // 处理重复支付的逻辑
    }
}
