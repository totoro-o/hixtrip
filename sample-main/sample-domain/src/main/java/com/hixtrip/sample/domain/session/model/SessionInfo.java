package com.hixtrip.sample.domain.session.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionInfo {

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 用户名
     */
    private String user_name;

    /**
     * 登录账号
     */
    private String login_name;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 机构id
     */
    private String dept_id;

    /**
     * 机构名称
     */
    private String dept_name;

}
