package com.weison.io.csv;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.weison.io.csv.filter.AliPayBillCsvFilter;
import com.weison.io.model.AliPay;
import com.weison.io.model.AliPayBill;
import com.weison.io.model.DualityTuple;
import com.weison.io.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.weison.io.utils.HttpUtil.parseUrlParam;

@Slf4j
public class AliPayBillTest {

    //阿里支付网关
    private static final String ALIPAY_OPENAPI_GATEWAY = "https://openapi.alipay.com/gateway.do";

    @Test
    public void syncBill() {
        String day = "2020-07-20";
        List<AliPay> aliPays = new ArrayList<>();
        AliPay aliPay1 = new AliPay().setEnv(3).setStatus(1);
        AliPay aliPay2 = new AliPay().setEnv(3).setStatus(1);
        aliPays.add(aliPay1);
        aliPays.add(aliPay2);
        List<DualityTuple<String, List<AliPayBill>>> sheetBills = aliPays.stream()
                .map(aliPay -> requestAliPay(aliPay, day))
                .filter(Objects::nonNull)
                .map(this::readZipFiles)
                .collect(Collectors.toList());
        buildExcel(sheetBills, day);
    }

    private DualityTuple<String, List<AliPayBill>> readZipFiles(File zipFile) {
        DualityTuple<String, List<AliPayBill>> dualityTuple = null;
        if (zipFile.exists()) {
            FileInputStream fileIs = null;
            BufferedInputStream bufferIs = null;
            ZipInputStream zipInputStream = null;
            try {
                fileIs = new FileInputStream(zipFile);
                bufferIs = new BufferedInputStream(fileIs);
                zipInputStream = new ZipInputStream(bufferIs, Charset.forName("GBK"));
                ZipEntry zipEntry;
                //遍历ZIP包中的文件
                while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                    log.info(String.format("支付宝对账--读取对账ZIP文件[文件名:%s,文件大小:%s]", zipEntry.getName(), zipEntry.getSize()));
                    //如果查找到业务明细csv则直接处理退出循环
                    if (zipEntry.getName().lastIndexOf("业务明细.csv") > 0) {
                        InputStreamReader reader = new InputStreamReader(zipInputStream, "GBK");
                        List<AliPayBill> aliPayBills = readCsv(AliPayBill.class, reader);
                        dualityTuple = new DualityTuple(zipFile.getName().replace(".zip", ""), aliPayBills);
                        reader.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileIs.close();
                    bufferIs.close();
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dualityTuple;
    }

    private File requestAliPay(AliPay aliPay, String day) {
        String appId = aliPay.getAppId();
        AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_OPENAPI_GATEWAY,
                appId,
                aliPay.getPrivateKey(),
                "json",
                aliPay.getCharset(),
                aliPay.getAliPayPublicKey(),
                "RSA2");
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizContent("{\"bill_type\":\"trade\",\"bill_date\":\"" + day + "\"}");

        try {
            AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                String fileUrl = response.getBillDownloadUrl();
                Map<String, String> params = parseUrlParam(fileUrl);
                String fileType = params.get("filetype");
                //判断文件类型是否是csv.zip格式
                if ("csv.zip".equals(fileType)) {
                    //HTTP下载对账文件
                    File file = new File(appId + ".zip");
                    HttpUtil.download(fileUrl, file);
                    return file;
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> List<T> readCsv(Class<T> clazz, InputStreamReader in) {
        ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withFilter(new AliPayBillCsvFilter())
                .withQuoteChar('\'')
                .withSkipLines(6)
                .withIgnoreLeadingWhiteSpace(true)
                .withMappingStrategy(strategy)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }

    private void buildExcel(List<DualityTuple<String, List<AliPayBill>>> sheetBills, String day) {
        createFilePath(day);
        String fileName = "./AliPay_bill_all.xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        sheetBills.stream().forEach(tuple -> buildSheet(excelWriter, tuple));
        if (excelWriter != null) {
            excelWriter.finish();
        }
    }

    private void buildSheet(ExcelWriter excelWriter, DualityTuple<String, List<AliPayBill>> tuple) {
        String appId = tuple.first;
        List<AliPayBill> sheetData = tuple.second;
        WriteSheet writeSheet = EasyExcel.writerSheet(appId + "_账单明细")
                .head(AliPayBill.class).build();
        excelWriter.write(sheetData, writeSheet);
        log.debug("-buildSheet-end:" + sheetData);
    }

    private void createFilePath(String day) {
        String fileName = "./" + day;
        File dir = new File(fileName);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
}
