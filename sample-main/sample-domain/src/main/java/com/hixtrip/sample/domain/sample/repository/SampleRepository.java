package com.hixtrip.sample.domain.sample.repository;

import com.hixtrip.sample.domain.sample.model.Sample;

/**
 * 这是一个示例，领域层定义接口，基础设施层实现具体查询方式，如查数据库、缓存、调用第三方SDK等
 */
public interface SampleRepository {
    Sample test();
}
