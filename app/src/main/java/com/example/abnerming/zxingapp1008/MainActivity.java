package com.example.abnerming.zxingapp1008;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.android.PermissionUtils;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.encode.CodeCreator;

public class MainActivity extends AppCompatActivity {

    private final int RESULT_CODE=1000;
    private TextView mTxt;
    private ImageView mImage;
    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxt=(TextView)findViewById(R.id.tv_mess);
         mImage=(ImageView) findViewById(R.id.image);
          mEdit=(EditText) findViewById(R.id.edit);
        findViewById(R.id.scan_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sCan();
            }
        });
        //带logo
        findViewById(R.id.captrue_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCaptrue(0);
            }
        });
        //不带logo
        findViewById(R.id.captrue_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCaptrue(1);
            }
        });
    }

    //生成图片
    private void setCaptrue(int type) {
       String message= mEdit.getText().toString().trim();
       if(TextUtils.isEmpty(message)){
           Toast.makeText(this,"请输入一点东西啊",Toast.LENGTH_SHORT).show();
           return;
       }



        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        if(type==1){
            bitmap=null;
        }

        try {
            Bitmap bitmap2 =  CodeCreator.createQRCode(message,200,200,bitmap);

            mImage.setImageBitmap(bitmap2);
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    //跳转到扫一扫
    private void sCan() {
        PermissionUtils.permission(this, new PermissionUtils.PermissionListener() {
            @Override
            public void success() {
                Intent intent=new Intent(MainActivity.this, CaptureActivity.class);

//                ZxingConfig config=new ZxingConfig();
//                config.setShowbottomLayout(false);
//                config.setPlayBeep(true);e
//                intent.putExtra(Constant.INTENT_ZXING_CONFIG,config);
                startActivityForResult(intent,RESULT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_CODE&&resultCode==RESULT_OK){
            String result=data.getStringExtra(Constant.CODED_CONTENT);
            if(result.contains("http")){
                //跳转
                Intent intent=new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("web_url",result);
                startActivity(intent);
            }else{
                mTxt.setText(result);
            }

        }

    }
}
