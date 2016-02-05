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
import com.example.hjiang.gactelphonedemo.util.OtherUtils;

/**
 * 自定义TabHost
 * Created by hjiang on 16-1-27.
 */
public class TabLayout extends FrameLayout{
    private Context context;
    private View view;
    private LinearLayout tabLayout;
    private ImageView slipBlock;
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
    }

    /**
     * 添加Tab
     * @param arraryString
     */
    public void addTabs(String[] arraryString){
        for(int i=0;i<arraryString.length;i++){
            createText(arraryString[i]);
        }
        int width = OtherUtils.getWidth(context);
        ViewGroup.LayoutParams layoutParams= slipBlock.getLayoutParams();
        layoutParams.width = width/arraryString.length;
        slipBlock.setLayoutParams(layoutParams);
    }


    public void addTabs(int arrayResId){
        String[] arrayString = getResources().getStringArray(arrayResId);
        addTabs(arrayString);

    }

    private void createText(String tabStr){
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0,LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        layoutParams.setMargins(20, 0, 20, 0);
        textView.setLayoutParams(layoutParams);
        textView.setText(tabStr);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        tabLayout.addView(textView);


    }



}
