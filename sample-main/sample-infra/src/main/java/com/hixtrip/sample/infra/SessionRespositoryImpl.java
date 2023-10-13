package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.session.model.SessionInfo;
import com.hixtrip.sample.domain.session.repository.SessionRepository;
import org.springframework.stereotype.Component;

@Component
public class SessionRespositoryImpl implements SessionRepository {

    /**
     * 获取当前登录信息
     * @return
     */
    @Override
    public SessionInfo getSessionInfo() {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setUser_id("00001");
        sessionInfo.setUser_name("张三");
        sessionInfo.setLogin_name("zhangsan");
        sessionInfo.setTel("13295204657");
        sessionInfo.setDept_id("00001");
        sessionInfo.setDept_name("xxxx公司");
        return sessionInfo;

    }
}
