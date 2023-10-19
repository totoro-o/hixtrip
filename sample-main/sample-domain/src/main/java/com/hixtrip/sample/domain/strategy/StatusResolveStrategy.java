package com.hixtrip.sample.domain.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * <p> 状态处理策略抽象接口，
 * 定义基本状态处理策略模式标准；
 *
 * @author airness
 * @since 2023/10/17 21:38
 */
public interface StatusResolveStrategy<DO> {

    @Getter
    @AllArgsConstructor
    enum ResolveResult {
        RESOLVED(1),
        NON_MATCHES(2),
        FAILED(0)
        ;
        private final int status;


        /**
         * 判断策略是否处理成功
         * @return 判断是否该策略处理成功
         */
        public boolean resolved() {
            return this.status == RESOLVED.status;
        }
    }


    /**
     * 策略判断逻辑；
     * 判断是否响应此策略的处理
     * @param dto 待判断实体
     * @return true表示应当处理此状态
     */
    boolean shouldResolve(@NotNull DO dto);

    /**
     * 策略处理逻辑
     * @return 略处理状态枚举
     */
    @NotNull ResolveResult resolve(DO dto) throws Exception;
}
