package com.example.hjiang.gactelphonedemo.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;

/**
 * 图文混排的一种常用格式（即旁边一张图片 后面两个文本）
 * Created by hjiang on 16-1-8.
 */
public class ImageOrTextMixed extends FrameLayout{
    private Context context;
    private ImageView imageView;
    private TextView textOneView;
    private TextView textTwoView;
    /** 是否选中 默认为false*/
    private Boolean isSelection = false;

    public ImageOrTextMixed(Context context) {
        super(context);
        this.context =context;
        init();
    }

    public ImageOrTextMixed(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
        init();
        setAttr(attrs);

    }

    public ImageOrTextMixed(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context =context;
        init();
        setAttr(attrs);

    }

    private void init(){
        View view = LayoutInflater.from(context).inflate(R.layout.imageortext_layout,this,true);
        imageView = (ImageView) view.findViewById(R.id.template_image);
        textOneView = (TextView) view.findViewById(R.id.text_one);
        textTwoView = (TextView) view.findViewById(R.id.text_two);

    }

    /**
     * 设置自定义控件的属性值
     * @param attrs
     */
    private void setAttr(AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.imageOrTextMixed_attr);

        int imageSrc = a.getResourceId(R.styleable.imageOrTextMixed_attr_image_src, R.drawable.drop_down_box_bg);
        int textOneStr = a.getResourceId(R.styleable.imageOrTextMixed_attr_text_one_text, R.string.default_null);
        int textTwoStr = a.getResourceId(R.styleable.imageOrTextMixed_attr_text_two_text, R.string.default_null);
        isSelection = a.getBoolean(R.styleable.imageOrTextMixed_attr_selection,false);
        imageView.setImageResource(imageSrc);
        textOneView.setText(textOneStr);
        textTwoView.setText(textTwoStr);

        a.recycle();
    }

    /**
     * 对外提供设置图片背景方法
     * @param resId
     */
    public void setImageBackground(int resId){
        imageView.setImageResource(resId);
    }

    /**
     * 对外提供textone的文本方法
     * @param message
     */
    public void setTextOne(String message){
        textOneView.setText(message);
    }
    /**
     * 对外提供texttwo的文本方法
     * @param message
     */
    public void setTextTwo(String message){
        textTwoView.setText(message);
    }

    /**
     * 对外提供是否选中的方法
     * @param isSelection
     */
    public void setSelection(Boolean isSelection){
        this.isSelection = isSelection;
        if(isSelection == true){
            this.setBackgroundResource(R.color.blue);
        }
    }
}
