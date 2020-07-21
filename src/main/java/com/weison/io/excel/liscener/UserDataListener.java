package com.weison.io.excel.liscener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.weison.io.model.User;

public class UserDataListener extends AnalysisEventListener<User> {

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        System.out.println("--" + user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("--");
    }
}
