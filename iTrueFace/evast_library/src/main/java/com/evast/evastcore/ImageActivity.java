package com.evast.evastcore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.evast.evastcore.core.BaseActivity;
import com.evast.evastcore.util.other.L;

import java.io.File;

/**
 * Created by 72963 on 2015/12/3.
 */
public class ImageActivity extends BaseActivity{
    private  final int CARMERA_REQUEST_CODE = 101;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
    }

    @Override
    protected void init() {
        Button button = (Button) findViewById(R.id.carmera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String pathStr = Environment.getExternalStorageDirectory().toString()+"/"+getPackageName();
                File fileDir = new File(pathStr);
                if(!fileDir.exists()){
                    fileDir.mkdirs();
                }
                File file = new File(pathStr,System.currentTimeMillis()+"carmera.jpg");
                Uri uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                intent.putExtra("picStr",pathStr);
                startActivityForResult(intent, CARMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CARMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            ImageView imageView = new ImageView(this);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.carmera_linear);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(imageView);
            if(data.hasExtra("picStr")){
                L.e("picStr:"+data.getStringExtra("picStr"));
                imageView.setImageDrawable(Drawable.createFromPath(data.getStringExtra("picStr")));
            }

        }
    }
}
