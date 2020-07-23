package com.weison.io.poi;

import java.util.List;
import java.util.concurrent.Callable;

public class ProcessThread<E> implements Callable<Boolean> {
    private MultiThreadExcelReadUtil<E> cls;
    private List<E> entities;

    public ProcessThread(MultiThreadExcelReadUtil<E> cls, List<E> entities) {
        this.cls = cls;
        this.entities = entities;
    }

    @Override
    public Boolean call() {
        this.cls.process(this.entities);
        entities.clear();
        return true;
    }
}