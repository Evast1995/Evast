package com.example.hjiang.gactelphonedemo.util;

import android.content.Context;

import com.base.module.call.account.CallAccount;
import com.base.module.call.account.CallAccountManager;

import java.util.List;

/**
 * 与当前账号相关的操作即与CallAccountManager相关
 * Created by hjiang on 16-1-8.
 */
public class AccountUtils {
    private static CallAccountManager accountManager;
    private AccountUtils(Context context){
        accountManager = (CallAccountManager)context.getSystemService(Context.CALL_ACCOUNT_SERVICE);
    };
    private static AccountUtils accountUtils;
    public synchronized static AccountUtils getInstance(Context context){
        if(accountUtils ==null){
            accountUtils = new AccountUtils(context);
        }
        return accountUtils;
    }

    /**
     * 获取当前话机所有账号
     * @return
     */
    public List<CallAccount> getAllAccounts(){
        return accountManager.getAllAccounts();
    }

    /**
     * 获取当前话机所有可用账号
     * @return
     */
    public List<CallAccount> getAllUsableCallAccounts(){
        return accountManager.getAllUsableCallAccounts();
    }



}
