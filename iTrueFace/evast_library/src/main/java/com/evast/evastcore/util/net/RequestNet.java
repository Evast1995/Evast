package com.evast.evastcore.util.net;

import android.content.Context;
import android.view.View;

import com.evast.evastcore.util.other.L;
import com.evast.evastcore.widget.ErrorLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pnikosis.materialishprogress.ProgressWheel;

import cz.msebera.android.httpclient.Header;


/**
 * Created by 72963 on 2015/10/28.
 */
public class RequestNet {

    private Context context;
    private ProgressWheel progressWheel;
    private static AsyncHttpClient asyncHttpClient;
    private ErrorLayout errorLayout;
    private RequestParams requestParams;//请求参数
    private String url;//请求的url地址
    private Boolean isGet;//是否是get请求 True为Get请求 False为Post请求
    private static final int GET = 1;//get请求方式
    private static final int POST = 2;//post请求方式
    private ResponseResult responseResult;

    public RequestNet(Context context){
        if(asyncHttpClient == null){
            asyncHttpClient = new AsyncHttpClient();
        }
        this.context = context;
    }


    /**
     * 清除加载数据的是的效果(用于第一次加载数据后不需要加载动画)
     */
    public void removeAnim(){
        if(progressWheel!=null){
            progressWheel = null;
        }
        if(errorLayout!=null){
            errorLayout=null;
        }
    }

    class MyResponseHandler extends AsyncHttpResponseHandler {
     /**
     * 网络请求成功
     * @param statusCode
     * @param headers
     * @param responseBody
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String responseStr = new String(responseBody);
        L.i("successful statusCode:" + statusCode);
        L.i("responseStr:" + responseStr);

        /** 网络请求成功后将齿轮和白板消失掉*/
        if(progressWheel!=null) {
            progressWheel.setVisibility(View.GONE);
        }
        if(errorLayout!=null){
            errorLayout.setVisibility(View.GONE);
        }
        responseResult.successful(responseStr);
    }

    /**
     * 网络请求失败
     * @param statusCode
     * @param headers
     * @param responseBody
     * @param error
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        responseResult.exception();
        L.e("failCode statusCode:" + statusCode);
        if(progressWheel!=null) {//失败后如果有齿轮参数将其消失
            progressWheel.setVisibility(View.GONE);
        }
        if(errorLayout!=null){//失败后如果有错误面板就将其显示并且给予刷新按钮点击事件
            errorLayout.setErrorShow();
            errorLayout.setVisibility(View.VISIBLE);
            errorLayout.setRefreshButListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isGet){//如果为Get请求则再次刷新请求过Get
                        get(url,requestParams,responseResult);
                    }else{//如果是Post请求则再次刷新请求Post
                        post(url,requestParams,responseResult);
                    }
                }
            });
        }
    }
}

    /**
     * 调用post请求网络的方法(需要加载动画的方法)
     * @param url 网络地址
     * @param requestParams 请求网络参数
     * @param progressWheel 齿轮变量
     * @param errorLayout 面板变量
     */
    public void post(String url,RequestParams requestParams,ResponseResult responseResult,
                     ProgressWheel progressWheel,ErrorLayout errorLayout){
        this.errorLayout = errorLayout;
        this.progressWheel = progressWheel;
        this.responseResult = responseResult;
        requestNetWork(POST, url, requestParams);
    }
    /**
     * 调用post请求网络的方法（不需要加载动画的方法）
     * @param url 网络地址
     * @param requestParams 请求网络参数
     */
    public void post(String url,RequestParams requestParams,ResponseResult responseResult){
        this.responseResult = responseResult;
        requestNetWork(POST, url, requestParams);
    }

    /**
     * 设置齿轮
     * @param progressWheel
     */
    public void setProgressWheel(ProgressWheel progressWheel){
        this.progressWheel = progressWheel;
    }

    /**
     * 设置面板
     * @param errorLayout
     */
    public void setErrorLayout(ErrorLayout errorLayout){
        this.errorLayout = errorLayout;
    }
    /**
     * 调用get请求网络的方法(需要加载动画的方法)
     * @param url 网络地址
     * @param requestParams 请求网络参数
     * @param progressWheel 齿轮变量
     * @param errorLayout 面板变量
     */
    public void get(String url,RequestParams requestParams,ResponseResult responseResult,
                    ProgressWheel progressWheel,ErrorLayout errorLayout){
        this.errorLayout = errorLayout;
        this.progressWheel = progressWheel;
        this.responseResult = responseResult;
        requestNetWork(GET, url, requestParams);
    }
    /**
     * 调用get请求网络的方法（不需要加载动画的方法）
     * @param url 网络地址
     * @param requestParams 请求网络参数
     */
    public void get(String url,RequestParams requestParams,ResponseResult responseResult){
        this.responseResult = responseResult;
        requestNetWork(GET, url, requestParams);
    }


    /**
     * 请求网络
     * @param type 请求网络的方式GET:1 POST:2
     * @param url 请求网络的地址
     * @param requestParams 请求网络的参数
     */
    private void requestNetWork(int type,String url,RequestParams requestParams){
        /** 如果齿轮变量不为空则显示齿轮*/
        if(progressWheel!=null) {
            progressWheel.setVisibility(View.VISIBLE);
        }
        /** 如果面板变量不为空则显示空白面板*/
        if (errorLayout!=null){
            errorLayout.setBlankTip();//空白面板
            errorLayout.setVisibility(View.VISIBLE);
        }
        this.url = url;
        this.requestParams = requestParams;

        if(type == GET){//get请求网络
            asyncHttpClient.get(context, url, requestParams, new MyResponseHandler());
            this.isGet = true;
        }else if (type == POST){//post请求网络
            asyncHttpClient.post(context, url, requestParams,new MyResponseHandler());
            this.isGet = false;
        }
    }

    public interface ResponseResult{
        /**
         * 请求网络成功后的操作
         * @param response
         */
        void successful(String response);

        /**
         * 请求网络失败后的操作
         */
        void exception();
    }

}
