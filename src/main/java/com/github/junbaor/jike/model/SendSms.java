package com.github.junbaor.jike.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SendSms {

    private boolean success;
    private DataBean data;

    @NoArgsConstructor
    @Data
    public static class DataBean {
        private String action;
    }

}
