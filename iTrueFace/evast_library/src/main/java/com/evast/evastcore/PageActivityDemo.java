package com.evast.evastcore;

import android.support.v4.app.Fragment;

import com.evast.evastcore.core.PageActivity;
import com.evast.evastcore.test.TestFragment;

/**
 * Created by 72963 on 2015/12/6.
 */
public class PageActivityDemo extends PageActivity{

    @Override
    protected Fragment[] getFragments() {
        Fragment[] fragments = new Fragment[]{new TestFragment(),new TestFragment(),new TestFragment()};
        return fragments;
    }

    @Override
    protected int[] getImageIds() {
        int[] textStrs = new int[]{R.mipmap.icon_home,R.mipmap.icon_home,R.mipmap.icon_home};
        return textStrs;
    }

    @Override
    protected int[] getTextStr() {
        int[] textStrs = new int[]{R.string.test_text,R.string.test_text,R.string.test_text};
        return textStrs;
    }
}
