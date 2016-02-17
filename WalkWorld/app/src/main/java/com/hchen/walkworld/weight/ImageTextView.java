package com.hchen.walkworld.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.hchen.walkworld.R;

/**
 * Created by hjiang on 16-2-17.
 */
public class ImageTextView extends FrameLayout{
    private Context context;
    private float imageSize;
    private float textSize;
    private Drawable imageDraw;
    private String textStr;
    private
    public ImageTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        initStylable(attrs);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initStylable(attrs);
        init();
    }

    /**
     * 初始化自定义属性
     * @param attrs
     */
    private void initStylable(AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
        imageSize = typedArray.getDimension(R.styleable.ImageTextView_image_size, 30);
        textSize = typedArray.getDimension(R.styleable.ImageTextView_text_size, 20);
        imageDraw =  typedArray.getDrawable(R.styleable.ImageTextView_image_background);
        textStr = (String) typedArray.getText(R.styleable.ImageTextView_text_str);

    }
    /**
     * 初始化视图
     */
    private void init(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.navigationbar_layout,this,true);
    }
}
