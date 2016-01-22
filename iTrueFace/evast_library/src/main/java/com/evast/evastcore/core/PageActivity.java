package com.evast.evastcore.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.evast.evastcore.R;
import com.evast.evastcore.widget.Navigation;

/**
 * 底部导航条（图片加文本）形式的Activity
 * 图片ID文本和Fragent必须一一对应顺序不要错
 * Created by 72963 on 2015/12/6.
 */
public abstract class PageActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setIsTemplate(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
    }

    @Override
    protected void init() {
        LinearLayout pageBottom = (LinearLayout) findViewById(R.id.page_bottom);
        if (getFragments()!=null) {
            for (int i = 0; i < getFragments().length; i++) {
//                RelativeLayout relativeLayout = new RelativeLayout(this);
//                relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(
//                        0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
//                TextView textView = new TextView(this);
//                ImageView imageView = new ImageView(this);
//                RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                textLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
//                textView.setLayoutParams(textLayoutParams);
//                textView.setText(getTextStr()[i]);
//                textView.setId(i);
//
//                RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                imageLayoutParams.addRule(RelativeLayout.ABOVE,i);
//                imageLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
//                imageView.setLayoutParams(imageLayoutParams);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setImageResource(getImageIds()[i]);

//                relativeLayout.addView(textView);
//                relativeLayout.addView(imageView);


//                pageBottom.addView(relativeLayout);

                Navigation navigation = new Navigation(this);
                navigation.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                navigation.addNagivation(getImageIds()[i],getTextStr()[i]);
                navigation.setNagivationTextColor(R.color.white);
                pageBottom.addView(navigation);
            }
        }
    }

    /**
     * 获取底部导航条切换的Fragment数组
     * @return
     */
    protected abstract Fragment[] getFragments();

    /**
     * 获取底部导航条切换的图片ID数组
     * @return
     */
    protected abstract int[] getImageIds();

    /**
     * 获取底部导航条切换的文本id数组
     * @return
     */
    protected abstract int[] getTextStr();
}
