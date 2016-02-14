package com.example.hjiang.gactelphonedemo.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;

/**
 * 自定义TabHost
 * Created by hjiang on 16-1-27.
 */
public class TabLayout extends FrameLayout implements View.OnClickListener{
    private Context context;
    private View view;
    private LinearLayout tabLayout;
    /** 滑动块*/
    private ImageView slipBlock;
    /** tab 的监听器 v.getTag 可以获取tab 的position*/
    private OnClickListener onClickListener;
    /** 有多少个tab*/
    private int length = 0;
    private int width = 0;
    /** 滑块第一个位置的x,y坐标*/
    private int startX = 0;
    private int startY = 0;
    private LinearLayout containLayout;

    public TabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }
    public TabLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init(){
        view =  LayoutInflater.from(context).inflate(R.layout.tabhost_layout,this,true);
        tabLayout = (LinearLayout) view.findViewById(R.id.tab_layout);
        slipBlock = (ImageView) view.findViewById(R.id.slip_block);
        containLayout = (LinearLayout) findViewById(R.id.container);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        /** 计算滑块的长度*/
        width = tabLayout.getMeasuredWidth();
        if(length != 0) {
            ViewGroup.LayoutParams layoutParams = slipBlock.getLayoutParams();
            width = width / length;
            layoutParams.width = width;
            slipBlock.setLayoutParams(layoutParams);
            startX = (int) slipBlock.getX();
            startY = (int) slipBlock.getY();
        }
        super.onWindowFocusChanged(hasWindowFocus);
    }

    /**
     * 添加Tab
     * @param arraryString
     */
    public void addTabs(String[] arraryString){
        for(int i=0;i<arraryString.length;i++){
            createTab(arraryString[i],i);
        }
        length = arraryString.length;
    }
    public void addTabs(int arrayResId){
        String[] arrayString = getResources().getStringArray(arrayResId);
        addTabs(arrayString);
    }

    /**
     * 创建tab
     * @param tabStr
     * @param position
     */
    private void createTab(String tabStr,int position){
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0,LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        textView.setLayoutParams(layoutParams);
        textView.setText(tabStr);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setOnClickListener(this);
        textView.setClickable(true);
        textView.setBackgroundResource(R.drawable.text_bg);
        textView.setTag(position);
        tabLayout.addView(textView);
    }

    /**
     * 对外提供每个Tab的onClick事件 通过 v.getTag 能够获取每个tab对应的position
     * @param onClickListener
     */
    public void setTabListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    /**
     * 对外提供tab 改变位置的方法
     * @param position　移动到哪一个位子
     * @param precentage　移动的百分比
     */
    public void setCurrentTab(int position,float precentage){
        if(precentage == 0){
            return;
        }
        int rightOffset = (int) ((position * width)*precentage);
        containLayout.scrollTo(startX -rightOffset, startY);
    }

    @Override
    public void onClick(View v) {
        onClickListener.onClick(v);
    }
}
