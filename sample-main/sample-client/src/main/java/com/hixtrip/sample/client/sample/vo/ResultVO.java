package com.hixtrip.sample.client.sample.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultVO {
    private Boolean success;
    private String code;
    private String msg;

    public ResultVO success(String msg) {
        this.success = true;
        this.code = "200";
        this.msg = msg;
        return this;
    }

    public ResultVO failure(String msg) {
        this.success = false;
        this.code = "400";
        this.msg = msg;
        return this;
    }
}
