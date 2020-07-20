package com.weison.io.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 支付宝支付对账单实体
 */

@Data
@ToString
@Accessors(chain = true)
public class AliPayBill implements Serializable {
    /**
     * 支付宝交易号
     */
    @CsvBindByPosition(position = 0)
    //@CsvBindByName(column = "支付宝交易号", required = false)
    private String alipayTxNumber;
    /**
     * 商户订单号
     */
    @CsvBindByPosition(position = 1)
    //@CsvBindByName(column = "商户订单号", required = false)
    private String merchantOrderNo;
    /**
     * 业务类型
     */
    @CsvBindByPosition(position = 2)
    //@CsvBindByName(column = "业务类型", required = false)
    private String businessType;
    /**
     * 商品名称
     */
    @CsvBindByPosition(position = 3)
    //@CsvBindByName(column = "商品名称", required = false)
    private String TradeName;
    /**
     * 创建时间
     */
    @CsvBindByPosition(position = 4)
    //@CsvBindByName(column = "创建时间", required = false)
    private String createTime;
    /**
     * 完成时间
     */
    @CsvBindByPosition(position = 5)
    //@CsvBindByName(column = "完成时间", required = false)
    private String completeTime;
    /**
     * 门店编号
     */
    @CsvBindByPosition(position = 6)
    //@CsvBindByName(column = "门店编号", required = false)
    private String shopNo;
    /**
     * 门店名称
     */
    @CsvBindByPosition(position = 7)
    //@CsvBindByName(column = "门店名称", required = false)
    private String shopName;
    /**
     * 操作员
     */
    @CsvBindByPosition(position = 8)
    //@CsvBindByName(column = "操作员", required = false)
    private String operator;
    /**
     * 终端号
     */
    @CsvBindByPosition(position = 9)
    //@CsvBindByName(column = "终端号", required = false)
    private String terminalNo;
    /**
     * 对方账户
     */
    @CsvBindByPosition(position = 10)
    //@CsvBindByName(column = "对方账户", required = false)
    private String counterAccount;
    /**
     * 订单金额（元)
     */
    @CsvBindByPosition(position = 11)
    //@CsvBindByName(column = "订单金额（元）", required = false)
    private String orderAmount;
    /**
     * 商家实收（元）
     */
    @CsvBindByPosition(position = 12)
    //@CsvBindByName(column = "商家实收（元）", required = false)
    private String businessPaidIn;
    /**
     * 支付宝红包（元）
     */
    @CsvBindByPosition(position = 13)
    //@CsvBindByName(column = "支付宝红包（元）", required = false)
    private String alipayRedBag;
    /**
     * 集分宝（元）
     */
    @CsvBindByPosition(position = 14)
    //@CsvBindByName(column = "集分宝（元）", required = false)
    private String setPointsBao;
    /**
     * 支付宝优惠（元）
     */
    @CsvBindByPosition(position = 15)
    //@CsvBindByName(column = "支付宝优惠（元）", required = false)
    private String alipayDiscount;
    /**
     * 商家优惠（元）
     */
    @CsvBindByPosition(position = 16)
    //@CsvBindByName(column = "商家优惠（元）", required = false)
    private String merchantDiscount;
    /**
     * 券核销金额（元）
     */
    @CsvBindByPosition(position = 17)
    //@CsvBindByName(column = "券核销金额（元）", required = false)
    private String couponWriteOffAmount;

    /**
     * 券名称
     */
    @CsvBindByPosition(position = 18)
    //@CsvBindByName(column = "券名称", required = false)
    private String couponName;
    /**
     * 商家红包消费金额（元）
     */
    @CsvBindByPosition(position = 19)
    //@CsvBindByName(column = "商家红包消费金额（元）", required = false)
    private String businessRedBagConsumeAmount;
    /**
     * 卡消费金额（元）
     */
    @CsvBindByPosition(position = 20)
    //@CsvBindByName(column = "卡消费金额（元）", required = false)
    private String cardConsumeAmount;

    /**
     * 退款批次号/请求号
     */
    @CsvBindByPosition(position = 21)
    //@CsvBindByName(column = "退款批次号/请求号", required = false)
    private String refundNoOrReqNo;

    /**
     * 服务费（元）
     */
    @CsvBindByPosition(position = 22)
    //@CsvBindByName(column = "服务费（元）", required = false)
    private String serviceCharge;
    /**
     * 分润（元）
     */
    @CsvBindByPosition(position = 23)
    //@CsvBindByName(column = "分润（元）", required = false)
    private String shareBenefit;
    /**
     * 备注
     */
    @CsvBindByPosition(position = 24)
    //@CsvBindByName(column = "备注", required = false)
    private String memo;
}
