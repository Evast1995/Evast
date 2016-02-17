package com.hchen.walkworld.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.hchen.walkworld.R;

/**
 * Created by hjiang on 16-2-17.
 */
public class NavigationBar extends FrameLayout{
    private Context context;
    private float textSize;
    private float imageSize;
    public NavigationBar(Context context) {
        super(context);
        this.context =context;
        initView();
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
        initView();
        initStylalbe(attrs);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context =context;
        initView();
        initStylalbe(attrs);
    }

    /**
     * 初始化视图
     */
    private void initView(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.navigationbar_layout, this, true);
    }

    /**
     * 初始化属性
     */
    private void initStylalbe(AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.NavigationBar);
        textSize = typedArray.getDimension(R.styleable.NavigationBar_text_size,30);
        imageSize = typedArray.getDimension(R.styleable.NavigationBar_image_size,30);
    }

    /**
     * 添加底部tab
     * @param arrays
     * @param imageIds
     */
    public void addTab(String[] arrays,int[] imageIds){
        if(arrays.length !=imageIds.length){
            Exception exception = new Exception("text arrays must equal image arrays length!!!");
            exception.printStackTrace();
            return;
        }
        for(int i=0;i<arrays.length;i++){

        }
    }

    /**
     * 添加底部tab
     * @param arrayId
     * @param imageIds
     */
    public void addTab(int arrayId,int[] imageIds){
        String[] arrays = context.getResources().getStringArray(arrayId);
        addTab(arrays,imageIds);
    }
}
