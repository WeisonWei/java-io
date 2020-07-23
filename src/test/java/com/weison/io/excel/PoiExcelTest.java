package com.weison.io.excel;

import com.weison.io.model.UserExcelPoi;
import com.weison.io.poi.MultiThreadExcelReadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PoiExcelTest {

    @Test
    public void read() throws Exception {
        MultiThreadExcelReadUtil<UserExcelPoi> util = new MultiThreadExcelReadUtil<UserExcelPoi>() {
            @Override
            public void process(List<UserExcelPoi> users) {
                if (CollectionUtils.isEmpty(users)) {
                    log.info("没有数据，不处理，时间：[{}]", LocalDateTime.now());
                    return;
                }
                log.info("------>" + users);
            }

            @Override
            public void handlerException(Exception e) {
                log.error("用户数据导入异常信息", e);
                throw new RuntimeException("读取数据出现问题", e);
            }
        };
        util.read("./wxx.xlsx", 0, 2, 10, 100, UserExcelPoi.class, 5, TimeUnit.MINUTES);
    }
}
