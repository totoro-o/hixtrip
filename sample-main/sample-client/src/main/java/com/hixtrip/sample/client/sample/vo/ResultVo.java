package com.hixtrip.sample.client.sample.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultVo {

    private Boolean success;
    private String code;
    private String msg;

}
