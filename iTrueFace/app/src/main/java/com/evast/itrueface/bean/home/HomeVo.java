package com.evast.itrueface.bean.home;

import java.util.Arrays;

/**
 * Created by 72963 on 2015/12/11.
 */
public class HomeVo {
    private String top;
    private ContextVo[] contextVos;

    public ContextVo[] getContextVos() {
        return contextVos;
    }

    public void setContextVos(ContextVo[] contextVos) {
        this.contextVos = contextVos;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "HomeVo{" +
                "contextVos=" + Arrays.toString(contextVos) +
                ", top='" + top + '\'' +
                '}';
    }
}
