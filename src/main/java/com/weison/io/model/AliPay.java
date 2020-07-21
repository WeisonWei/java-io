package com.weison.io.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class AliPay {

    /**
     * 状态 0 无效 1有效
     */
    private Integer status;
    /**
     * 业务线 0 1 2
     */
    private Integer businessLine;
    /**
     * 环境 0:dev 1:test 2:gray 3:pro
     */
    private Integer env;
    /**
     * appid
     */
    private String appId;
    /**
     * 阿里支付应用私钥
     */
    private String privateKey;
    /**
     * 阿里支付公钥
     */
    private String aliPayPublicKey;
    /**
     * 字符集
     */
    private String charset;

}
