package com.weison.io.poi;

import java.util.List;
import java.util.concurrent.Callable;

public class ProcessThread<E> implements Callable<Boolean> {
    private MultiThreadExcelReadUtil<E> cls;
    private List<E> entitys;

    public ProcessThread(MultiThreadExcelReadUtil<E> cls, List<E> entitys) {
        this.cls = cls;
        this.entitys = entitys;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            this.cls.process(this.entitys);
            entitys.clear();
            return true;
        } catch (Exception e) {
            this.cls.handlerException(e);

        }
        return false;
    }
}