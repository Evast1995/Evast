package com.example.hjiang.gactelphonedemo.weight;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-7.
 */
public class DelEdit extends FrameLayout{
    private Context context;
    private EditText editTv;
    private ImageView delIcon;
    private LinearLayout textViewLayout;
    private RelativeLayout delEditLayout;
    private HorizontalScrollView scrollView;

    public DelEdit(Context context) {
        super(context);
        this.context =context;
        init();
    }

    public DelEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
        init();
    }

    public DelEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context =context;
        init();
    }


    private void init(){
        View view =LayoutInflater.from(context).inflate(R.layout.edit_layout,this,true);
        editTv = (EditText) view.findViewById(R.id.del_edit);
        delIcon = (ImageView) view.findViewById(R.id.del_icon);
        textViewLayout = (LinearLayout) view.findViewById(R.id.textview_layout);
        scrollView = (HorizontalScrollView) view.findViewById(R.id.scroll_view);
        delEditLayout = (RelativeLayout) view.findViewById(R.id.deledit_body);
        delCharInEdit();
    }

    /**
     * 对外提供一个设置背景的方法
     */
    public void setEditBackgound(int resId){
        delEditLayout.setBackgroundResource(resId);
    }

    /**
     * 对外提供一个添加文本视图的方法
     * @param phoneStr
     */
    public void setAddTextView(String phoneStr){
        editTv.setText("");
        final TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 0, 0, 0);

        textView.setLayoutParams(layoutParams);
        textView.setText(phoneStr);
        textView.setTextColor(context.getResources().getColor(R.color.white));
        textView.setBackgroundResource(R.mipmap.number_box_launcher_default);
        textView.setGravity(Gravity.CENTER);

        /** 点击视图删除*/
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLayout.removeView(textView);
                setScrollViewLayoutParams();
            }
        });
        textViewLayout.addView(textView);
        handler.post(new Runnable() {
            @Override
            public void run() {
                setScrollViewLayoutParams();
            }
        });

    }
    Handler handler = new Handler();


    /**
     * 设置当scrollview的长度大于某个值时 使它设置marginleft 当它小于这个值的时候使它去掉marginleft
     */
    private void setScrollViewLayoutParams(){
        RelativeLayout.LayoutParams  scrolLayoutParams = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
        if(scrollView.getWidth()>=200){
            scrolLayoutParams.setMargins(0, 0, 50, 0);
            scrollView.setLayoutParams(scrolLayoutParams);
            /** 滑动到最后*/
            scrollView.fullScroll(ScrollView.FOCUS_RIGHT);
        }else if(scrollView.getWidth()<200){//小于这个值的时候editText宽度为 wrap_content scrollview 恢复marginleft为0
            scrolLayoutParams.setMargins(0,0,0,0);
            scrollView.setLayoutParams(scrolLayoutParams);
        }
    }

    /**
     * 点击删除图标删除编辑框中的一个字符
     */
    private void delCharInEdit(){
        delIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int textViewLastPositon = textViewLayout.getChildCount();
                /** 当编辑框中文本不为空的时候删除文本先*/
                if (!TextUtils.isEmpty(editTv.getText())) {
                    Editable editable = editTv.getText();
                    int position = getCurPosition();
                    if (position != 0) {
                        editable.delete(position - 1, position);
                    }
                } else if (textViewLastPositon != 0) {
                    textViewLayout.removeViewAt(textViewLastPositon - 1);
                }
                setScrollViewLayoutParams();
            }
        });
    }

    /**
     * 对外提供清空编辑框的方法
     */
    public void removeEditText(){
        textViewLayout.removeAllViews();
        editTv.setText("");
        setScrollViewLayoutParams();
    }
    /**
     * 获取光标位置
     * @return
     */
    private int getCurPosition(){
        return editTv.getSelectionStart();
    }

    /**
     * 对外提供添加数据在EditText上的方法
     */
    public void addString(String ch){
        String str;
        str = editTv.getText().toString() + ch;
        editTv.setText(str);
        editTv.setSelection(str.length());
    }

    /**
     * 提供　一个获取edit文本的方法
     * @return
     */
    public String getEditText(){

        return editTv.getText().toString();
    }

    /**
     * 提供一个添加编辑框变化事件的方法
     * @param watcher
     */
    public void addTextChangedListener(TextWatcher watcher){
        editTv.addTextChangedListener(watcher);
    }

    /**
     * 获取编辑框中文本图片的字符串
     * @return
     */
    public List<String> getImagesText(){
        List<String> list = new ArrayList<String>();
        /** 最后一个的位子*/
        int position = textViewLayout.getChildCount();
        while (position>0){
            TextView textView = (TextView) textViewLayout.getChildAt(position-1);
            list.add(textView.getText().toString());
            position--;
        }
        return list;
    }
}
