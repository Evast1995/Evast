package com.evast.itrueface.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evast.evastcore.core.BaseActivity;
import com.evast.evastcore.widget.TemplateTitle;
import com.evast.itrueface.R;
import com.evast.itrueface.adapter.NavigationAdapter;
import com.evast.itrueface.fragment.FindFragment;
import com.evast.itrueface.fragment.HomeFragment;
import com.evast.itrueface.fragment.MeFragment;
import com.evast.itrueface.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final int HOME_ID = 0;
    private static final int ME_ID = 3;
    private static final int FIND_ID = 1;
    private static final int MESSAGE_ID = 2;

    private RelativeLayout homeNavigation;
    private ImageView homeImage;
    private TextView homeText;

    private RelativeLayout findNavigation;
    private ImageView findImage;
    private TextView findText;

    private RelativeLayout messageNavigation;
    private ImageView messageImage;
    private TextView messageText;


    private RelativeLayout meNavigation;
    private ImageView meImage;
    private TextView meText;

    private ViewPager viewPager;
    private TemplateTitle titleView;

    List<Fragment> fragments = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setIsTemplate(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {
        initView();
        fragments.add(new HomeFragment());
        fragments.add(new FindFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MeFragment());
        FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new NavigationAdapter(fragmentManager, fragments));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case HOME_ID: {
                        setSelected(HOME_ID);
                        break;
                    }
                    case ME_ID: {
                        setSelected(ME_ID);
                        break;
                    }
                    case MESSAGE_ID: {
                        setSelected(MESSAGE_ID);
                        break;
                    }
                    case FIND_ID: {
                        setSelected(FIND_ID);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化视图相关
     */
    private void initView(){
        titleView = (TemplateTitle) findViewById(R.id.main_title);
        homeNavigation = (RelativeLayout) findViewById(R.id.home_nagivation);
        homeImage = (ImageView) findViewById(R.id.home_image);
        homeText = (TextView) findViewById(R.id.home_text);

        findNavigation = (RelativeLayout) findViewById(R.id.find_nagivation);
        findImage = (ImageView) findViewById(R.id.find_image);
        findText = (TextView) findViewById(R.id.find_text);

        messageNavigation = (RelativeLayout) findViewById(R.id.message_nagivation);
        messageImage = (ImageView) findViewById(R.id.message_image);
        messageText = (TextView) findViewById(R.id.message_text);

        meNavigation = (RelativeLayout) findViewById(R.id.me_nagivation);
        meImage = (ImageView) findViewById(R.id.me_image);
        meText = (TextView) findViewById(R.id.me_text);

        viewPager = (ViewPager) findViewById(R.id.viewpage);

        setHomeTitleView();
       

        homeNavigation.setOnClickListener(this);
        findNavigation.setOnClickListener(this);
        messageNavigation.setOnClickListener(this);
        meNavigation.setOnClickListener(this);

        setSelected(HOME_ID);
    }

    /**
     * 设置消息页面的标题
     */
    private void setMessageTitleView(){
        titleView.setTitleName("");
        titleView.setleftTextVisibility(View.GONE);
        titleView.setrightTextVisibility(View.GONE);
        titleView.setLeftTextRightImageVisibility(true);
        titleView.setCenterButtonVisibility(true);
        titleView.setMesButOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragmentContent(R.id.base_mainbody,new MessageFragment());
            }
        });
//        titleView
    }
    
    /** 设置HOME标题视图*/
    private void setHomeTitleView(){
        titleView.setTitleName(R.string.home);
        titleView.setTitleTextColor(R.color.black);
        titleView.setleftTextText(R.string.city);
        titleView.setLeftTextColor(R.color.navigation_blue);
        titleView.setrightTextText(R.string.search);
        titleView.setRightTextColor(R.color.navigation_blue);
        titleView.setLeftTextRightImage(R.mipmap.arrows);
        titleView.setCenterButtonVisibility(false);
    }

    /** 设置fiND标题视图*/
    private void setFindTitleView(){
        titleView.setTitleName(R.string.find);
        titleView.setTitleTextColor(R.color.black);
        titleView.setleftTextText(R.string.city);
        titleView.setLeftTextColor(R.color.navigation_blue);
        titleView.setrightTextText(null);
        titleView.setLeftTextRightImage(R.mipmap.arrows);
        titleView.setCenterButtonVisibility(false);
    }

    /**
     * 设置Me的标题视图
     */
    private void setMeTitleView(){
        titleView.setTitleName(R.string.me);
        titleView.setTitleTextColor(R.color.black);
        titleView.setleftTextText(null);
        titleView.setrightTextText(null);
        titleView.setLeftTextRightImageVisibility(true);
        titleView.setCenterButtonVisibility(false);
    }
    private void setSelected(int changeId){
        setNoSelect();
        switch (changeId){
            case HOME_ID:{
                homeImage.setSelected(true);
                homeText.setTextColor(getResources().getColor(R.color.navigation_blue));
                setHomeTitleView();
                break;
            }
            case ME_ID:{
                meImage.setSelected(true);
                meText.setTextColor(getResources().getColor(R.color.navigation_blue));
                setMeTitleView();
                break;
            }
            case FIND_ID:{
                findImage.setSelected(true);
                findText.setTextColor(getResources().getColor(R.color.navigation_blue));
                setFindTitleView();
                break;
            }
            case MESSAGE_ID:{
                messageImage.setSelected(true);
                messageText.setTextColor(getResources().getColor(R.color.navigation_blue));
                setMessageTitleView();
                break;
            }
        }
    }

    /**
     * 在切换底部导航条时，先将其全部设置为不选中，后再将切换的设置为选中状态
     */
    private void setNoSelect(){
        findImage.setSelected(false);
        findText.setTextColor(getResources().getColor(R.color.navigation_brown));
        meImage.setSelected(false);
        meText.setTextColor(getResources().getColor(R.color.navigation_brown));
        homeImage.setSelected(false);
        homeText.setTextColor(getResources().getColor(R.color.navigation_brown));
        messageImage.setSelected(false);
        messageText.setTextColor(getResources().getColor(R.color.navigation_brown));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_nagivation:{
                setSelected(HOME_ID);
                viewPager.setCurrentItem(HOME_ID);
                break;
            }
            case R.id.me_nagivation:{
                setSelected(ME_ID);
                viewPager.setCurrentItem(ME_ID);
                break;
            }
            case R.id.find_nagivation:{
                setSelected(FIND_ID);
                viewPager.setCurrentItem(FIND_ID);
                break;
            }
            case R.id.message_nagivation:{
                setSelected(MESSAGE_ID);
                viewPager.setCurrentItem(MESSAGE_ID);
                break;
            }
        }
    }
}
