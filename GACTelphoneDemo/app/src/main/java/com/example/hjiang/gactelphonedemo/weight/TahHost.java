package com.example.hjiang.gactelphonedemo.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;

/**
 * 自定义TabHost
 * Created by hjiang on 16-1-27.
 */
public class TahHost extends FrameLayout{
    private Context context;
    private LinearLayout view;
    public TahHost(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public TahHost(Context context) {
        super(context);
        this.context = context;
    }

    public TahHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private void init(){
        view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.tabhost_layout,this,true);
    }

    /**
     * 添加Tab
     * @param arraryString
     */
    public void addTabs(String[] arraryString){
        for(int i=0;i<arraryString.length;i++){

        }
    }


    public void addTabs(int arrayResId){

    }

    private void createText(String tabStr){
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setText(tabStr);
        view.addView(textView);
    }


}
