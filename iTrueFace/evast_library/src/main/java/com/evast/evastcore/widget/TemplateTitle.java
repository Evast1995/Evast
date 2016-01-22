package com.evast.evastcore.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evast.evastcore.R;


/**
 * Created by 72963 on 2015/9/25.
 */
public class TemplateTitle extends FrameLayout{
    protected Resources resources;
    protected TextView leftText;
    protected TextView rightText;
    protected TextView textView;
    protected ImageView leftTextRightImage;
    protected LinearLayout centerbutLayout;
    protected Button mesBut;
    protected Button linkmanBut;
    public TemplateTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        resources = context.getResources();
        init(context);
    }
    public TemplateTitle(Context context) {
        super(context);
        resources = context.getResources();
        init(context);
    }
    public TemplateTitle(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        resources = context.getResources();
        init(context);
    }
    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View titleView = inflater.inflate(R.layout.core_templete_title, this, true);
        leftText = (TextView) titleView.findViewById(R.id.left_text);
        rightText = (TextView) titleView.findViewById(R.id.right_text);
        textView = (TextView) titleView.findViewById(R.id.title_name);
        leftTextRightImage = (ImageView) titleView.findViewById(R.id.left_text_right_image);
        centerbutLayout = (LinearLayout) titleView.findViewById(R.id.centerbut_body);
        linkmanBut = (Button) titleView.findViewById(R.id.linkman);
        mesBut = (Button) titleView.findViewById(R.id.message);
    }

    public void setMesButOnClick(View.OnClickListener onClickListener){
        mesBut.setOnClickListener(onClickListener);
    }
    public void setlinkmanButOnClick(View.OnClickListener onClickListener){
        linkmanBut.setOnClickListener(onClickListener);
    }
    public void setrightTextListener(OnClickListener rightTextListener){
        rightText.setOnClickListener(rightTextListener);
    }

    public void setleftTextListener(OnClickListener leftTextListener){
        leftText.setOnClickListener(leftTextListener);
    }


    public void setleftTextVisibility(int visibility){
        leftText.setVisibility(visibility);
    }

    public void setrightTextVisibility(int visibility){
        rightText.setVisibility(visibility);
    }


    public void setTitleName(String titleName){
        textView.setText(titleName);
    }
    public void setTitleName(int titleName){
        textView.setText(resources.getString(titleName));
    }
    public void setleftTextText(String leftTextText){
        leftText.setText(leftTextText);
    }
    public void setrightTextText(String rightTextText){
        rightText.setText(rightTextText);
    }

    public void setleftTextText(int leftTextText){
        leftText.setText(resources.getString(leftTextText));
    }
    public void setrightTextText(int rightTextText){
        rightText.setText(resources.getString(rightTextText));
    }


    public void setleftTextImage(int leftTextImage){
        leftText.setBackgroundResource(leftTextImage);
    }
    public void setrightTextImage(int rightTextImage){
        leftText.setBackgroundResource(rightTextImage);
    }

    public void setLeftTextColor(int leftTextColor){
        leftText.setTextColor(getResources().getColor(leftTextColor));
    }
    public void setRightTextColor(int rightTextColor){
        rightText.setTextColor(getResources().getColor(rightTextColor));
    }
    public void setTitleTextColor(int titleTextColor){
        textView.setTextColor(getResources().getColor(titleTextColor));
    }

    public void setLeftTextRightImage(int imageId){
        leftTextRightImage.setImageResource(imageId);
    }
    public void setLeftTextRightImageVisibility(Boolean isHide){
        if(isHide == true) {
            leftTextRightImage.setVisibility(GONE);
        }else{
            leftTextRightImage.setVisibility(VISIBLE);
        }
    }

    public void setCenterButtonVisibility(Boolean isVisibility){
        if(isVisibility){
            centerbutLayout.setVisibility(VISIBLE);
        }else{
            centerbutLayout.setVisibility(GONE);
        }
    }
}
