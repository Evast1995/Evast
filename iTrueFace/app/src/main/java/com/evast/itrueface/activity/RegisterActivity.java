package com.evast.itrueface.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.evast.evastcore.core.BaseActivity;
import com.evast.evastcore.util.other.L;
import com.evast.evastcore.xmpp.XmlppManager;
import com.evast.itrueface.R;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 72963 on 2015/12/18.
 */
public class RegisterActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void init() {
        final EditText userNameEdit = (EditText) findViewById(R.id.username_edit);
        final EditText passWordEdit = (EditText) findViewById(R.id.password_edit);
        final EditText phoneEdit = (EditText) findViewById(R.id.phone_edit);
        final EditText emailEdit = (EditText) findViewById(R.id.email_edit);
        final EditText addressEdit = (EditText) findViewById(R.id.address_edit);
        final EditText authCodeEdit = (EditText) findViewById(R.id.authcode_eidt);
        Button authCodeBut = (Button) findViewById(R.id.authcode_but);
        Button registerBut = (Button) findViewById(R.id.register_but);
        authCodeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCode();
            }
        });
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = userNameEdit.getText().toString();
                String passwordStr = passWordEdit.getText().toString();
                String phoneStr = phoneEdit.getText().toString();
                String emailStr = emailEdit.getText().toString();
                String addressStr = addressEdit.getText().toString();
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", usernameStr);
                map.put("password", passwordStr);
                map.put("phone", phoneStr);
                map.put("email", emailStr);
                map.put("address", addressStr);
                regsiter(map);
            }
        });
    }

    /**
     * 注册
     */
    public void regsiter(final Map<String,String> map){
        new AsyncTask<String, Integer, IQ>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected IQ doInBackground(String... params) {
                return XmlppManager.getInstance().register(map);
            }

            @Override
            protected void onPostExecute(IQ iq) {
                if(iq == null){
                    showToast("服务器连接错误");
                }else if (iq.getType() == IQ.Type.ERROR){
                    L.i(iq.toXML());
                    if (iq.getError().toString().equalsIgnoreCase("conflict(409)")){
                        showToast("账号已存在");
                    }else{
                        showToast("注册失败");
                    }
                }else if (iq.getType() == IQ.Type.RESULT){
                    showToast("注册成功");
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        }.execute();
    }


    public void requestCode(){

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                Packet packet = XmlppManager.getInstance().getAuthCode();
                XmlppManager.getInstance().getConnection().sendPacket(packet);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();

    }
}
