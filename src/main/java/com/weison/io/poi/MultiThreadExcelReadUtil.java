package com.weison.io.poi;

import com.monitorjbl.xlsx.StreamingReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public abstract class MultiThreadExcelReadUtil<E> {

    //任务超时时间
    private final int timeout = 5;
    //任务超时时间单位
    private final TimeUnit timeUnit = TimeUnit.MINUTES;
    //读取Excel sheet索引页
    private final int sheetIndex = 0;
    //Excel读取起始行
    private final int startRow = 1;
    //处理线程数
    private final int threadNum = 10;
    //每次处理记录数
    private final int rowNum = 100;

    public abstract void process(List<E> entities);

    /**
     * 多线程Excel分批处理工具类
     *
     * @param path   String Excel路径
     * @param entity Class<E> 实体
     * @throws Exception
     */
    public void read(String path, Class<E> entity) throws Exception {
        if (!path.endsWith("xlsx")) {
            new RuntimeException("导入程序不支持Excel早期版本仅支持2007");
        }

        List<Field> fields = this.getAnnotationSortFields(entity);
        InputStream is = new FileInputStream(new File(path));

        try {
            Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(is);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            List<E> entitys = new LinkedList<>();
            ExecutorService poolProcess = Executors.newFixedThreadPool(threadNum);
            int batchIndex = 2;
            List<Future<Boolean>> results = new ArrayList<>();
            for (Row r : sheet) {
                if ((batchIndex - 1) < startRow) {
                    batchIndex++;
                    continue;
                }

                int cellIndex;
                E e = entity.newInstance();
                for (Cell c : r) {
                    cellIndex = c.getColumnIndex();
                    for (int i = 0; i < fields.size(); i++) {
                        Field filed = fields.get(i);
                        if (cellIndex == filed.getAnnotation(ExcelFieldBind.class).cellIndex()) {
                            filed.setAccessible(true);
                            filed.set(e, c.getStringCellValue());
                        }
                    }
                }
                entitys.add(e);

                if ((batchIndex % rowNum) == 0) {
                    results.add(poolProcess.submit(new ProcessThread(this, entitys)));
                    entitys = new LinkedList<E>();
                }

                batchIndex++;
            }

            if (entitys.size() > 0) {
                results.add(poolProcess.submit(new ProcessThread(this, entitys)));
            }
            int successNum = 0;
            int failNum = 0;

            for (Future<Boolean> result : results) {
                try {
                    Boolean ret = result.get(timeout, timeUnit);
                    if (ret) {
                        successNum++;
                    } else {
                        failNum++;
                    }
                } catch (TimeoutException e) {
                    log.error("异步任务执行超时", e);
                    throw e;
                }
            }
            log.info(String.format("处理成功任务数:%s,处理失败任务数:%s", successNum, failNum));
            poolProcess.shutdown();
        } finally {
            is.close();
        }
    }

    private List<Field> getAnnotationSortFields(Class<E> cls) throws Exception {
        Field[] fields = this.getFields(cls);
        List<Field> fieldList = new ArrayList();
        for (Field field : fields) {
            ExcelFieldBind excelFieldBind = field.getAnnotation(ExcelFieldBind.class);
            if (null != excelFieldBind) {
                fieldList.add(field);
            }
        }
        return fieldList;
    }

    private Field[] getFields(Class<E> e) {
        Field[] fields = e.getDeclaredFields();
        int clsLevel = 5;
        Class<?> superCls = e.getSuperclass();
        while (clsLevel-- > 0) {
            if (Object.class != superCls) {
                Field[] superFileds = superCls.getDeclaredFields();
                fields = this.concat(fields, superFileds);
                superCls = superCls.getSuperclass();
            } else {
                break;
            }
        }
        return fields;
    }

    private Field[] concat(Field[] a, Field[] b) {
        final int ale = a.length;
        final int ble = b.length;
        if (ale == 0) {
            return b;
        }
        if (ble == 0) {
            return a;
        }
        final Field[] result = (Field[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), ale + ble);
        System.arraycopy(a, 0, result, 0, ale);
        System.arraycopy(b, 0, result, ale, ble);
        return result;
    }
}
