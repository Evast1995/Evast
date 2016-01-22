package com.evast.itrueface.weight;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.evast.evastcore.R;
import com.evast.itrueface.util.ImageLoadUtil;


/**
 * Created by 72963 on 2015/12/11.
 */
public class ImageScorll extends FrameLayout{
    private ViewPager viewPager;
    private LinearLayout tipsBox;
    private int[] imageIds;
    private ImageView[] tips;
    private int currentPage=0;//当前展示的页码
    public ImageScorll(Context context,int[] imageIds) {
        super(context);
        this.imageIds = imageIds;
        init(context);
    }


    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View imageScroll = inflater.inflate(R.layout.core_imagepage, this, true);
        viewPager = (ViewPager) imageScroll.findViewById(R.id.image_page);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tips[currentPage].setBackgroundResource(R.mipmap.gray_point);
                currentPage = position;
                tips[position].setBackgroundResource(R.mipmap.white_point);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //存放点点的容器
        tipsBox = (LinearLayout) imageScroll.findViewById(R.id.point);
        setPoints();
    }
    private void setPoints(){
        //初始化 提示点点
        tips=new ImageView[imageIds.length];
        for(int i=0;i<tips.length;i++){
            ImageView img=new ImageView(getContext()); //实例化一个点点
            img.setLayoutParams(new LayoutParams(10,10));
            tips[i]=img;
            if(i==0)
            {
                img.setBackgroundResource(R.mipmap.white_point);//白色背景
            }
            else{
                img.setBackgroundResource(R.mipmap.gray_point);//灰色背景
            }
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            params.leftMargin=5;
            params.rightMargin=5;
            tipsBox.addView(img, params); //把点点添加到容器中
        }
        setPointOnClick();

    }

    /**
     * 设置点点的点击切换事件
     */
    private void setPointOnClick(){
        for(int i=0;i<tips.length;i++){
            final int finalI = i;
            tips[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI);
                }
            });
        }
    }
    private PagerAdapter adapter=new PagerAdapter(){
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageIds.length;
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0==arg1;
        }

        @Override
        public void destroyItem(ViewGroup container,int position,Object o){
            //container.removeViewAt(position);
        }
        //设置ViewPager指定位置要显示的view
        @Override
        public Object instantiateItem(ViewGroup container,int position){
            ImageView im=new ImageView(getContext());
            im.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            im.setImageResource(imageIds[position]);
            ImageLoadUtil.getInstance().setImageLoader(im,"http://down.tutu001.com/d/file/20101129/2f5ca0f1c9b6d02ea87df74fcc_560.jpg");
            container.addView(im);
            return im;
        }
    };

}
