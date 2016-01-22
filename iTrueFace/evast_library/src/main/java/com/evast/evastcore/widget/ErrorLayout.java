package com.evast.evastcore.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.evast.evastcore.R;
/**
 * 自定义网络加载错误提示面板
 * Created by 72963 on 2015/11/2.
 */
public class ErrorLayout extends FrameLayout{
    private Context context;
    private TextView errorTv;
    private Button refreshBut;
    public ErrorLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ErrorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public ErrorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.core_errorlayout,null,false);
        errorTv = (TextView) view.findViewById(R.id.core_error_tv);
        refreshBut = (Button) view.findViewById(R.id.core_error_button);
        addView(view);
    }

    /**
     * 设置空白页面(即加载开始的白板)
     */
    public void setBlankTip(){
        errorTv.setVisibility(GONE);
        refreshBut.setVisibility(GONE);
    }

    /**
     * 与白板对应设置错误出现时显示
     */
    public void setErrorShow(){
        errorTv.setVisibility(VISIBLE);
        refreshBut.setVisibility(VISIBLE);
    }
    /**
     * 错误信息文本
     * @param errorMessageStr
     */
    public void setErrorText(String errorMessageStr){
        errorTv.setText(errorMessageStr);
    }
    public void setErrorText(int errorMessageId){
        errorTv.setText(errorMessageId);
    }

    /**
     * 刷新按钮的事件
     */
    public void setRefreshButListener(OnClickListener onClickListener){
        refreshBut.setOnClickListener(onClickListener);
    }

}
