package com.weison.io.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 支付宝支付对账单
 */

@Data
@ToString
@Accessors(chain = true)
@ContentRowHeight(30)
@HeadRowHeight(20)
@ColumnWidth(35)
public class AliPayBill implements Serializable {
    /**
     * 支付宝交易号
     */
    @CsvBindByPosition(position = 0)
    @ColumnWidth(35)
    @ExcelProperty("支付宝交易号")
    private String alipayTxNumber;
    /**
     * 商户订单号
     */
    @CsvBindByPosition(position = 1)
    @ColumnWidth(35)
    @ExcelProperty("商户订单号")
    private String merchantOrderNo;
    /**
     * 业务类型
     */
    @CsvBindByPosition(position = 2)
    @ColumnWidth(20)
    @ExcelProperty("业务类型")
    private String businessType;
    /**
     * 商品名称
     */
    @CsvBindByPosition(position = 3)
    @ColumnWidth(20)
    @ExcelProperty("商品名称")
    private String TradeName;
    /**
     * 创建时间
     */
    @CsvBindByPosition(position = 4)
    @ColumnWidth(35)
    @ExcelProperty("创建时间")
    private String createTime;
    /**
     * 完成时间
     */
    @CsvBindByPosition(position = 5)
    @ColumnWidth(35)
    @ExcelProperty("完成时间")
    private String completeTime;
    /**
     * 门店编号
     */
    @CsvBindByPosition(position = 6)
    @ColumnWidth(15)
    @ExcelProperty("门店编号")
    private String shopNo;
    /**
     * 门店名称
     */
    @CsvBindByPosition(position = 7)
    @ColumnWidth(15)
    @ExcelProperty("门店名称")
    private String shopName;
    /**
     * 操作员
     */
    @CsvBindByPosition(position = 8)
    @ColumnWidth(15)
    @ExcelProperty("操作员")
    private String operator;
    /**
     * 终端号
     */
    @CsvBindByPosition(position = 9)
    @ColumnWidth(15)
    @ExcelProperty("终端号")
    private String terminalNo;
    /**
     * 对方账户
     */
    @CsvBindByPosition(position = 10)
    @ColumnWidth(20)
    @ExcelProperty("对方账户")
    private String counterAccount;
    /**
     * 订单金额（元)
     */
    @CsvBindByPosition(position = 11)
    //@CsvBindByName(column = "", required = false)
    @ColumnWidth(20)
    @ExcelProperty("订单金额（元）")
    private String orderAmount;
    /**
     * 商家实收（元）
     */
    @CsvBindByPosition(position = 12)
    @ColumnWidth(20)
    @ExcelProperty("商家实收（元）")
    private String businessPaidIn;
    /**
     * 支付宝红包（元）
     */
    @CsvBindByPosition(position = 13)
    @ColumnWidth(20)
    @ExcelProperty("支付宝红包（元）")
    private String alipayRedBag;
    /**
     * 集分宝（元）
     */
    @CsvBindByPosition(position = 14)
    @ColumnWidth(20)
    @ExcelProperty("集分宝（元）")
    private String setPointsBao;
    /**
     * 支付宝优惠（元）
     */
    @CsvBindByPosition(position = 15)
    @ColumnWidth(20)
    @ExcelProperty("支付宝优惠（元）")
    private String alipayDiscount;
    /**
     * 商家优惠（元）
     */
    @CsvBindByPosition(position = 16)
    @ColumnWidth(20)
    @ExcelProperty("商家优惠（元）")
    private String merchantDiscount;
    /**
     * 券核销金额（元）
     */
    @CsvBindByPosition(position = 17)
    @ColumnWidth(20)
    @ExcelProperty("券核销金额（元）")
    private String couponWriteOffAmount;

    /**
     * 券名称
     */
    @CsvBindByPosition(position = 18)
    @ColumnWidth(20)
    @ExcelProperty("券名称")
    private String couponName;
    /**
     * 商家红包消费金额（元）
     */
    @CsvBindByPosition(position = 19)
    @ColumnWidth(25)
    @ExcelProperty("商家红包消费金额（元）")
    private String businessRedBagConsumeAmount;
    /**
     * 卡消费金额（元）
     */
    @CsvBindByPosition(position = 20)
    @ColumnWidth(25)
    @ExcelProperty("卡消费金额（元）")
    private String cardConsumeAmount;

    /**
     * 退款批次号/请求号
     */
    @CsvBindByPosition(position = 21)
    @ColumnWidth(25)
    @ExcelProperty("退款批次号/请求号")
    private String refundNoOrReqNo;

    /**
     * 服务费（元）
     */
    @CsvBindByPosition(position = 22)
    @ColumnWidth(20)
    @ExcelProperty("服务费（元）")
    private String serviceCharge;
    /**
     * 分润（元）
     */
    @CsvBindByPosition(position = 23)
    @ColumnWidth(20)
    @ExcelProperty("分润（元）")
    private String shareBenefit;
    /**
     * 备注
     */
    @CsvBindByPosition(position = 24)
    @ColumnWidth(20)
    @ExcelProperty("备注")
    private String memo;
}
