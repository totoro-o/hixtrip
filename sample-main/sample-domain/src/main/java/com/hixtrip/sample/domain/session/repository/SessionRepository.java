package com.hixtrip.sample.domain.session.repository;

import com.hixtrip.sample.domain.session.model.SessionInfo;

public interface SessionRepository {

    /**
     * 获取当前登录信息
     */
    public SessionInfo getSessionInfo();
}
