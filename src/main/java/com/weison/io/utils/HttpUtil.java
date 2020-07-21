package com.weison.io.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class HttpUtil {

    public static void download(String url, File localFile) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        OutputStream out = null;
        InputStream in = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            in = entity.getContent();
            if (!localFile.exists()) {
                localFile.createNewFile();
            }
            out = new FileOutputStream(localFile);
            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength = in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }
            out.flush();
        } catch (Exception e) {
            log.error(String.format("HTTP文件下载失败[本地地址:%s,远程地址:%s]", localFile.getAbsolutePath(), url), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String TruncateUrlPage(String strURL) throws Exception {
        strURL = URLDecoder.decode(strURL, "UTF-8");
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim().toLowerCase();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    public static Map<String, String> parseUrlParam(String url) {
        Map<String, String> mapRequest = new HashMap<>(16);

        String[] arrSplit = null;
        String strUrlParam = null;
        try {
            strUrlParam = TruncateUrlPage(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }
}
