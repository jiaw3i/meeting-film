package com.stylefeng.guns.api.alipay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jerry
 **/
@Data
public class AlipayInfoVO implements Serializable {
    private String orderId;
    private String QRCodeAddress;
}
