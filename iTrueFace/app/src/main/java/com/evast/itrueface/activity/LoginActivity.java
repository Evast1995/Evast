package com.evast.itrueface.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.evast.evastcore.core.BaseActivity;
import com.evast.evastcore.util.other.L;
import com.evast.evastcore.xmpp.XmlppManager;
import com.evast.itrueface.R;

import org.jivesoftware.smack.XMPPException;

/**
 * Created by 72963 on 2015/12/18.
 */
public class LoginActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void init() {

        final EditText userNameEdit = (EditText) findViewById(R.id.username_edit);
        final EditText passWordEdit = (EditText) findViewById(R.id.password_edit);
        Button logout = (Button) findViewById(R.id.register_but);
        Button loginBut = (Button) findViewById(R.id.login_but);
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = userNameEdit.getText().toString();
                String passwordStr = passWordEdit.getText().toString();
                login("admin", "123456");
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }


    private void login(final String username, final String password){
        new AsyncTask<String, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                Boolean isLogin = null;
                try {
                    isLogin = XmlppManager.getInstance().login(username,password);
                } catch (XMPPException e) {
                    e.printStackTrace();
                    isLogin = false;
                }
                return isLogin;
            }
            @Override
            protected void onPostExecute(Boolean isLogin) {
                super.onPostExecute(isLogin);
                if(isLogin){
                    L.i("login successful");
                    showToast("successful");
                }else{
                    L.e("login failed");
                    showToast("failed");
                }
            }
        }.execute();
    }

    private void logout(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                XmlppManager.getInstance().logout();
                return null;
            }
        }.execute();

    }
}
