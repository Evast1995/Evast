package com.evast.evastcore.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.evast.evastcore.R;
import com.evast.evastcore.widget.ErrorLayout;
import com.pnikosis.materialishprogress.ProgressWheel;


/**
 * Created by 72963 on 2015/10/28.
 */
public abstract class BaseFragment extends Fragment {
    private View rootView;
    private RelativeLayout mainBody;
    private ProgressWheel progressWheel;
    private ErrorLayout errorLayout;
    /**
     * 获取齿轮类变量
     * @return
     */
    protected ProgressWheel getProgressWheel() {
        return progressWheel;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.core_fragment_base,container,false);
        mainBody = (RelativeLayout) baseView.findViewById(R.id.base_mainbody);
        progressWheel = (ProgressWheel) baseView.findViewById(R.id.base_wheel_loading);
        errorLayout = (ErrorLayout) baseView.findViewById(R.id.base_errorbody);
        if(rootView == null){
            rootView = getRootView(inflater, container, savedInstanceState);
            init(rootView);
        }
        else{
            ViewGroup parentView = (ViewGroup) rootView.getParent();
            if(parentView!=null){
                parentView.removeAllViewsInLayout();
            }
        }
        mainBody.addView(rootView);
        return baseView;
    }

    /**
     * 获取错误面板的对象
     * @return
     */
    protected ErrorLayout getErrorLayout() {
        return errorLayout;
    }

    /**
     * 获取根视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化
     */
    protected abstract void init(View rootView);

}
